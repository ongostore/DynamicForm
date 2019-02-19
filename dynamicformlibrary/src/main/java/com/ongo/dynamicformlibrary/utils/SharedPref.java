package com.ongo.dynamicformlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPref {
    private static SharedPreferences mSharedPref;
    private static Context mContext;

    public static void init(Context context) {
        mContext=context;
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(OnGoConstants.PREF_NAME, Activity.MODE_PRIVATE);
    }

    public static String read(String key, String defValue) {
        if (mSharedPref == null)
            mSharedPref = mContext.getSharedPreferences(OnGoConstants.PREF_NAME, Activity.MODE_PRIVATE);

        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static boolean read(String key, boolean defValue) {


        if (mSharedPref == null)
            mSharedPref = mContext.getSharedPreferences(OnGoConstants.PREF_NAME, Activity.MODE_PRIVATE);

        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

}