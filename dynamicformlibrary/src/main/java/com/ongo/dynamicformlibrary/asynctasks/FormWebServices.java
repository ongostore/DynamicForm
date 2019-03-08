package com.ongo.dynamicformlibrary.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class FormWebServices extends Activity {
    private ACProgressFlower dialog;
    private Context context;

    public FormWebServices(Context c) {
        this.context = c;
    }

    public FormWebServices() {
    }

    /**
     * Method that performs RESTFull webservice invocations
     *
     * @param params       requestedParams
     * @param urlPath      Url of Api
     * @param showProgress to show progress dialog
     * @param wsResponse   response Listener
     */
    public void invokeWebService(RequestParams params, final String urlPath, final boolean showProgress, final WSResponse wsResponse) {
        // Make RESTFull webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(30, 1000 * 900);
        if (showProgress) {
            showProgress();
        }
        client.get(urlPath, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    if (showProgress) {
                        hideProgress();
                    }
                    String str = new String(responseBody, "UTF-8");
                    wsResponse.onResponse(true, str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                if (showProgress) {
                    hideProgress();
                }
                Log.e("WebService", "onFailure: >>>>>>>>  " + Arrays.toString(responseBody));
                wsResponse.onResponse(false, "");
            }
        });
    }

    void hideProgress() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                dialog.dismiss();
            }
        }
    }

    void showProgress() {
        try {
            dialog = new ACProgressFlower.Builder(context)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("Loading...")
                    .fadeColor(Color.DKGRAY).build();
            if (dialog != null && !dialog.isShowing())
                dialog.show();
        } catch (Exception e) {
            Log.e("WebService", "" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public interface WSResponse {
        void onResponse(boolean success, String response);
    }
}
