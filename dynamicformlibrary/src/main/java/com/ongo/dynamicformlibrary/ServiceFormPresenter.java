package com.ongo.dynamicformlibrary;

/*
 * Created by CX on 13-11-2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Spinner;

import com.ongo.dynamicformlibrary.asynctasks.FormAPICall;
import com.ongo.dynamicformlibrary.form_utils.FormDependentDTO;
import com.ongo.dynamicformlibrary.form_utils.FormServiceFieldsDto;
import com.ongo.dynamicformlibrary.form_utils.FormUploadFilePath;
import com.ongo.dynamicformlibrary.utils.FormUtils;
import com.ongo.dynamicformlibrary.utils.FormConstants;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.ongo.dynamicformlibrary.ServiceFormActivityForm.REQUEST_FILE_SELECT;


class ServiceFormPresenter implements ServiceFormDataSource.SaleRegisterDataSourceListener {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;
    private final int REQUEST_IMAGE = 301;
    private HashMap<String, File> hashMapFile = new HashMap<>();
    private ServiceFormDataSource serviceFormDataSource;
    private Context mContext;
    private ServiceFormListener saleRegisterListener;
    private Fragment fragment;
    private ArrayList<String> photosArray;

    ServiceFormPresenter(Fragment fragment, ServiceFormListener saleRegisterListener) {
        this.fragment = fragment;
        this.mContext = fragment.getContext();
        this.saleRegisterListener = saleRegisterListener;
        serviceFormDataSource = new ServiceFormDataSource(mContext, this);
    }

    ServiceFormPresenter(Context mContext, ServiceFormListener saleRegisterListener) {
        this.mContext = mContext;
        this.saleRegisterListener = saleRegisterListener;
        serviceFormDataSource = new ServiceFormDataSource(mContext, this);
    }

    void getImagesList() {
     /*   MultiImageSelector mMultiImageSelector = MultiImageSelector.create();
        if (checkAndRequestPermissions()) {
            mMultiImageSelector.showCamera(true);
            mMultiImageSelector.multi();
//            mMultiImageSelector.startCamera((Activity) mContext, REQUEST_IMAGE); //only camera
            if (fragment != null) {
                mMultiImageSelector.start(fragment, REQUEST_IMAGE); //show's images list and option for camera
            } else {
                mMultiImageSelector.start((Activity) mContext, REQUEST_IMAGE); //show's images list and option for camera
            }
        }*/
    }

    private boolean checkAndRequestPermissions() {
        int externalStoragePermission = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) mContext, listPermissionsNeeded
                    .toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            getImagesList();
        }
    }

    void onActivityResult(int requestCode, int resultCode, Intent data, ServiceFormActivityForm.ListenerInterface listenerInterface) {
        if (requestCode == REQUEST_IMAGE && data != null) {
         /*   ArrayList<String> imagesList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            if (imagesList != null && imagesList.size() > 0) {
//                imageSelectedCount++;
                new CompressImagesAsync(mContext, imagesList, new CompressImagesAsync.OnImagesCompressedListener() {
                    @Override
                    public void onCompressedImages(ArrayList<String> compressedImages) {
                        try {
                            if (compressedImages != null && compressedImages.size() > 0) {
                                saleRegisterListener.onImageSelected(compressedImages);
                                try {
                                    for (int i = 0; i < compressedImages.size(); i++) {
//                    Uri uri = imagesList.get(i);
                                        // To get selected path...
//                    FormUploadFilePath filePathClass = new FormUploadFilePath(mContext);
                                        String path = compressedImages.get(i);
                                        File file = new File(path);
                                        String filename = path.substring(path.lastIndexOf("/") + 1);
                                        hashMapFile.put(filename, file);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                FormConstants.mSelectedImagesList = compressedImages;
//                                FormSharedPref.writeArry(FormConstants.PREF_PHOTO_ARRY, compressedImages);
//
//                                if (FormConstants.mSelectedImagesList != null && FormConstants.mSelectedImagesList.size() > 0) {
//                                    isFtromAddPhotos = true;
//                                    Intent intent = new Intent(StatusImageDialogActivity.this, EditImageActivity.class);
//                                    intent.putExtra("PhotoUrlsList", FormConstants.mSelectedImagesList);
//                                    intent.putExtra("UserName", FormSharedPref.read(FormConstants.Name, ""));
//                                    intent.putExtra("Status", et_status_assignee.getText().toString());
//                                    startActivityForResult(intent, 1111);
//
//                                    Log.e("images", ">>>>>>>>>>" + FormConstants.mSelectedImagesList.toString());
//
//                                } else {
//                                    Log.e("no images", ">>>>>>>>>>");
//                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).execute();
            }*/
        } else if (requestCode == 1111 && data != null) {
            try {
//                //String photo = data.getStringExtra("Photo");
//                ArrayList<String> editedImagesList = data.getStringArrayListExtra("EditedPhotosList");
//                FormConstants.mSelectedImagesList.clear();
//                if (editedImagesList != null && editedImagesList.size() > 0) {
//                    if (isFtromAddPhotos) {
//                        if (imageSelectedCount <= 1) {
//                            FormConstants.mSelectedImagesList = editedImagesList;
//                        } else {
//                            ArrayList<String> existingImages = new ArrayList<>();
//                            if (horizontalAdapter != null && horizontalAdapter.getAllImages() != null
//                                    && horizontalAdapter.getAllImages().size() > 0) {
//                                existingImages = horizontalAdapter.getAllImages();
//                            }
//                            if (existingImages != null && existingImages.size() > 0) {
//                                for (String editedUrls : editedImagesList) {
//                                    if (!existingImages.contains(editedUrls)) {
//                                        existingImages.add(editedUrls);
//                                    }
//                                }
//                                FormConstants.mSelectedImagesList = existingImages;
//                            } else {
//                                FormConstants.mSelectedImagesList = editedImagesList;
//                            }
//                        }
//                    } else {
//                        FormConstants.mSelectedImagesList = editedImagesList;
//                    }
//                }
//                // imagesList.set(currentPhotoPosition, photo);
//                if (FormConstants.mSelectedImagesList != null && FormConstants.mSelectedImagesList.size() > 0) {
//                    //FormConstants.mSelectedImagesList.set(currentPhotoPosition, photo);
//                    FormSharedPref.writeArry(FormConstants.PREF_PHOTO_ARRY, FormConstants.mSelectedImagesList);
//
//                    if (FormConstants.mSelectedImagesList != null && FormConstants.mSelectedImagesList.size() > 0) {
//                        isImagesAdded = true;
//                        recycler_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//                        horizontalAdapter = new PhotoUploadAdapter(FormConstants.mSelectedImagesList, mContext);
//                        recycler_view.setAdapter(horizontalAdapter);
//                        Log.e("images", ">>>>>>>>>>" + FormConstants.mSelectedImagesList.toString());
//                    } else {
//                        isImagesAdded = false;
                Log.e("no images", ">>>>>>>>>>");
//                    }
//
//                    if (isStatusChanged && isImagesAdded) {
//                        upload_btn.setVisibility(View.VISIBLE);
//                        upload_btn.setText("Upload Image & Status");
//                    }
////                    else if (isStatusChanged && !isImagesAdded) {
////                        upload_btn.setVisibility(View.VISIBLE);
////                        upload_btn.setText("Upload Status");
////                    } else if (!isStatusChanged && isImagesAdded) {
////                        upload_btn.setVisibility(View.VISIBLE);
////                        upload_btn.setText("Upload Image");
////                    }
//                    else {
//                        upload_btn.setVisibility(View.GONE);
//                    }
//                } else {
//                    recycler_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//                    horizontalAdapter = new PhotoUploadAdapter(new ArrayList<String>(), mContext);
//                    recycler_view.setAdapter(horizontalAdapter);
//                }
//
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_FILE_SELECT && data != null) {
            Uri uri = data.getData();
            // To get selected path...
            FormUploadFilePath filePathClass = new FormUploadFilePath(mContext);
            String path = filePathClass.getFilePath(uri);
            File file = new File(path);
            String filename = path.substring(path.lastIndexOf("/") + 1);

            hashMapFile.put(filename, file);
            if (listenerInterface != null) {
                listenerInterface.onImageSelected(requestCode, filename);
            }

            // apiCall to upload profile image and get url
//            if (FormUtils.isNetworkAvailable(mContext)) {
//                mAttachment_name_tv.setText(filename);
            //uploadProfileImage(file, filename);
//                if (attachmentImageView != null) {
//                    FormServiceUtils.setImageBackground(mContext, attachmentImageView, true);
//                }
//                hashMap.put(upload_key_tv, filename);
//                isManadatory.put(upload_key_tv, "false");
//            } else {
//                FormUtils.toast(getResources().getString(R.string.nointernet), mContext);
//            }
        }
// else if (requestCode == 3210) {
//            if (data != null) {
//                Bundle bundle = data.getExtras();
//                double latitude = bundle.getDouble("latitude");
//                double longitude = bundle.getDouble("longitude");
//                String myAddress = bundle.getString("address");
//                String address1 = bundle.getString("address1");
//                String city = bundle.getString("city");
//                String country = bundle.getString("country");
//                String postalCode = bundle.getString("postalCode");
//                String address = "";
//                if (address1 != null) {
//                    address = address + ", " + address1;
//                }
//                if (city != null) {
//                    address = address + ", " + city;
//                }
//                if (country != null) {
//                    address = address + ", " + country;
//                }
//                if (postalCode != null) {
//                    address = address + ", " + postalCode;
//                }
//
//                locationTv.setText(address);
//            }
//        } else if (requestCode == IMAGE_CAMERA && data != null) {
//            File file = null;
//            String filename = "";
//            Uri uri = data.getData();
//            if (uri == null) {
//                Bundle extras = data.getExtras();
//                if (extras != null) {
//                    Bitmap bitmap = (Bitmap) extras.get("data");
//                    //create a file to write bitmap data
//                    file = new File(mContext.getCacheDir(), "temporary_file");
//                    try {
//                        file.createNewFile();
//                        //Convert bitmap to byte array
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//                        byte[] bitmapdata = bos.toByteArray();
//
//                        //write the bytes in file
//                        FileOutputStream fos = new FileOutputStream(file);
//                        fos.write(bitmapdata);
//                        fos.flush();
//                        fos.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    photoFile = null;
//                    mCurrentPhotoPath = "";
//                }
//            } else {
//                // To get selected path...
//                FormUploadFilePath filePathClass = new FormUploadFilePath(mContext);
//                String path = filePathClass.getFilePath(uri);
//                file = new File(path);
//                filename = path.substring(path.lastIndexOf("/") + 1);
//
//            }
//            // apiCall to upload profile image and get url
//            if (file != null) {
//                mAttachment_name_tv.setText(filename);
//                //  uploadProfileImage(file, filename);
//                hashMapFile.put(filename, file);
//
//                if (attachmentImageView != null) {
//                    FormServiceUtils.setImageBackground(mContext, attachmentImageView, true);
//                }
//                hashMap.put(upload_key_tv, filename);
//                isManadatory.put(upload_key_tv, "false");
//
//            } else {
//                photoFile = null;
//                mCurrentPhotoPath = "";
//                FormUtils.toast("Please Try Again.", mContext);
//            }
//        } else if (requestCode == IMAGE_CAMERA) {
//            if (photoFile != null && !mCurrentPhotoPath.equalsIgnoreCase("")) {
//                // apiCall to upload profile image and get url
//                String filename = mCurrentPhotoPath.substring(mCurrentPhotoPath.lastIndexOf("/") + 1);
//                mAttachment_name_tv.setText(filename);
//                hashMapFile.put(filename, photoFile);
//                hashMap.put(upload_key_tv, filename);
//                isManadatory.put(upload_key_tv, "false");
//
//                if (attachmentImageView != null) {
//                    FormServiceUtils.setImageBackground(mContext, attachmentImageView, true);
//                }
//                // uploadProfileImage(photoFile, filename);
//            } else {
//                FormUtils.toast("Please Try Again.", mContext);
//            }
// }
        else if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            if (mPaths.size() == 0) {
                FormUtils.toast("Please Try Again.", mContext);
                return;
            }

            for (int i = 0; i < mPaths.size(); i++) {
                Uri uri = Uri.parse(mPaths.get(i));
                File file = new File(uri.getPath());
                String filename = file.getName();

                hashMapFile.put(filename, file);
                if (listenerInterface != null) {
                    listenerInterface.onImageSelected(requestCode, filename);
                }
                // apiCall to upload profile image and get url
//                if (file != null) {
//                    if (mAttachment_name_tv != null) {
//                        mAttachment_name_tv.setText(filename);
//                    }
//                    //  uploadProfileImage(file, filename);
//
//                    if (attachmentImageView != null) {
//                        FormServiceUtils.setImageBackground(mContext, attachmentImageView, true);
//                    }
//                    hashMap.put(upload_key_tv, filename);
//                    isManadatory.put(upload_key_tv, "false");
//
//                } else {
//                    photoFile = null;
//                    mCurrentPhotoPath = "";
//                }
            }

            Log.e("this is images selected", ">>>>>>>>" + mPaths);
        }
//        else if (requestCode == INT_REQ_CODE_SIGNATURE_PAD && resultCode == Activity.RESULT_OK) {
//            //FormUtils.loadImage(mContext, data.getStringExtra("file"), signatureImageView);
//            if (!TextUtils.isEmpty(data.getStringExtra("file"))) {
//                Bitmap bitmap = BitmapFactory.decodeFile(data.getStringExtra("file"));
//                signatureImageView.setImageBitmap(bitmap);
//            }
//            signaturePath = data.getStringExtra("file");
//            Log.e("data is ", ">>>>>>>>..." + signatureImageView.getTag().toString());
//            // signatureImageView.setImageResource();
//        }
    }

    void getServiceFields(String baseUrl, String postType, String mallId,String dt) {
        serviceFormDataSource.getServiceFields(baseUrl, postType, mallId,dt);
    }

    @Override
    public void onResponse(ArrayList<FormServiceFieldsDto> formServiceFieldsDtos) {
        if (formServiceFieldsDtos.size() > 0) {
            saleRegisterListener.setLayout(formServiceFieldsDtos);
        }
    }

    String getDependencyNameList(ArrayList<FormServiceFieldsDto> formServiceFieldsDtos, String tagName) {
        for (int i = 0; i < formServiceFieldsDtos.size(); i++) {
            String name = formServiceFieldsDtos.get(i).getName();
            if (name.equalsIgnoreCase(tagName)) {
                return formServiceFieldsDtos.get(i).getDependentFields();
            }
        }
        return "";
    }

    FormDependentDTO checkDependency(ArrayList<FormDependentDTO> formDependentDTOS, String tagName) {
        for (int i = 0; i < formDependentDTOS.size(); i++) {
            FormDependentDTO formDependentDTO = formDependentDTOS.get(i);
            if (formDependentDTO.getDependentName().equalsIgnoreCase(tagName)) {
                return formDependentDTO;
            }
        }
        return null;
    }


    void checkValidation(String postType, HashMap<String, String> hashMap, HashMap<String, String> isManadatory) {
        if (hashMap.size() > 0) {
            boolean isMan = false;
            for (String key : hashMap.keySet()) {
                //force adding the selectedImage list image to hashMap
                if (key.contains("Image") || key.contains("Photo")) {
                    if (hashMapFile.size() > 0) {
                        //noinspection LoopStatementThatDoesntLoop
                        for (String name : hashMapFile.keySet()) {
                            hashMap.put(key, name);
                            break;
                        }
                    }
                }
                for (String keyNew : isManadatory.keySet()) {
                    if (key.equalsIgnoreCase(keyNew)) {
                        if (isManadatory.get(keyNew).equalsIgnoreCase("true")) {
                            if (hashMap.get(key).isEmpty()) {
                                if (keyNew.equalsIgnoreCase("Image")
                                        && hashMap.containsKey("ItemCode")
                                        && hashMapFile.isEmpty()) {
                                    break;
                                }
                                saleRegisterListener.showMessage("Please select " + key);
                                isMan = true;
                                break;
                            }
                        }
                    }
                }
                if (isMan) {
                    break;
                }
            }
            if (!isMan) {
                if (postType.equalsIgnoreCase("Operators")) {

                    JSONObject jObjContent = new JSONObject();
                    try {
                        for (String key : hashMap.keySet()) {
                            jObjContent.put(key, hashMap.get(key));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //   new UpdateProfile().updateProfileFields(mContext, jObjContent);

                } else {
                    postJob(postType, hashMap);
                }
            }
        }
    }


    private void postJob(String postType, final HashMap<String, String> hashMap) {

        try {
            addAllFiles();

            JSONArray jArrayList = new JSONArray();
            JSONObject jObjContent = new JSONObject();
            JSONObject jObjList = new JSONObject();

            for (String key : hashMap.keySet()) {
                jObjContent.put(key, hashMap.get(key));
            }
            jArrayList.put(jObjContent);
            jObjList.put("list", jArrayList);

            HashMap<String, String> hashMapString = new HashMap<>();
            hashMapString.put("type", postType);
            hashMapString.put("json", jObjList.toString());
            hashMapString.put("dt", "CAMPAIGNS");
            hashMapString.put("category", "Products");

            hashMapString.put("userId", FormConstants.getMallId());
            hashMapString.put("consumerEmail", FormConstants.getConsumerEmail()); //as user sign up by mobileNumber and postfix as '@ongo.com'.

            new FormAPICall(FormConstants.postServices(), hashMapFile, hashMapString, mContext, new FormAPICall.APIResponse() {
                @Override
                public void onResponse(String result) {

                    if (result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("myHashMap");
                            String jobId = jsonObject1.getString("jobId");
                            if (hashMapFile.size() > 0) {
                                uploadMultipleImages(hashMapFile, jobId, hashMap.containsKey("ItemCode"));
                            } else {
                                saleRegisterListener.onResponse("1", result);
                            }
                        } catch (Exception e) {
                            Log.e("exp e is", ">>>>>>>>>.." + e.getLocalizedMessage());
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            Log.e("exception here", ">>>>>>>>>>>>" + e.getLocalizedMessage());
        }
    }

    private void uploadMultipleImages(HashMap<String, File> fileHashMapFiles, String jobId, boolean isItemCode) {
        HashMap<String, String> filehashmap = new HashMap<>();
        filehashmap.put("jobId", jobId);
        filehashmap.put("orgId", FormConstants.getMallId());
        Log.e("fileHashMapFiles", ">>>>>>>>.." + fileHashMapFiles);
        if (isItemCode) {
            deleteAttachments(jobId, fileHashMapFiles, filehashmap);
        } else {
            uploadAttachments(fileHashMapFiles, filehashmap);
        }
    }


    private void uploadAttachments(final HashMap<String, File> fileHashMapFiles, final HashMap<String, String> fileHashMap) {
        new FormAPICall(FormConstants.uploadAttachments(), fileHashMapFiles, fileHashMap, mContext, new FormAPICall.APIResponse() {
            @Override
            public void onResponse(String result) {
                if (result == null || result.equalsIgnoreCase("")) {
                    FormUtils.toast("Could not upload your images", mContext);
                } else {
                    ArrayList<String> arry = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String status = jsonObject.getString("status");
//                        if (status.equalsIgnoreCase("1")) {
//                            JSONArray jsonArray = jsonObject.getJSONArray("attachments");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                String imageUrl = jsonArray.getString(i);
//                                arry.add(imageUrl);
//                            }
//                        }
                        saleRegisterListener.onResponse(status, result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).execute();
    }

    private void deleteAttachments(String jobId, final HashMap<String, File> fileHashMapFiles, final HashMap<String, String> fileHashMap) {
        String url = FormConstants.deleteAllAttachments() + "jobId=" + jobId;
        new FormAPICall(url, fileHashMapFiles, fileHashMap, mContext, new FormAPICall.APIResponse() {
            @Override
            public void onResponse(String result) {
                if (result == null || result.equalsIgnoreCase("")) {
                    FormUtils.toast("Could not upload your images", mContext);
                } else {
                    ArrayList<String> arry = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String status = jsonObject.getString("status");
                        if (status.equalsIgnoreCase("1")) {
                            uploadAttachments(fileHashMapFiles, fileHashMap);
//                            JSONArray jsonArray = jsonObject.getJSONArray("attachments");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                String imageUrl = jsonArray.getString(i);
//                                arry.add(imageUrl);
//                            }
                        }
                        saleRegisterListener.onResponse(status,result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).execute();
    }

    HashMap<String, String> getHashMap(String itemObj) {
        HashMap<String, String> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(itemObj);
            Iterator<String> keySet = jsonObject.keys();

            while (keySet.hasNext()) {
                //based on you key types
                String keyStr = keySet.next();
                Object keyValue = jsonObject.get(keyStr);
                if (FormUtils.checkType(keyStr)) {
                    map.put(keyStr, FormUtils.removeBracket(String.valueOf(keyValue)));
                }
            }

            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    HashMap<String, String> getImagesHashMap(String itemObj) {
        HashMap<String, String> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(itemObj);
            JSONArray jsonArray = jsonObject.getJSONArray("Attachments");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject innerObject = jsonArray.getJSONObject(i);
                String keyName = FormUtils.checkJsonObjStr(innerObject, "Image_Name");
                String keyValue = FormUtils.checkJsonObjStr(innerObject, "URL");
                map.put(keyName, keyValue);
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    int getSelectedItem(Spinner categorySpinner, String str) {
        for (int i = 0; i < categorySpinner.getCount(); i++) {
            if (categorySpinner.getAdapter().getItem(i).equals(str)) {
                return i;
            }
        }
        return 0;
    }

    void getHashMapFile(HashMap<String, File> editFieldsImagesHashMap) {
        hashMapFile.putAll(editFieldsImagesHashMap);
    }


    void addAllFiles() {
        if (photosArray != null && photosArray.size() > 0) {
            for (String filePath : photosArray) {
                File file = new File(filePath);
                String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
                hashMapFile.put(filename, file);
            }
        }
    }

    void setPhotosArray(ArrayList<String> filePaths) {
        photosArray = filePaths;
    }

    interface ServiceFormListener {
        void onResponse(String status, String result);

        void onImageSelected(ArrayList<String> imagesList);

        void setLayout(ArrayList<FormServiceFieldsDto> formServiceFieldsDtos);

        void showMessage(String msg);
    }
}

