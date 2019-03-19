package com.ongo.dynamicformlibrary;

/*
 * Created by CX on 13-11-2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Spinner;

import com.ongo.dynamicformlibrary.asynctasks.FormAPICall;
import com.ongo.dynamicformlibrary.form_utils.FormDependentDTO;
import com.ongo.dynamicformlibrary.form_utils.FormServiceFieldsDto;
import com.ongo.dynamicformlibrary.utils.FormConstants;
import com.ongo.dynamicformlibrary.utils.FormUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


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


    void checkValidation(String postType, HashMap<String, String> hashMap, HashMap<String, String> isManadatory,String dt,String category) {
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
                    postJob(postType, hashMap,dt,category);
                }
            }
        }
    }


    private void postJob(String postType, final HashMap<String, String> hashMap,String dt,String category) {

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
            hashMapString.put("dt", dt);
            hashMapString.put("category", category);

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

