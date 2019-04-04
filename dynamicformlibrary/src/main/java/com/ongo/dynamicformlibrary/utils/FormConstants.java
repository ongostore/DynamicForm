package com.ongo.dynamicformlibrary.utils;

import java.util.HashMap;

/**
 * @author ONGO-0006
 */
public class FormConstants {
    public static final String PREF_NAME = "PREF_NAME";
    public static final String PREF_PROFILE_IMAGE = "PREF_PROFILE_IMAGE";
    public static String server;
    public static HashMap<String, String> editFieldsHASHMAP = new HashMap<>();
    public static HashMap<String, String> editFieldsImagesHashMap = new HashMap<>();
    public static HashMap<String,String> customValues=null;
    public static final String postType = "postType";
    public static String jobObj = "jobObj";
    public static String dt = "dt";

    public static String mallId;

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        FormConstants.userId = userId;
    }

    public static String userId;
    private static String consumerEmail;
    public static String category="category";


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
        return server;
    }

    public static void setHostUrl(String hostUrl) {
        server = hostUrl;
    }

    public static void setConsumerEmail(String consumerEmail) {
        FormConstants.consumerEmail = consumerEmail;
    }

    public static String getConsumerEmail() {
        return consumerEmail;
    }

    public static String getMallId() {
        return mallId;
    }

    public static void setMallId(String mallId) {
        FormConstants.mallId = mallId;
    }


    public static String uploadFile() {
        return getHostUrl() + "/Services/uploadFileFromAndroidToServer";
    }
}
