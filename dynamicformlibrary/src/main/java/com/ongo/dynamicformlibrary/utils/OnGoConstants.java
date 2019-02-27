package com.ongo.dynamicformlibrary.utils;

import android.content.Context;

import com.ongo.dynamicformlibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ONGO-0006
 */
public class OnGoConstants {
    public static final String PREF_NAME = "PREF_NAME";
    public static final String PREF_PROFILE_IMAGE = "PREF_PROFILE_IMAGE";
    public static String server_api_url;
    public static HashMap<String, String> editFieldsHASHMAP = new HashMap<>();
    public static HashMap<String, String> editFieldsImagesHashMap = new HashMap<>();
    public static final String postType = "postType";
    public static String jobObj = "jobObj";

    public String baseUrl;
    public static String mallId;
    private static String consumerEmail;


    public static String uploadAttachments() {
        return getHostUrl() + "/Services/uploadMultipleFiles?";
    }
    public static String deleteAllAttachments() {
        return getHostUrl() + "/MobileAPIs/deleteJobAttachments?";
    }

    public static String postServices() {
        return getHostUrl() + "/MobileAPIs/postedJobs?";
    }


    public static String getHostUrl() {
        return server_api_url;
    }

    public static void setHostUrl(String hostUrl) {
        server_api_url = hostUrl;
    }

    public static void setConsumerEmail(String consumerEmail) {
        OnGoConstants.consumerEmail = consumerEmail;
    }

    public static String getConsumerEmail() {
        return consumerEmail;
    }

    public static String getMallId() {
        return mallId;
    }

    public static void setMallId(String mallId) {
        OnGoConstants.mallId = mallId;
    }


    public static String uploadFile() {
        return getHostUrl() + "/Services/uploadFileFromAndroidToServer";
    }
}
