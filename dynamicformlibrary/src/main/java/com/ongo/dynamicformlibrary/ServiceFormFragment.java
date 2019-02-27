package com.ongo.dynamicformlibrary;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.ongo.dynamicformlibrary.form_utils.DependentDTO;
import com.ongo.dynamicformlibrary.form_utils.MyMap;
import com.ongo.dynamicformlibrary.form_utils.ServiceFieldsDto;
import com.ongo.dynamicformlibrary.form_utils.ServiceUtils;
import com.ongo.dynamicformlibrary.utils.AlertDialogsUtils;
import com.ongo.dynamicformlibrary.utils.OnGoConstants;
import com.ongo.dynamicformlibrary.utils.Utils;

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

public class ServiceFormFragment extends BaseFragment implements ServiceFormPresenter.ServiceFormListener {
    public static final int REQUEST_FILE_SELECT = 1;
    final static String TAG = "ServiceFormFragment";
    private static final int RP_READ_STORAGE = 126;
    private static final int REQ_LOCATION_CODE = 1432;
    private static final int REQ_CAMERA_CODE = 1452;
    private static final int INT_REQ_CODE_SIGNATURE_PAD = 3541;
    private static int IMAGE_CAMERA = 111;
    String[] stringPerms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    String[] cameraPerms = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Context mContext;
    Spinner categorySpinner;
    Button submitBTN;
    LinearLayout rentLL1, sell, photosLL, tempImage1, linearLayout;
    EditText withFuel, withoutFuel, price;
    ServiceFormPresenter serviceFormPresenter;
    ImageView image1, image2, image3, image4, image5, image6;
    ImageView tempImage2, tempImage3, tempImage4, tempImage5, tempImage6;
    ListView lView;
    RelativeLayout rlOk, spinnerRL;
    String[] multiStringArry;
    TextView multiEditText;
    MultiSelectInterface multiSelectListener;
    HashMap<String, String> hashMap = new HashMap<>();
    ImageView attachmentImageView;
    ImageView signatureImageView;
    String upload_key_tv, upload_mandatory_tv;
    TextView mAttachment_name_tv;
    String itemJob;
    private ArrayList<ServiceFieldsDto> serviceFieldsDtos;
    private ArrayList<DependentDTO> dependentDTOS = new ArrayList<>();
    private HashMap<String, String> isManadatory = new HashMap<>();
    private String previewForm = "false";
    private String signaturePath;
    private String postType;

//    public ServiceFormFragment() {
//        // Required empty public constructor
//    }

    public static ServiceFormFragment newInstance() {
        ServiceFormFragment fragment = new ServiceFormFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_form, container, false);
        mContext = getActivity();
        serviceFormPresenter = new ServiceFormPresenter(ServiceFormFragment.this, this);
        initView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            postType = bundle.getString(OnGoConstants.postType);
            itemJob = bundle.getString(OnGoConstants.jobObj);
            if (itemJob != null) {
                OnGoConstants.editFieldsHASHMAP = serviceFormPresenter.getHashMap(itemJob);
                OnGoConstants.editFieldsImagesHashMap = serviceFormPresenter.getImagesHashMap(itemJob);
            }
        }
        serviceFormPresenter.getServiceFields(OnGoConstants.getHostUrl(), postType, OnGoConstants.mallId);

        return view;
    }

    @Override
    public void initView(View view) {
        categorySpinner = view.findViewById(R.id.categorySpinner);
        submitBTN = view.findViewById(R.id.submitBTN);
        rentLL1 = view.findViewById(R.id.rentLL1);
        sell = view.findViewById(R.id.sell);
        withFuel = view.findViewById(R.id.withFuel);
        withoutFuel = view.findViewById(R.id.withoutFuel);
        price = view.findViewById(R.id.price);
        spinnerRL = view.findViewById(R.id.spinnerRL);
        photosLL = view.findViewById(R.id.photosLL);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        image5 = view.findViewById(R.id.image5);
        image6 = view.findViewById(R.id.image6);
        tempImage2 = view.findViewById(R.id.tempImage2);
        tempImage3 = view.findViewById(R.id.tempImage3);
        tempImage4 = view.findViewById(R.id.tempImage4);
        tempImage5 = view.findViewById(R.id.tempImage5);
        tempImage6 = view.findViewById(R.id.tempImage6);
        tempImage1 = view.findViewById(R.id.tempImage1);
        linearLayout = view.findViewById(R.id.linearLayout);
        initListeners();
    }

    @Override
    public void initListeners() {
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedItem = categorySpinner.getSelectedItem().toString();
                hashMap.put("EquipmentFor", selectedItem);
                if (selectedItem.equalsIgnoreCase("Rent")) {
                    price.setText("");
                    rentLL1.setVisibility(View.VISIBLE);
                    sell.setVisibility(View.GONE);
                } else {
                    withFuel.setText("");
                    withoutFuel.setText("");
                    rentLL1.setVisibility(View.GONE);
                    sell.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        photosLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceFormPresenter.getImagesList();
            }
        });
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues(true);
            }
        });
    }

    @Override
    public void onResponse(String status) {
        if (status.equalsIgnoreCase("1")) {
            AlertDialogsUtils.showCustomAlertDialog(mContext, R.layout.thank_you_dialog_fragment, false, new AlertDialogsUtils.CustomAlertInterface() {
                @Override
                public void setListenerCustomAlert(View alertView, final AlertDialog alertDialog) {
//                    ImageView cancel_action = alertView.findViewById(R.id.cancel_action);
                    TextView okTV = alertView.findViewById(R.id.okTV);
//                    TextView callHelpline = alertView.findViewById(R.id.callHelpline);
                    okTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ((Activity) mContext).onBackPressed();
                        }
                    });
//                    contactForm.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            showMessage("Under Development.");
//                        }
//                    });
//                    callHelpline.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            showMessage("Under Development.");
//                        }
//                    });
                    alertDialog.show();
                }
            });
        }
    }

    @Override
    public void onImageSelected(ArrayList<String> imagesList) {
        Log.d(TAG, "Image selected list");
        tempImage1.setVisibility(View.VISIBLE);
        Utils.setVisibility(image1, View.GONE);
        Utils.setVisibility(tempImage2, View.VISIBLE);
        Utils.setVisibility(image2, View.GONE);
        Utils.setVisibility(tempImage3, View.VISIBLE);
        Utils.setVisibility(image3, View.GONE);
        Utils.setVisibility(tempImage4, View.VISIBLE);
        Utils.setVisibility(image4, View.GONE);
        Utils.setVisibility(tempImage5, View.VISIBLE);
        Utils.setVisibility(image5, View.GONE);
        Utils.setVisibility(tempImage6, View.VISIBLE);
        Utils.setVisibility(image6, View.GONE);

        for (int i = 0; i < imagesList.size(); i++) {
            if (i == 0) {
                tempImage1.setVisibility(View.GONE);
                Utils.setVisibility(image1, View.VISIBLE);
                Utils.loadImage(image1, imagesList.get(i), mContext);
            } else if (i == 1) {
                Utils.setVisibility(tempImage2, View.GONE);
                Utils.setVisibility(image2, View.VISIBLE);
                Utils.loadImage(image2, imagesList.get(i), mContext);
            } else if (i == 2) {
                Utils.setVisibility(tempImage3, View.GONE);
                Utils.setVisibility(image3, View.VISIBLE);
                Utils.loadImage(image3, imagesList.get(i), mContext);
            } else if (i == 3) {
                Utils.setVisibility(tempImage4, View.GONE);
                Utils.setVisibility(image4, View.VISIBLE);
                Utils.loadImage(image4, imagesList.get(i), mContext);
            } else if (i == 4) {
                Utils.setVisibility(tempImage5, View.GONE);
                Utils.setVisibility(image5, View.VISIBLE);
                Utils.loadImage(image5, imagesList.get(i), mContext);
            } else if (i == 5) {
                Utils.setVisibility(tempImage6, View.GONE);
                Utils.setVisibility(image6, View.VISIBLE);
                Utils.loadImage(image6, imagesList.get(i), mContext);
            }
        }
    }

    @Override
    public void setLayout(ArrayList<ServiceFieldsDto> serviceFieldsDtos) {
        this.serviceFieldsDtos = serviceFieldsDtos;
        addLayouts();
    }

    @Override
    public void showMessage(String msg) {
        Utils.toast(msg, mContext);
    }

    private void addLayouts() {
        Log.e("add layout", ".............");
        for (int i = 0; i < serviceFieldsDtos.size(); i++) {
            ServiceFieldsDto serviceFieldsDto = serviceFieldsDtos.get(i);
            final String tagName = serviceFieldsDto.getName();
            final String mandatory = serviceFieldsDto.getMandatory();
            if (serviceFieldsDto.getType().equalsIgnoreCase("Small Text")) {
                addSmallText(tagName, mandatory);
            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Numeric Text")) {
                addNumericText(tagName, mandatory);
            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Large Text")) {
                addLargeText(tagName, mandatory);
            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Radio OR CheckBox")) {
                Log.e("allowed values", ">>>>>>>>....." + serviceFieldsDto.allowedValues);
                List<String> allowedValuesArry = new ArrayList<String>
                        (Arrays.asList(serviceFieldsDto.allowedValues.split("\\|")));
                addRadioButton(tagName, mandatory, allowedValuesArry);
            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Selection")) {
                boolean isMultiset = Boolean.valueOf(serviceFieldsDto.getMultiselect());
                String allowedValues = serviceFieldsDto.getAllowedValues();
                //changed due to realm
                // HashMap<String, String> stringStringHashMap = serviceFieldsDto.getAllowedValuesResults();
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                ArrayList<MyMap> arry = serviceFieldsDto.getAllowedValuesResults();
                if (arry != null && arry.size() > 0) {
                    for (int k = 0; k < arry.size(); k++) {
                        try {
                            MyMap map = arry.get(k);
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

                    LinearLayout innerLinearLayout = ServiceUtils.getSelectionViewForQColumn(mandatory, mContext, params, tagName, isMultiset, stringArrayListKeys, stringArrayListValues, new ServiceUtils.SelectionInterfaceForQColums() {
                        @Override
                        public void selectionListener(boolean isMultiset, final ArrayList<String> keysArray, final ArrayList<String> valuesArray, TextView textViewMain) {
                            final ArrayList<String> finalStrings = keysArray;
                            final ArrayList<String> finalValuesArray = valuesArray;
                            final TextView finalEditText = textViewMain;
                            String dependencyName = serviceFormPresenter.getDependencyNameList(serviceFieldsDtos, tagName);
                            DependentDTO dependentDTO = new DependentDTO();
                            dependentDTO.setDependentName(dependencyName);
                            dependentDTO.setKeyArray(keysArray);
                            dependentDTO.setValueArray(valuesArray);
                            dependentDTO.setTagName(tagName);
                            dependentDTO.setTextView(textViewMain);
                            dependentDTOS.add(dependentDTO);
                            if (keysArray != null && !isMultiset) {
                                textViewMain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ArrayList<String> subKeysArray = new ArrayList<>();
                                        ArrayList<String> subValueArray = new ArrayList<>();
                                        DependentDTO dependentDTO1 = serviceFormPresenter.checkDependency(dependentDTOS, tagName);
                                        if (dependentDTO1 != null) {
                                            String selectedId = dependentDTO1.getSelectedId();
                                            if (TextUtils.isEmpty(selectedId)) {
                                                Utils.toast("Please select " + dependentDTO1.getTagName() + " filed", mContext);
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
                                            Utils.toast("No " + tagName + " available", mContext);
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
                    LinearLayout innerLinearLayout = ServiceUtils.getSelectionView(mandatory, mContext, params, tagName, isMultiset, stringArray, new ServiceUtils.SelectionInterface() {
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

            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Attachment")) {
                photosLL.setVisibility(View.VISIBLE);
                hashMap.put(tagName, "");
                isManadatory.put(tagName, mandatory);

                //As per layout this is not required.
//                final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                ServiceUtils.getAttachmentView(mandatory, mContext, params, tagName, new ServiceUtils.AttachmentInterface() {
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

            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Date Time")) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout innerLinearLayout = ServiceUtils.getDateTimeView(mandatory, mContext, params, tagName, "DateTime", new ServiceUtils.DateTimeInterface() {
                    @Override
                    public void dateTimeListener(final TextView textView, ImageView imageView) {
                        if (textView != null) {
                            textView.setInputType(InputType.TYPE_NULL);
                        }

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ServiceUtils.showDateTimePicker(mContext, new ServiceUtils.DatePickerInterface() {
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
            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Date")) {


                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout innerLinearLayout = ServiceUtils.getDateTimeView(mandatory, mContext, params, tagName, "Date", new ServiceUtils.DateTimeInterface() {
                    @Override
                    public void dateTimeListener(final TextView textView, ImageView imageView) {
                        if (textView != null) {
                            textView.setInputType(InputType.TYPE_NULL);
                        }
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ServiceUtils.showDatePicker(mContext, new ServiceUtils.DatePickerInterface() {
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

            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Time")) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout innerLinearLayout = ServiceUtils.getDateTimeView(mandatory, mContext, params, tagName, "Time", new ServiceUtils.DateTimeInterface() {
                    @Override
                    public void dateTimeListener(final TextView textView, ImageView imageView) {
                        if (textView != null) {
                            textView.setInputType(InputType.TYPE_NULL);
                        }
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ServiceUtils.showTimePicker(mContext, new ServiceUtils.TimePickerInterface() {
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

            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Geo Location")) {
//                locationTAGNAME = tagName;
//                SharedPreferences sharedPreferences = mContext.getSharedPreferences(OnGoConstants.PREF_NAME, Context.MODE_PRIVATE);
//                Utils.turnGPSOn(getActivity(), sharedPreferences.edit(), true);
//
//                isPermissionGranted();

            } else if (serviceFieldsDto.getType().equalsIgnoreCase("Signature")) {
                addSignature(tagName, mandatory);
            }
        }

        if ((OnGoConstants.editFieldsHASHMAP != null && !OnGoConstants.editFieldsHASHMAP.isEmpty()) || previewForm.equalsIgnoreCase("true")) {
            getValues(false);
            if (OnGoConstants.editFieldsHASHMAP != null && !OnGoConstants.editFieldsImagesHashMap.isEmpty() && OnGoConstants.editFieldsImagesHashMap.size() > 0) {
                ArrayList<String> arrayList = new ArrayList<>();
                HashMap<String, File> map = new HashMap<>();
                for (String key : OnGoConstants.editFieldsImagesHashMap.keySet()) {
                    String value = OnGoConstants.editFieldsImagesHashMap.get(key);
                    arrayList.add(value);
                    try {
                        File file = new File(Utils.getPath(mContext, Uri.parse(value)));
                        map.put(key, file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                onImageSelected(arrayList);
                serviceFormPresenter.getHashMapFile(map);
            }
        }

        /*if (SharedPref.read(OnGoConstants.userRoleName, "").equalsIgnoreCase("Inspector")) {
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
            return Utils.checkJsonObjStr(jsonObject, tagName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addSmallText(String tagName, String mandatory) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout innerRelativeLayout = ServiceUtils.getSmallEditTextWithImageView(mContext, params, tagName, false, mandatory);
        linearLayout.addView(innerRelativeLayout, params);
    }

    private void addSignature(String tagName, String mandatory) {
        Utils.toast("Not Added in this Project", mContext);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        // LinearLayout innerLinearLayout = ServiceUtils.getSignatureLayout(mContext, params, tagName, mandatory);
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
        RelativeLayout innerRelativeLayout = ServiceUtils.getNumericEditText(mContext, params, tagName, false, mandatory);
        linearLayout.addView(innerRelativeLayout, params);
    }

    private void addLargeText(String tagName, String mandatory) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout innerLinearLayout = ServiceUtils.getLargeEditText(mContext, params, tagName, mandatory);
        linearLayout.addView(innerLinearLayout, params);
    }

    private void addRadioButton(String tagName, String mandatory, List<String> allowedValuesArry) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout innerLinearLayout = ServiceUtils.getRadioButton(mContext, params, tagName, mandatory,
                allowedValuesArry);
        linearLayout.addView(innerLinearLayout, params);
    }

    private void getValues(boolean isSubmitClicked) {

        for (ServiceFieldsDto serviceFieldsDto : serviceFieldsDtos) {
            String tagName = serviceFieldsDto.getName();
            String madatory = serviceFieldsDto.getMandatory();
            String allowedValues = serviceFieldsDto.getAllowedValues();
            String type = serviceFieldsDto.getType();
            Log.d("SaleRegisterFragTagName", tagName);

            for (int i = 0; i < linearLayout.getChildCount(); i++) {
//                if (tagName.equalsIgnoreCase("Price With Fuel")) {
//                    String str = OnGoConstants.editFieldsHASHMAP.get("Price With Fuel");
//                    withFuel.setText(str);
//                    break;
//                } else if (tagName.equalsIgnoreCase("Price Without Fuel")) {
//                    String str = OnGoConstants.editFieldsHASHMAP.get("Price Without Fuel");
//                    withoutFuel.setText(str);
//                    break;
//                } else if (tagName.equalsIgnoreCase("Price")) {
//                    String str = OnGoConstants.editFieldsHASHMAP.get("Price");
//                    price.setText(str);
//                    break;
//                } else if (tagName.equalsIgnoreCase("EquipmentFor")) {
//                    String str = OnGoConstants.editFieldsHASHMAP.get("EquipmentFor");
//                    categorySpinner.setSelection(serviceFormPresenter.getSelectedItem(categorySpinner, str));
//                    break;
//                }
                View view = linearLayout.getChildAt(i).findViewWithTag(tagName);
                //Log.e("linear", "<<<<<<<<" + view.toString());
                //Log.e("ServiceFormFrag", "getValues" + tagName);
                if (view instanceof EditText) {
                    String tagNameValue = ((EditText) view).getText().toString();
                    if (!OnGoConstants.editFieldsHASHMAP.isEmpty()) {
                        ((EditText) view).setText(OnGoConstants.editFieldsHASHMAP.get(tagName));
                        if (tagName.equalsIgnoreCase("photo")) {
                            if (OnGoConstants.editFieldsHASHMAP.containsKey("Photo_URL")) {
                                ((EditText) view).setText(OnGoConstants.editFieldsHASHMAP.get("Photo_URL"));
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
                    if (!OnGoConstants.editFieldsHASHMAP.isEmpty()) {
                        ((TextView) view).setText(OnGoConstants.editFieldsHASHMAP.get(tagName));
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

                        if (!OnGoConstants.editFieldsHASHMAP.isEmpty()) {
                            Log.e("selection is", ">>>>>>>>>>>>." + selection);
                        }

                        if (!type.equalsIgnoreCase("Attachment") && !allowedValues.contains("q:")) {
                            hashMap.put(tagName, selection);
                            isManadatory.put(tagName, madatory);
                        }
                    } else {
                        if (!OnGoConstants.editFieldsHASHMAP.isEmpty()) {
                            radioGroup.getChildCount();
                            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                                int id = radioGroup.getChildAt(j).getId();
                                View rbv = radioGroup.findViewById(id);
                                RadioButton btn = (RadioButton) rbv.findViewById(id);
                                String btnName = btn.getText().toString();

                                if (OnGoConstants.editFieldsHASHMAP.get(tagName) != null && OnGoConstants.editFieldsHASHMAP.get(tagName).equalsIgnoreCase(btnName))
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

                        if (!OnGoConstants.editFieldsHASHMAP.isEmpty()) {
                            //  ((EditText) view).setText(OnGoConstants.editFieldsHASHMAP.get(tagName));
                            if (tagName.equalsIgnoreCase("Signature")) {
                                if (OnGoConstants.editFieldsHASHMAP.containsKey("Signature")) {
                                    // Utils.loadImage(mContext, OnGoConstants.editFieldsHASHMAP.get("Signature"), ((ImageView) view));
                                    if (!TextUtils.isEmpty(OnGoConstants.editFieldsHASHMAP.get("Signature"))) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(OnGoConstants.editFieldsHASHMAP.get("Signature"));
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
            if (!OnGoConstants.editFieldsHASHMAP.isEmpty())
                hashMap.put("ItemCode", OnGoConstants.editFieldsHASHMAP.get("ItemCode"));
            //Log.e("hashmap is", "<<<<<<<<<<<<" + hashMap.toString());

//            hashMap.put("Price With Fuel", withFuel.getText().toString());
//            hashMap.put("Price Without Fuel", withoutFuel.getText().toString());
//            hashMap.put("Price", price.getText().toString());

            serviceFormPresenter.checkValidation(postType, hashMap, isManadatory);
        }


        Log.d("ServiceFormFrag", "getValues");
//        ((EditText)linearLayout.getChildAt(0).findViewWithTag("Name")).getText();
    }

    private String getTagNameValue(String tagName) {
        try {
            JSONObject jsonObject = new JSONObject(itemJob);
            String jsonObjKey = jsonObject.getString(tagName);
            for (int j = 0; j < dependentDTOS.size(); j++) {
                DependentDTO dependentDTO = dependentDTOS.get(j);
                String dtoTagName = dependentDTO.getTagName();
                if (tagName.equalsIgnoreCase(dtoTagName)) {
                    ArrayList<String> keysList = dependentDTO.getKeyArray();
                    for (int k = 0; k < keysList.size(); k++) {
                        String kPositKey = keysList.get(k);
                        String key;
                        if (!kPositKey.contains("~")) {
                            key = kPositKey;
                        } else {
                            key = (kPositKey.substring(kPositKey.lastIndexOf("~") + 1));
                        }
                        if (key.equalsIgnoreCase(jsonObjKey)) {
                            ArrayList<String> valuesArray = dependentDTO.getValueArray();
                            if (valuesArray != null && valuesArray.size() > 0) {
                                String value = valuesArray.get(k);
                                if (value.contains("~")) {
                                    value = value.substring(value.lastIndexOf("~") + 1);
                                }
                                dependentDTO.setSelectedId(key);
                                dependentDTOS.set(j, dependentDTO);
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
        AlertDialogsUtils.showCustomAlertDialog(mContext, R.layout.single_selection_list, true, new AlertDialogsUtils.CustomAlertInterface() {
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
        AlertDialogsUtils.showCustomAlertDialog(mContext, R.layout.single_selection_list, true, new AlertDialogsUtils.CustomAlertInterface() {
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
                        for (int i = 0; i < dependentDTOS.size(); i++) {
                            if (dependentDTOS.get(i).getTagName().equalsIgnoreCase(tagName)) {
                                String dependentName = dependentDTOS.get(i).getDependentName();
                                String tempStrId;
                                if (selectedKeyId.contains("~")) {
                                    tempStrId = selectedKeyId.substring(selectedKeyId.indexOf("~") + 1, selectedKeyId.length());
                                } else {
                                    tempStrId = selectedKeyId;
                                }
                                removeDependents(tagName);
                                dependentDTOS.get(i).setSelectedId(tempStrId);
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
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void removeDependents(String tagName) {
        for (int i = 0; i < dependentDTOS.size(); i++) {
            DependentDTO dependentDTO = dependentDTOS.get(i);
            if (dependentDTO.getTagName().equalsIgnoreCase(tagName)) {
                String dependentName = dependentDTO.getDependentName();
                dependentDTO.setSelectedId("");
                dependentDTOS.set(i, dependentDTO);
                removeDependentsValue(dependentName);
            }
        }
    }

    private void removeDependentsValue(String dependentName) {
        for (int i = 0; i < dependentDTOS.size(); i++) {
            DependentDTO dependentDTO = dependentDTOS.get(i);
            if (dependentDTO.getTagName().equalsIgnoreCase(dependentName)) {
                String tagName = dependentDTO.getTagName();
                if (!TextUtils.isEmpty(tagName)) {
                    removeDependents(tagName);
                }
            }
        }
    }

    private void showMultiSelection(String tagName, final String[] strings, final TextView editText, final MultiSelectInterface multiSelectInterface) {
        AlertDialogsUtils.showCustomAlertDialog(mContext, R.layout.single_selection_list, true, new AlertDialogsUtils.CustomAlertInterface() {
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
//                lView.setOnItemClickListener(ServiceFormFragment.this);
                alertDialog.show();
            }
        });
    }

    private void showCameraGalleryAlert(final String tagName, final TextView textView) {
        AlertDialogsUtils.showAlertDialog(mContext, "Options", "Please select options for attachment.", "Camera", "Gallery", new AlertDialogsUtils.AlertDialogInterface() {
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
                AlertDialogsUtils.showAlertDialog(mContext, "Alert", "You can't set location without permissions.", "Continue", "Try Again", new AlertDialogsUtils.AlertDialogInterface() {
                    @Override
                    public void positiveButton() {
                    }

                    @Override
                    public void negativeButton() {
                        EasyPermissions.requestPermissions(ServiceFormFragment.this, "This app needs Location permission for address.", REQ_LOCATION_CODE, stringPerms);
                    }
                });
            }
        } else if (requestCode == REQ_CAMERA_CODE) {
            if (grantResults[0] == -1 || grantResults[1] == -1 || grantResults[2] == -1) {
                AlertDialogsUtils.showAlertDialog(mContext, "Alert", "You can't use Camera without permissions.", "Continue", "Try Again", new AlertDialogsUtils.AlertDialogInterface() {
                    @Override
                    public void positiveButton() {
                    }

                    @Override
                    public void negativeButton() {
                        EasyPermissions.requestPermissions(ServiceFormFragment.this, "This app needs Location permission for address.", REQ_LOCATION_CODE, stringPerms);
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
        serviceFormPresenter.onActivityResult(requestCode, resultCode, data, new ServiceFormActivity.ListenerInterface() {
            @Override
            public void onImageSelected(int requestCode, String fileName) {
            }
        });
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
