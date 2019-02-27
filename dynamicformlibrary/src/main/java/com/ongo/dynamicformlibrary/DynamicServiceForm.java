package com.ongo.dynamicformlibrary;

/*
 * Created by Adil on 27-02-2019.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.ongo.dynamicformlibrary.utils.OnGoConstants;

import java.io.File;
import java.util.HashMap;

public class DynamicServiceForm {

    private FragmentActivity activity;
    private int contentFrame;

    /**
     * @param activity     to set fragment using FragmentManager (ex: getActivity())
     * @param contentFrame layout id to set UI(ex: R.id.contentFrame)
     * @param baseUrl      IP address for the api call(ex: 123.123.123.123:8081)
     * @param mallId       from with mall, required the data (ex: 46)
     */
    public DynamicServiceForm(FragmentActivity activity, int contentFrame, String baseUrl, String mallId, String consumerEmail) {
        this.activity = activity;
        this.contentFrame = contentFrame;
        OnGoConstants.setHostUrl(baseUrl);
        OnGoConstants.setConsumerEmail(consumerEmail);
        OnGoConstants.setMallId(mallId);
    }

    /**
     * @param postType the type of for required (ex: "Posts")
     * @param itemJobObj if data is already present (for edit mode)
     */
    public void getForm(String postType, String itemJobObj) {
        createFragment(postType, itemJobObj);
    }

    public void onSuccess(HashMap<String, String> dataHashMap, HashMap<String, File> filesHashMap) {

    }


    /**
     * This is to add/replace the new fragment in view.
     *
     * @param isInit         boolean for add/replace the fragment
     * @param contentFrame   frame layout in which the parse fragment will be added
     * @param screenFragment fragment which will be adding to contentFrame
     * @param tagName        for fragment
     */

    private void startTransaction(FragmentActivity fragActivity, boolean isInit, int contentFrame, Fragment screenFragment, String tagName) {
        FragmentManager fragmentManager = fragActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        if (isInit) {
            fragmentTransaction.add(contentFrame, screenFragment, tagName);
        } else {
            fragmentTransaction.replace(contentFrame, screenFragment, tagName);
        }
        fragmentTransaction.commit();

    }

    private void createFragment(String postType, String itemJobObj) {
        ServiceFormFragment serviceFormFragment = new ServiceFormFragment();
        Bundle bundle = new Bundle();
        bundle.putString(OnGoConstants.postType, postType);
        if (!TextUtils.isEmpty(itemJobObj)){
            bundle.putString(OnGoConstants.jobObj, itemJobObj);
        }
        serviceFormFragment.setArguments(bundle);
        startTransaction(activity, false, contentFrame, serviceFormFragment, postType);
    }

    public interface DynamicServiceFormListener {
        public void onSuccess(HashMap<String, String> dataHashMap, HashMap<String, File> filesHashMap);
    }


}
