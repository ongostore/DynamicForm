package com.ongo.dynamicformlibrary.asynctasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
 * Created by kushal on 26/9/17.
 */

public class APICall extends AsyncTask<Void, Void, Void> {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String result;
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private OkHttpClient client = new OkHttpClient();
    //private final String credential;
    private Object object;
    private APIResponse apiResponse;
    private HashMap<String, String> hashMapJson = null;
    private HashMap<String, File> hashMapFiles = null;
    private String json;
    private String url;
    private boolean isDialogCancelled;
    private ACProgressFlower dialog;
    private boolean showProgress;

    // basic method for call back
    public APICall(String json, String url, Object object) {
        this.json = json;
        this.url = url;
        this.object = object;
        apiResponse = (APIResponse) object;
        mContext = (Context) object;
        //credential = Credentials.basic(OnGoConstants.AUTH_USERNAME, OnGoConstants.AUTH_PASSWORD);
    }

    // for multi part upload
    public APICall(String url, HashMap<String, File> hashMapFiles
            , HashMap<String, String> hashMapJson, Object object) {

        this.url = url;
        this.object = object;
        apiResponse = (APIResponse) object;
        mContext = (Context) object;
        //credential = Credentials.basic(OnGoConstants.AUTH_USERNAME, OnGoConstants.AUTH_PASSWORD);
        this.hashMapJson = hashMapJson;
        this.hashMapFiles = hashMapFiles;
    }

    // to be used in frag
    public APICall(String json, String url, Object object, Context mContext) {
        this.json = json;
        this.url = url;
        this.object = object;
        apiResponse = (APIResponse) object;
        this.mContext = mContext;
        //credential = Credentials.basic(OnGoConstants.AUTH_USERNAME, OnGoConstants.AUTH_PASSWORD);
    }


    // to be used in adapter or getting response in self listener
    public APICall(String json, String url, Context mContext, boolean showProgress, APIResponse apiResponse) {
        this.json = json;
        this.url = url;
        this.mContext = mContext;
        this.showProgress = showProgress;
        //credential = Credentials.basic(OnGoConstants.AUTH_USERNAME, OnGoConstants.AUTH_PASSWORD);
        this.apiResponse = apiResponse;
        Log.e("APICall url here is", ">>>>>" + url);
    }

    public APICall(String url, HashMap<String, File> hashMapFiles
            , HashMap<String, String> hashMapJson, Object object, APIResponse apiResponse) {

        this.url = url;
        this.object = object;
        this.apiResponse = apiResponse;
        mContext = (Context) object;
        //credential = Credentials.basic(OnGoConstants.AUTH_USERNAME, OnGoConstants.AUTH_PASSWORD);
        this.hashMapJson = hashMapJson;
        this.hashMapFiles = hashMapFiles;
    }


    private String post(String json, String url) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                //.header("Authorization", credential)
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String postMultiPart(String url, HashMap<String, File> hashMapFiles, HashMap<String, String> hashMapJson) throws IOException {
        MultipartBody.Builder multiBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        // add type entity
        for (String key : hashMapJson.keySet()) {
            multiBuilder.addFormDataPart(key, hashMapJson.get(key));
        }
        // add type entity
        if (hashMapFiles != null) {
            MediaType mediaType = MediaType.parse("image/jpg");
            for (String key : hashMapFiles.keySet()) {
                if (hashMapFiles.get(key) != null) {
                    String fileName = hashMapFiles.get(key).getName();
                    multiBuilder.addFormDataPart("srcFile", fileName, RequestBody.create(mediaType, hashMapFiles.get(key)));
                }
            }
        }

        RequestBody requestBody = multiBuilder.build();
        Request request = new Request.Builder()

                //.header("Authorization", credential)
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                //.header("Authorization", credential)
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showProgress) {

            dialog = new ACProgressFlower.Builder(mContext)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("Loading...")
                    .fadeColor(Color.DKGRAY).build();
            if (dialog != null && !dialog.isShowing())
                dialog.show();
        }
    }


    @Override
    protected Void doInBackground(Void... voids) {
        result = "";

        //.if multipart
        if (hashMapJson != null) {

            try {
                result = postMultiPart(url, hashMapFiles, hashMapJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // if post
            if (json != null) {
                try {
                    result = post(json, url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    result = get(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (showProgress) {
            if (dialog != null && dialog.isShowing()) {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    dialog.dismiss();
                }
            }
        }

        apiResponse.onResponse(result);
        super.onPostExecute(aVoid);
    }

    public interface APIResponse {
        void onResponse(String result);
    }
}
