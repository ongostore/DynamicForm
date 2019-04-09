package com.ongo.dynamicformlibrary;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ongo.dynamicformlibrary.form_utils.FormDependentDTO;
import com.ongo.dynamicformlibrary.form_utils.FormMyMap;
import com.ongo.dynamicformlibrary.form_utils.FormServiceFieldsDto;
import com.ongo.dynamicformlibrary.form_utils.FormServiceUtils;
import com.ongo.dynamicformlibrary.utils.FormAlertDialogsUtils;
import com.ongo.dynamicformlibrary.utils.FormConstants;
import com.ongo.dynamicformlibrary.utils.FormFilePath;
import com.ongo.dynamicformlibrary.utils.FormUtils;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class ServiceFormFragmentForm extends FormBaseFragment implements ServiceFormPresenter.ServiceFormListener {
    public static final int REQUEST_FILE_SELECT = 1;
    final static String TAG = "ServiceFormFragmentForm";
    private static final int RP_READ_STORAGE = 126;
    private static final int REQ_LOCATION_CODE = 1432;
    private static final int REQ_CAMERA_CODE = 1452;
    private static final int INT_REQ_CODE_SIGNATURE_PAD = 3541;
    private static int IMAGE_CAMERA = 111;
    String[] stringPerms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    String[] cameraPerms = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Context mContext;
    Button submitBTN;
    LinearLayout linearLayout, photosLL;
    ServiceFormPresenter serviceFormPresenter;
    ListView lView;
    RelativeLayout rlOk;
    String[] multiStringArry;
    TextView multiEditText;
    MultiSelectInterface multiSelectListener;
    HashMap<String, String> hashMap = new HashMap<>();
    ImageView attachmentImageView;
    ImageView signatureImageView;
    ImageView addPhotoIV;
    String upload_key_tv, upload_mandatory_tv;
    TextView mAttachment_name_tv;
    String itemJob;
    private ArrayList<FormServiceFieldsDto> formServiceFieldsDtos;
    private ArrayList<FormDependentDTO> formDependentDTOS = new ArrayList<>();
    private HashMap<String, String> isManadatory = new HashMap<>();
    private String previewForm = "false";
    private String signaturePath;
    private String postType;
    private DynamicServiceForm.DynamicServiceFormListener dynamicServiceFormListener;
    private RecyclerView photosRV;
    private PhotosAdapter photosAdapter;
    private ArrayList<String> photos;
    private String dt, category;

    @SuppressLint("ValidFragment")
    public ServiceFormFragmentForm(DynamicServiceForm.DynamicServiceFormListener dynamicServiceFormListener) {
        this.dynamicServiceFormListener = dynamicServiceFormListener;
    }

    public ServiceFormFragmentForm() {
        // Required empty public constructor
    }
//
//    public static ServiceFormFragmentForm newInstance() {
//        ServiceFormFragmentForm fragment = new ServiceFormFragmentForm();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_form, container, false);
        mContext = getActivity();
        serviceFormPresenter = new ServiceFormPresenter(ServiceFormFragmentForm.this, this);
        initView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            postType = bundle.getString(FormConstants.postType);
            itemJob = bundle.getString(FormConstants.jobObj);
            dt = bundle.getString(FormConstants.dt);
            category = bundle.getString(FormConstants.category);
            if (itemJob != null) {
                FormConstants.editFieldsHASHMAP = serviceFormPresenter.getHashMap(itemJob);
                FormConstants.editFieldsImagesHashMap = serviceFormPresenter.getImagesHashMap(itemJob);
            }
        }
        serviceFormPresenter.getServiceFields(FormConstants.getHostUrl(), postType, FormConstants.mallId, dt);

        return view;
    }

    @Override
    public void initView(View view) {
        submitBTN = view.findViewById(R.id.submitBTN);
        linearLayout = view.findViewById(R.id.linearLayout);
        photosLL = view.findViewById(R.id.photosLL);
        addPhotoIV = view.findViewById(R.id.addPhotoIV);
        photosRV = view.findViewById(R.id.photosRV);
        initListeners();
    }

    @Override
    public void initListeners() {
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues(true);
            }
        });
    }

    @Override
    public void onResponse(String status, String result) {
        if (dynamicServiceFormListener != null) {
            dynamicServiceFormListener.onSuccess(status, result);
        } else {
            FormUtils.toast("Something went wrong with listener.", mContext);
        }
    }

    @Override
    public void onImageSelected(ArrayList<String> imagesList) {

    }

    @Override
    public void setLayout(ArrayList<FormServiceFieldsDto> formServiceFieldsDtos) {
        this.formServiceFieldsDtos = formServiceFieldsDtos;
        linearLayout.removeAllViews();
        addLayouts();
    }

    @Override
    public void showMessage(String msg) {
        FormUtils.toast(msg, mContext);
    }

    private void addLayouts() {
        Log.e("add layout", ".............");
        for (int i = 0; i < formServiceFieldsDtos.size(); i++) {
            FormServiceFieldsDto formServiceFieldsDto = formServiceFieldsDtos.get(i);
            final String tagName = formServiceFieldsDto.getName();
            final String mandatory = formServiceFieldsDto.getMandatory();
            if (formServiceFieldsDto.getType().equalsIgnoreCase("Small Text")) {
                addSmallText(tagName, mandatory);
            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Numeric Text")) {
                addNumericText(tagName, mandatory);
            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Large Text")) {
                addLargeText(tagName, mandatory);
            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Radio OR CheckBox")) {
                Log.e("allowed values", ">>>>>>>>....." + formServiceFieldsDto.allowedValues);
                List<String> allowedValuesArry = new ArrayList<String>
                        (Arrays.asList(formServiceFieldsDto.allowedValues.split("\\|")));
                addRadioButton(tagName, mandatory, allowedValuesArry);
            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Selection")) {
                boolean isMultiset = Boolean.valueOf(formServiceFieldsDto.getMultiselect());
                String allowedValues = formServiceFieldsDto.getAllowedValues();
                //changed due to realm
                // HashMap<String, String> stringStringHashMap = formServiceFieldsDto.getAllowedValuesResults();
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                ArrayList<FormMyMap> arry = formServiceFieldsDto.getAllowedValuesResults();
                if (arry != null && arry.size() > 0) {
                    for (int k = 0; k < arry.size(); k++) {
                        try {
                            FormMyMap map = arry.get(k);
                            JSONObject obj = new JSONObject();
                            obj.put(map.getKey(), map.getValue());
                            // obj = arry.getJSONObject(k);
                            Iterator<?> keys = obj.keys();

                            while (keys.hasNext()) {
                                String key = (String) keys.next();
                                if (obj.get(key) instanceof String) {
                                    String value = obj.getString(key);
                                    stringStringHashMap.put(key, value);
                                }
                            }

                        } catch (JSONException e) {
                            Log.e("json exp", ">>>>>>>>>>" + e.getLocalizedMessage());
                        }

                    }
                }
                String[] stringArray = null;

                ArrayList<String> stringArrayListKeys = new ArrayList<>();
                ArrayList<String> stringArrayListValues = new ArrayList<>();

                if (!allowedValues.isEmpty()) {

                    if (allowedValues.contains("q:")) {

                        Iterator it = stringStringHashMap.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();

                            stringArrayListKeys.add(pair.getKey().toString());
                            stringArrayListValues.add(pair.getValue().toString());

                        }
                    } else {
                        if (allowedValues.contains("|")) {
                            stringArray = allowedValues.split("\\|");
                        }
                    }
                }


                if (stringArrayListKeys.size() > 0) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    LinearLayout innerLinearLayout = FormServiceUtils.getSelectionViewForQColumn(mandatory, mContext, params, tagName, isMultiset, stringArrayListKeys, stringArrayListValues, new FormServiceUtils.SelectionInterfaceForQColums() {
                        @Override
                        public void selectionListener(boolean isMultiset, final ArrayList<String> keysArray, final ArrayList<String> valuesArray, TextView textViewMain) {
                            final ArrayList<String> finalStrings = keysArray;
                            final ArrayList<String> finalValuesArray = valuesArray;
                            final TextView finalEditText = textViewMain;
                            String dependencyName = serviceFormPresenter.getDependencyNameList(formServiceFieldsDtos, tagName);
                            FormDependentDTO formDependentDTO = new FormDependentDTO();
                            formDependentDTO.setDependentName(dependencyName);
                            formDependentDTO.setKeyArray(keysArray);
                            formDependentDTO.setValueArray(valuesArray);
                            formDependentDTO.setTagName(tagName);
                            formDependentDTO.setTextView(textViewMain);
                            formDependentDTOS.add(formDependentDTO);
                            if (keysArray != null && !isMultiset) {
                                textViewMain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ArrayList<String> subKeysArray = new ArrayList<>();
                                        ArrayList<String> subValueArray = new ArrayList<>();
                                        FormDependentDTO formDependentDTO1 = serviceFormPresenter.checkDependency(formDependentDTOS, tagName);
                                        if (formDependentDTO1 != null) {
                                            String selectedId = formDependentDTO1.getSelectedId();
                                            if (TextUtils.isEmpty(selectedId)) {
                                                FormUtils.toast("Please select " + formDependentDTO1.getTagName() + " filed", mContext);
                                                return;
                                            }

                                            for (int i = 0; i < keysArray.size(); i++) {
                                                if (keysArray.get(i).contains(selectedId)) {
                                                    subKeysArray.add(keysArray.get(i).substring(0, keysArray.get(i).lastIndexOf("(")));
                                                    subValueArray.add(valuesArray.get(i));
                                                }
                                            }
                                        } else {
                                            for (int i = 0; i < keysArray.size(); i++) {
                                                subKeysArray.add(keysArray.get(i).substring(0, keysArray.get(i).lastIndexOf("(")));
                                                subValueArray.add(valuesArray.get(i));
                                            }
                                        }
                                        if (subKeysArray.size() > 0) {
                                            showSingleSelection(tagName, subKeysArray, subValueArray, finalEditText, keysArray);
                                        } else {
                                            FormUtils.toast("No " + tagName + " available", mContext);
                                        }
                                    }
                                });
                            }
                            if (textViewMain != null) {
                                textViewMain.setInputType(InputType.TYPE_NULL);
                            }
                        }
                    });

                    linearLayout.addView(innerLinearLayout, params);


                } else {

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    LinearLayout innerLinearLayout = FormServiceUtils.getSelectionView(mandatory, mContext, params, tagName, isMultiset, stringArray, new FormServiceUtils.SelectionInterface() {
                        @Override
                        public void selectionListener(boolean isMultiset, String[] stringArray, TextView textViewMain) {
                            final String[] finalStrings = stringArray;
                            final TextView finalEditText = textViewMain;
                            if (stringArray != null && isMultiset) {
                                textViewMain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showMultiSelection(tagName, finalStrings, finalEditText, new MultiSelectInterface() {
                                            @Override
                                            public void multiSelectListener(String selectedName) {
                                                finalEditText.setText(selectedName);
                                            }
                                        });
                                    }
                                });


                            } else if (stringArray != null && !isMultiset) {
                                textViewMain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showSingleSelection(tagName, finalStrings, finalEditText);
                                    }
                                });
                            }
                            if (textViewMain != null) {
                                textViewMain.setInputType(InputType.TYPE_NULL);
                            }
                        }
                    });

                    linearLayout.addView(innerLinearLayout, params);
                }

            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Attachment")) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_layout, null, false);
                RecyclerView photosRV = view.findViewById(R.id.photosRV);
                ImageView photosIV = view.findViewById(R.id.addPhotoIV);
                linearLayout.addView(view);

                hashMap.put(tagName, "");
                isManadatory.put(tagName, mandatory);

                photosIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);//
                            try {
                                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 1111);
                            } catch (android.content.ActivityNotFoundException ex) {
                                // Potentially direct the user to the Market with a Dialog
                            }
                        } else {
                            EasyPermissions.requestPermissions(getActivity(), "This app needs permission to storage", RP_READ_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                        }

                    }
                });
                photos = new ArrayList<>();
                photosAdapter = new PhotosAdapter(photos, mContext);
                LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                photosRV.setLayoutManager(manager);
                photosRV.setAdapter(photosAdapter);


                //As per layout this is not required.
//                final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                FormServiceUtils.getAttachmentView(mandatory, mContext, params, tagName, new FormServiceUtils.AttachmentInterface() {
//                    @Override
//                    public void attachmentListener(LinearLayout innerLinearLayout, final ImageView view, final TextView textView) {
//                        linearLayout.addView(innerLinearLayout, params);
//                        if (textView != null) {
//                            textView.setInputType(InputType.TYPE_NULL);
//                        }
//                        hashMap.put(tagName, "");
//                        isManadatory.put(tagName, mandatory);
//                        view.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                attachmentImageView = view;
//                                showCameraGalleryAlert(tagName, textView);
//                            }
//                        });
//                    }
//                });

            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Dates_after_today")) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout innerLinearLayout = FormServiceUtils.getDateTimeView(mandatory, mContext, params, tagName, "DateTime", new FormServiceUtils.DateTimeInterface() {
                    @Override
                    public void dateTimeListener(final TextView textView, ImageView imageView) {
                        if (textView != null) {
                            textView.setInputType(InputType.TYPE_NULL);
                        }

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FormUtils.showDateAfterTodayPicker(mContext, 0, new FormUtils.DatePickerInterface() {
                                    @Override
                                    public void datePickerInterface(String date) {
                                        textView.setText(date);
                                    }
                                });
                            }
                        });
                    }
                });
                linearLayout.addView(innerLinearLayout, params);
            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Date Time")) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout innerLinearLayout = FormServiceUtils.getDateTimeView(mandatory, mContext, params, tagName, "DateTime", new FormServiceUtils.DateTimeInterface() {
                    @Override
                    public void dateTimeListener(final TextView textView, ImageView imageView) {
                        if (textView != null) {
                            textView.setInputType(InputType.TYPE_NULL);
                        }

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FormServiceUtils.showDateTimePicker(mContext, new FormServiceUtils.DatePickerInterface() {
                                    @Override
                                    public void datePickerInterface(String date) {
                                        textView.setText(date);
                                    }
                                });
                            }
                        });
                    }
                });
                linearLayout.addView(innerLinearLayout, params);
            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Date")) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout innerLinearLayout = FormServiceUtils.getDateTimeView(mandatory, mContext, params, tagName, "Date", new FormServiceUtils.DateTimeInterface() {
                    @Override
                    public void dateTimeListener(final TextView textView, ImageView imageView) {
                        if (textView != null) {
                            textView.setInputType(InputType.TYPE_NULL);
                        }
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (tagName.equalsIgnoreCase("DOB")) {
                                    FormUtils.showDateOfBirthPicker(mContext, 0, new FormUtils.DatePickerInterface() {
                                        @Override
                                        public void datePickerInterface(String date) {
                                            textView.setText(date);
                                        }
                                    });
                                } else {
                                    FormServiceUtils.showDatePicker(mContext, new FormServiceUtils.DatePickerInterface() {
                                        @Override
                                        public void datePickerInterface(String date) {
                                            textView.setText(date);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });

                linearLayout.addView(innerLinearLayout, params);

            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Time")) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout innerLinearLayout = FormServiceUtils.getDateTimeView(mandatory, mContext, params, tagName, "Time", new FormServiceUtils.DateTimeInterface() {
                    @Override
                    public void dateTimeListener(final TextView textView, ImageView imageView) {
                        if (textView != null) {
                            textView.setInputType(InputType.TYPE_NULL);
                        }
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FormServiceUtils.showTimePicker(mContext, new FormServiceUtils.TimePickerInterface() {
                                    @Override
                                    public void timePickerInterface(String date) {
                                        textView.setText(date);
                                    }
                                });
                            }
                        });
                    }
                });
                linearLayout.addView(innerLinearLayout, params);

            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Geo Location")) {
//                locationTAGNAME = tagName;
//                SharedPreferences sharedPreferences = mContext.getSharedPreferences(FormConstants.PREF_NAME, Context.MODE_PRIVATE);
//                FormUtils.turnGPSOn(getActivity(), sharedPreferences.edit(), true);
//
//                isPermissionGranted();

            } else if (formServiceFieldsDto.getType().equalsIgnoreCase("Signature")) {
                addSignature(tagName, mandatory);
            }
        }

        if ((FormConstants.editFieldsHASHMAP != null && !FormConstants.editFieldsHASHMAP.isEmpty()) || previewForm.equalsIgnoreCase("true")) {
            getValues(false);
            if (FormConstants.editFieldsHASHMAP != null && !FormConstants.editFieldsImagesHashMap.isEmpty() && FormConstants.editFieldsImagesHashMap.size() > 0) {
                ArrayList<String> arrayList = new ArrayList<>();
                HashMap<String, File> map = new HashMap<>();
                for (String key : FormConstants.editFieldsImagesHashMap.keySet()) {
                    String value = FormConstants.editFieldsImagesHashMap.get(key);
                    arrayList.add(value);
                    try {
                        File file = new File(FormUtils.getPath(mContext, Uri.parse(value)));
                        map.put(key, file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                onImageSelected(arrayList);
                serviceFormPresenter.getHashMapFile(map);
            }
        }

        /*if (FormSharedPref.read(FormConstants.userRoleName, "").equalsIgnoreCase("Inspector")) {
            // rlSubmit.setVisibility(View.GONE);
            fab_check.setVisibility(View.GONE);
        } else {
            // rlSubmit.setVisibility(View.VISIBLE);
            fab_check.setVisibility(View.VISIBLE);
        }*/

    }

    private String getTagValue(String tagName) {
        try {
            JSONObject jsonObject = new JSONObject(itemJob);
            return FormUtils.checkJsonObjStr(jsonObject, tagName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addSmallText(String tagName, String mandatory) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout innerRelativeLayout = FormServiceUtils.getSmallEditTextWithImageView(mContext, params, tagName, false, mandatory);
        linearLayout.addView(innerRelativeLayout, params);
    }

    private void addRecyclerView(String tagName, String mandatory) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_layout, null, false);
        linearLayout.addView(view);
    }

    private void addSignature(String tagName, String mandatory) {
        FormUtils.toast("Not Added in this Project", mContext);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        // LinearLayout innerLinearLayout = FormServiceUtils.getSignatureLayout(mContext, params, tagName, mandatory);
//
//        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        params2.setMargins(10, 0, 15, 0);
//        LinearLayout innerLinearLayout = new LinearLayout(mContext);
//        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
//        // innerLinearLayout.setPadding(20,10,20,10);
//        innerLinearLayout.setLayoutParams(params2);
//
//        TextView textView = new TextView(mContext);
//        if (mandatory.equalsIgnoreCase("true"))
//            textView.setText(tagName + "*");
//        else
//            textView.setText(tagName);
//        textView.setTextSize(16);
//        textView.setTextColor(Color.BLACK);
//        params.setMargins(20, 5, 20, 2);
//        innerLinearLayout.addView(textView, params);
//
//        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(400,
//                400);
//        params1.setMargins(20, 10, 20, 10);
//        params1.gravity = Gravity.CENTER;
//
//        //ImageView Setup
//        signatureImageView = new ImageView(mContext);
//        signatureImageView.setImageResource(R.drawable.ic_signing_the_contract);
//        signatureImageView.setTag(tagName);
//        innerLinearLayout.addView(signatureImageView, params1);
//
//        signatureImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, SignaturePadActivity.class);
//                startActivityForResult(intent, INT_REQ_CODE_SIGNATURE_PAD);
//            }
//        });
//        linearLayout.addView(innerLinearLayout, params);
    }

    private void addNumericText(String tagName, String mandatory) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout innerRelativeLayout = FormServiceUtils.getNumericEditText(mContext, params, tagName, false, mandatory);
        linearLayout.addView(innerRelativeLayout, params);
    }

    private void addLargeText(String tagName, String mandatory) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout innerLinearLayout = FormServiceUtils.getLargeEditText(mContext, params, tagName, mandatory);
        linearLayout.addView(innerLinearLayout, params);
    }

    private void addRadioButton(String tagName, String mandatory, List<String> allowedValuesArry) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout innerLinearLayout = FormServiceUtils.getRadioButton(mContext, params, tagName, mandatory,
                allowedValuesArry);
        linearLayout.addView(innerLinearLayout, params);
    }

    private void getValues(boolean isSubmitClicked) {

        for (FormServiceFieldsDto formServiceFieldsDto : formServiceFieldsDtos) {
            String tagName = formServiceFieldsDto.getName();
            String madatory = formServiceFieldsDto.getMandatory();
            String allowedValues = formServiceFieldsDto.getAllowedValues();
            String type = formServiceFieldsDto.getType();
            Log.d("SaleRegisterFragTagName", tagName);

            for (int i = 0; i < linearLayout.getChildCount(); i++) {
//                if (tagName.equalsIgnoreCase("Price With Fuel")) {
//                    String str = FormConstants.editFieldsHASHMAP.get("Price With Fuel");
//                    withFuel.setText(str);
//                    break;
//                } else if (tagName.equalsIgnoreCase("Price Without Fuel")) {
//                    String str = FormConstants.editFieldsHASHMAP.get("Price Without Fuel");
//                    withoutFuel.setText(str);
//                    break;
//                } else if (tagName.equalsIgnoreCase("Price")) {
//                    String str = FormConstants.editFieldsHASHMAP.get("Price");
//                    price.setText(str);
//                    break;
//                } else if (tagName.equalsIgnoreCase("EquipmentFor")) {
//                    String str = FormConstants.editFieldsHASHMAP.get("EquipmentFor");
//                    categorySpinner.setSelection(serviceFormPresenter.getSelectedItem(categorySpinner, str));
//                    break;
//                }
                View view = linearLayout.getChildAt(i).findViewWithTag(tagName);
                //Log.e("linear", "<<<<<<<<" + view.toString());
                //Log.e("ServiceFormFrag", "getValues" + tagName);
                if (view instanceof EditText) {
                    String tagNameValue = ((EditText) view).getText().toString();
                    if (!FormConstants.editFieldsHASHMAP.isEmpty()) {
                        ((EditText) view).setText(FormConstants.editFieldsHASHMAP.get(tagName));
                        if (tagName.equalsIgnoreCase("photo")) {
                            if (FormConstants.editFieldsHASHMAP.containsKey("Photo_URL")) {
                                ((EditText) view).setText(FormConstants.editFieldsHASHMAP.get("Photo_URL"));
                            }
                        }
                    }

                    if (!type.equalsIgnoreCase("Attachment")
                            && !allowedValues.contains("q:")) {
                        hashMap.put(tagName, tagNameValue);
                        isManadatory.put(tagName, madatory);
                    }
                    break;
                } else if (view instanceof TextView) {
                    String tagNameValue = ((TextView) view).getText().toString();
                    if (!FormConstants.editFieldsHASHMAP.isEmpty()) {
                        ((TextView) view).setText(FormConstants.editFieldsHASHMAP.get(tagName));
                    }
                    if (!type.equalsIgnoreCase("Attachment") && !allowedValues.contains("q:")) {
                        hashMap.put(tagName, tagNameValue);
                        isManadatory.put(tagName, madatory);
                    } else if (!type.equalsIgnoreCase("Attachment") && allowedValues.contains("q:") && !isSubmitClicked) {
                        hashMap.put(tagName, getTagNameValue(tagName));
                        isManadatory.put(tagName, madatory);
                    }
                    break;
                } else if (view instanceof RadioGroup) {
                    RadioGroup radioGroup = (RadioGroup) view;
                    if (radioGroup.getCheckedRadioButtonId() != -1) {
                        int id = radioGroup.getCheckedRadioButtonId();
                        View radioButton = radioGroup.findViewById(id);
                        int radioId = radioGroup.indexOfChild(radioButton);
                        RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                        String selection = (String) btn.getText();

                        // Log.e("selecteion is", ">>>>>>>>>>.." + selection);

                        if (!FormConstants.editFieldsHASHMAP.isEmpty()) {
                            Log.e("selection is", ">>>>>>>>>>>>." + selection);
                        }

                        if (!type.equalsIgnoreCase("Attachment") && !allowedValues.contains("q:")) {
                            hashMap.put(tagName, selection);
                            isManadatory.put(tagName, madatory);
                        }
                    } else {
                        if (!FormConstants.editFieldsHASHMAP.isEmpty()) {
                            radioGroup.getChildCount();
                            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                                int id = radioGroup.getChildAt(j).getId();
                                View rbv = radioGroup.findViewById(id);
                                RadioButton btn = (RadioButton) rbv.findViewById(id);
                                String btnName = btn.getText().toString();

                                if (FormConstants.editFieldsHASHMAP.get(tagName) != null && FormConstants.editFieldsHASHMAP.get(tagName).equalsIgnoreCase(btnName))
                                    btn.setChecked(true);

                                // Log.e("selection is", ">>>>>>>>>>>>." + btn.getText().toString());
                            }
                        }
                        for (int j = 0; j < radioGroup.getChildCount(); j++) {
                            int id = radioGroup.getChildAt(j).getId();
                            View rbv = radioGroup.findViewById(id);
                            RadioButton btn = (RadioButton) rbv.findViewById(id);
                            if (previewForm.equalsIgnoreCase("true")) {
                                btn.setEnabled(false);
                                btn.setChecked(false);
                            }
                        }

                    }
                } else if (view instanceof ImageView) {
                    if (tagName.equalsIgnoreCase("Signature")) {
                        String tagNameValue = signaturePath;
                        if (!type.equalsIgnoreCase("Attachment") && !allowedValues.contains("q:")) {
                            hashMap.put(tagName, tagNameValue);
                            isManadatory.put(tagName, madatory);
                        }

                        if (previewForm.equalsIgnoreCase("true")) {
                            ((ImageView) view).setFocusable(false);
                            ((ImageView) view).setEnabled(false);
                        }

                        if (!FormConstants.editFieldsHASHMAP.isEmpty()) {
                            //  ((EditText) view).setText(FormConstants.editFieldsHASHMAP.get(tagName));
                            if (tagName.equalsIgnoreCase("Signature")) {
                                if (FormConstants.editFieldsHASHMAP.containsKey("Signature")) {
                                    // FormUtils.loadImage(mContext, FormConstants.editFieldsHASHMAP.get("Signature"), ((ImageView) view));
                                    if (!TextUtils.isEmpty(FormConstants.editFieldsHASHMAP.get("Signature"))) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(FormConstants.editFieldsHASHMAP.get("Signature"));
                                        ((ImageView) view).setImageBitmap(bitmap);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        if (isSubmitClicked) {
            if (!FormConstants.editFieldsHASHMAP.isEmpty())
                hashMap.put("ItemCode", FormConstants.editFieldsHASHMAP.get("ItemCode"));
            //Log.e("hashmap is", "<<<<<<<<<<<<" + hashMap.toString());

//            hashMap.put("Price With Fuel", withFuel.getText().toString());
//            hashMap.put("Price Without Fuel", withoutFuel.getText().toString());
//            hashMap.put("Price", price.getText().toString());
            serviceFormPresenter.setPhotosArray(photos);
            serviceFormPresenter.checkValidation(postType, hashMap, isManadatory, dt, category);
        }


        Log.d("ServiceFormFrag", "getValues");
//        ((EditText)linearLayout.getChildAt(0).findViewWithTag("Name")).getText();
    }

    private String getTagNameValue(String tagName) {
        try {
            JSONObject jsonObject = new JSONObject(itemJob);
            String jsonObjKey = jsonObject.getString(tagName);
            for (int j = 0; j < formDependentDTOS.size(); j++) {
                FormDependentDTO formDependentDTO = formDependentDTOS.get(j);
                String dtoTagName = formDependentDTO.getTagName();
                if (tagName.equalsIgnoreCase(dtoTagName)) {
                    ArrayList<String> keysList = formDependentDTO.getKeyArray();
                    for (int k = 0; k < keysList.size(); k++) {
                        String kPositKey = keysList.get(k);
                        String key;
                        if (!kPositKey.contains("~")) {
                            key = kPositKey;
                        } else {
                            key = (kPositKey.substring(kPositKey.lastIndexOf("~") + 1));
                        }
                        if (key.equalsIgnoreCase(jsonObjKey)) {
                            ArrayList<String> valuesArray = formDependentDTO.getValueArray();
                            if (valuesArray != null && valuesArray.size() > 0) {
                                String value = valuesArray.get(k);
                                if (value.contains("~")) {
                                    value = value.substring(value.lastIndexOf("~") + 1);
                                }
                                formDependentDTO.setSelectedId(key);
                                formDependentDTOS.set(j, formDependentDTO);
                                return value;
                            } else {
                                return "";
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void showSingleSelection(String tagName, final String[] strings, final TextView editText) {
        FormAlertDialogsUtils.showCustomAlertDialog(mContext, R.layout.single_selection_list, true, new FormAlertDialogsUtils.CustomAlertInterface() {
            @Override
            public void setListenerCustomAlert(View alertView, final AlertDialog alertDialog) {
                ListView view = alertView.findViewById(R.id.listView);
                rlOk = alertView.findViewById(R.id.rlOk);
                rlOk.setVisibility(View.GONE);
                ArrayAdapter<String> aa = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strings);
                view.setAdapter(aa);
                view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("TAG", position + "");
                        editText.setText(strings[position]);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void showSingleSelection(final String tagName, final ArrayList<String> keysArray, final ArrayList<String> valuesArray, final TextView editText, final ArrayList<String> array) {
        final ArrayList<String> tempKeysArray = new ArrayList<>();
        for (int i = 0; i < keysArray.size(); i++) {
            String str = keysArray.get(i);
            if (str.contains("~")) {
                String tempStr = str.substring(str.indexOf("~") + 1, str.length());
                tempKeysArray.add(tempStr);
            } else {
                tempKeysArray.add(str);
            }
        }
        FormAlertDialogsUtils.showCustomAlertDialog(mContext, R.layout.single_selection_list, true, new FormAlertDialogsUtils.CustomAlertInterface() {
            @Override
            public void setListenerCustomAlert(View alertView, final AlertDialog alertDialog) {
                ListView view = alertView.findViewById(R.id.listView);
                rlOk = alertView.findViewById(R.id.rlOk);
                rlOk.setVisibility(View.GONE);
                ArrayAdapter<String> aa = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, tempKeysArray);
                view.setAdapter(aa);
                view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("TAG", position + "");
                        Log.d("TAG", keysArray + "");
                        String selectedKeyId = "";
                        for (int i = 0; i < array.size(); i++) {
                            String tempArrayStr = array.get(i);
                            if (tempArrayStr.contains(keysArray.get(position))) {
                                selectedKeyId = tempArrayStr;
                                break;
                            }
                        }
//                        selectedKeyId = keysArray.get(position);
                        for (int i = 0; i < formDependentDTOS.size(); i++) {
                            if (formDependentDTOS.get(i).getTagName().equalsIgnoreCase(tagName)) {
                                String dependentName = formDependentDTOS.get(i).getDependentName();
                                String tempStrId;
                                if (selectedKeyId.contains("~")) {
                                    tempStrId = selectedKeyId.substring(selectedKeyId.indexOf("~") + 1, selectedKeyId.length());
                                } else {
                                    tempStrId = selectedKeyId;
                                }
                                removeDependents(tagName);
                                formDependentDTOS.get(i).setSelectedId(tempStrId);
                            }
                        }
                        editText.setText(tempKeysArray.get(position));
                        String selectedItemCode = valuesArray.get(position);
                        if (selectedItemCode.contains("~")) {
                            String tempItemCode = selectedItemCode.substring(selectedItemCode.indexOf("~") + 1, selectedItemCode.length());
                            hashMap.put(tagName, tempItemCode);
                        } else {
                            hashMap.put(tagName, selectedItemCode);
                        }
                        removeDependencies(tagName);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }


    void removeDependencies(String mTagName) {
        for (int i = 0; i < formDependentDTOS.size(); i++) {
            FormDependentDTO formDependentDTO = formDependentDTOS.get(i);
            String tagName = formDependentDTO.getTagName();
            if (mTagName.equalsIgnoreCase(tagName)) {
//                //remove selectedId
//                formDependentDTO.setSelectedId("");
                String dependentName = formDependentDTO.getDependentName();
                if (!TextUtils.isEmpty(dependentName)) {
                    for (int j = 0; j < linearLayout.getChildCount(); j++) {
                        View view = linearLayout.getChildAt(j).findViewWithTag(dependentName);
                        if (view instanceof TextView) {
                            ((TextView) view).setText("");
                            hashMap.put(dependentName, "");
                            try {
                                FormConstants.editFieldsHASHMAP.put(dependentName, "");
                            } catch (Exception e) {
                                Log.e("exp", e.toString());
                            }
                            String mandatory = getMandatory(mTagName);
                            isManadatory.put(dependentName, mandatory);
                            removeSelectedId(dependentName);
                            removeDependencies(dependentName);
                            break;
                        }
                    }
                }
            }

        }
    }


    void removeSelectedId(String mDependentName) {
        for (int i = 0; i < formDependentDTOS.size(); i++) {
            FormDependentDTO formDependentDTO = formDependentDTOS.get(i);
            String tagName = formDependentDTO.getTagName();
            if (tagName.equalsIgnoreCase(mDependentName)) {
                formDependentDTO.setSelectedId("");
                break;
            }
        }
    }

    String getMandatory(String mTagName) {
        for (FormServiceFieldsDto formServiceFieldsDto : formServiceFieldsDtos) {
            String tagName = formServiceFieldsDto.getName();
            if (tagName.equalsIgnoreCase(mTagName)) {
                return formServiceFieldsDto.getMandatory();
            }
        }
        return "false";
    }

    private void removeDependents(String tagName) {
        for (int i = 0; i < formDependentDTOS.size(); i++) {
            FormDependentDTO formDependentDTO = formDependentDTOS.get(i);
            if (formDependentDTO.getTagName().equalsIgnoreCase(tagName)) {
                String dependentName = formDependentDTO.getDependentName();
                formDependentDTO.setSelectedId("");
                formDependentDTOS.set(i, formDependentDTO);
                removeDependentsValue(dependentName);
            }
        }
    }

    private void removeDependentsValue(String dependentName) {
        for (int i = 0; i < formDependentDTOS.size(); i++) {
            FormDependentDTO formDependentDTO = formDependentDTOS.get(i);
            if (formDependentDTO.getTagName().equalsIgnoreCase(dependentName)) {
                String tagName = formDependentDTO.getTagName();
                if (!TextUtils.isEmpty(tagName)) {
                    removeDependents(tagName);
                }
            }
        }
    }

    private void showMultiSelection(String tagName, final String[] strings, final TextView editText, final MultiSelectInterface multiSelectInterface) {
        FormAlertDialogsUtils.showCustomAlertDialog(mContext, R.layout.single_selection_list, true, new FormAlertDialogsUtils.CustomAlertInterface() {
            @Override
            public void setListenerCustomAlert(View alertView, AlertDialog alertDialog) {
                multiEditText = editText;
                multiSelectListener = multiSelectInterface;
                multiStringArry = strings;
                lView = alertView.findViewById(R.id.listView);
                rlOk = alertView.findViewById(R.id.rlOk);
                rlOk.setVisibility(View.VISIBLE);
//                lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                MultiSelectAdapter aa = new MultiSelectAdapter(mContext, strings, multiSelectInterface, rlOk, alertDialog);
                lView.setAdapter(aa);
//                lView.setOnItemClickListener(ServiceFormFragmentForm.this);
                alertDialog.show();
            }
        });
    }

    private void showCameraGalleryAlert(final String tagName, final TextView textView) {
        FormAlertDialogsUtils.showAlertDialog(mContext, "Options", "Please select options for attachment.", "Camera", "Gallery", new FormAlertDialogsUtils.AlertDialogInterface() {
            @Override
            public void positiveButton() {
                cameraAccessTask(tagName, textView);
            }

            @Override
            public void negativeButton() {
                galleryAccessTask(tagName, textView);
            }
        });
    }

    private void cameraAccessTask(String tagName, TextView textView) {
        if (isCameraPermissionGranted()) {
            upload_key_tv = tagName;
            mAttachment_name_tv = textView;
            pickCamera();
        }
    }

    private void pickCamera() {

        // camera select
        new ImagePicker.Builder(getActivity()).mode(ImagePicker.Mode.CAMERA).allowMultipleImages(true).compressLevel(ImagePicker.ComperesLevel.MEDIUM).directory(ImagePicker.Directory.DEFAULT).extension(ImagePicker.Extension.PNG).scale(600, 600).allowMultipleImages(true).enableDebuggingMode(true).build();

    }

    @AfterPermissionGranted(REQ_CAMERA_CODE)
    private boolean isCameraPermissionGranted() {
        if (!EasyPermissions.hasPermissions(mContext, cameraPerms)) {
            EasyPermissions.requestPermissions(this, "This app needs Location permission for address.", REQ_CAMERA_CODE, cameraPerms);
            return false;
        }
        return true;
    }

    @AfterPermissionGranted(REQ_LOCATION_CODE)
    private boolean isPermissionGranted() {
        if (!EasyPermissions.hasPermissions(mContext, stringPerms)) {
            EasyPermissions.requestPermissions(this, "This app needs Location permission for address.", REQ_LOCATION_CODE, stringPerms);
        }
        //setLocationView();
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_LOCATION_CODE) {
            if (grantResults[0] == -1 && grantResults[1] == -1) {
                FormAlertDialogsUtils.showAlertDialog(mContext, "Alert", "You can't set location without permissions.", "Continue", "Try Again", new FormAlertDialogsUtils.AlertDialogInterface() {
                    @Override
                    public void positiveButton() {
                    }

                    @Override
                    public void negativeButton() {
                        EasyPermissions.requestPermissions(ServiceFormFragmentForm.this, "This app needs Location permission for address.", REQ_LOCATION_CODE, stringPerms);
                    }
                });
            }
        } else if (requestCode == REQ_CAMERA_CODE) {
            if (grantResults[0] == -1 || grantResults[1] == -1 || grantResults[2] == -1) {
                FormAlertDialogsUtils.showAlertDialog(mContext, "Alert", "You can't use Camera without permissions.", "Continue", "Try Again", new FormAlertDialogsUtils.AlertDialogInterface() {
                    @Override
                    public void positiveButton() {
                    }

                    @Override
                    public void negativeButton() {
                        EasyPermissions.requestPermissions(ServiceFormFragmentForm.this, "This app needs Location permission for address.", REQ_LOCATION_CODE, stringPerms);
                    }
                });
            } else {
                pickCamera();
            }

        } else {
            serviceFormPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @AfterPermissionGranted(RP_READ_STORAGE)
    private void galleryAccessTask(String tagName, TextView textView) {
        upload_key_tv = tagName;
        mAttachment_name_tv = textView;
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            //intent.setType("application/pdf");
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), REQUEST_FILE_SELECT);
        } else {
            EasyPermissions.requestPermissions(this, "This app needs permission to storage", RP_READ_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      /*  serviceFormPresenter.onActivityResult(requestCode, resultCode, data, new ServiceFormActivityForm.ListenerInterface() {
            @Override
            public void onImageSelected(int requestCode, String fileName) {
            }
        });*/

        if (requestCode == 1111 && resultCode == RESULT_OK && data != null) {
            // Get the Uri of the selected file
            Uri selectedImage = data.getData();
            String filePath = FormFilePath.getPath(mContext, selectedImage);
            if (!TextUtils.isEmpty(filePath)) {
                if (checkForDuplicateFile(filePath)) {
                    photos.add(filePath);
                    photosAdapter.notifyDataSetChanged();
                } else {
                    FormUtils.toast("You have selected this file.", mContext);
                }
            }
        }
    }

    private boolean checkForDuplicateFile(String filePath) {
        if (photos != null && photos.size() > 0) {
            for (String path : photos) {
                if (path.equalsIgnoreCase(filePath))
                    return false;
            }
        }
        return true;
    }

    private interface MultiSelectInterface {
        void multiSelectListener(String selectedName);
    }

    private class MultiSelectAdapter extends BaseAdapter {
        private final String[] stringsList;
        LayoutInflater mInflater;
        List<String> listNames = new ArrayList<>();

        public MultiSelectAdapter(Context mContext, String[] strings, final MultiSelectInterface multiSelectInterface, RelativeLayout rlOk, final AlertDialog alertDialog) {
            this.stringsList = strings;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rlOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedName = "";
                    for (int i = 0; i < listNames.size(); i++) {
                        selectedName = selectedName + listNames.get(i) + ",";
                    }
                    selectedName = selectedName.substring(0, selectedName.length() - 1);
                    multiSelectInterface.multiSelectListener(selectedName);
                    alertDialog.dismiss();
                }
            });
        }

        @Override
        public int getCount() {
            return stringsList.length;
        }

        @Override
        public Object getItem(int position) {
            return stringsList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.multi_selection_list, parent, false);
            String itemName = stringsList[position];
            TextView tv_item_name = convertView.findViewById(R.id.tv_item_name);
            CheckBox checkbox = convertView.findViewById(R.id.checkbox);
            tv_item_name.setText(itemName);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        listNames.add(stringsList[position]);
                    } else {
                        for (int i = 0; i < listNames.size(); i++) {
                            String tempName = listNames.get(i);
                            if (tempName.equalsIgnoreCase(stringsList[position])) {
                                listNames.remove(i);
                                break;
                            }
                        }
                    }
                }
            });

            return convertView;
        }
    }


}
