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

import com.ongo.dynamicformlibrary.utils.FormConstants;

public class DynamicServiceForm {

    private FragmentActivity activity;
    private int contentFrame;
    private DynamicServiceFormListener dynamicServiceFormListener;

    /**
     * @param activity                   to set fragment using FragmentManager (ex: getActivity())
     * @param contentFrame               layout id to set UI(ex: R.id.contentFrame)
     * @param baseUrl                    IP address for the api call(ex: 123.123.123.123:8081)
     * @param mallId                     from with mall, required the data (ex: 46)
     * @param dynamicServiceFormListener for sending status.
     */
    public DynamicServiceForm(FragmentActivity activity, int contentFrame, String baseUrl, String mallId, String consumerEmail, DynamicServiceFormListener dynamicServiceFormListener) {
        this.activity = activity;
        this.contentFrame = contentFrame;
        this.dynamicServiceFormListener = dynamicServiceFormListener;
        FormConstants.setHostUrl(baseUrl);
        FormConstants.setConsumerEmail(consumerEmail);
        FormConstants.setMallId(mallId);
    }

    /**
     * @param postType   the type of for required (ex: "Posts")
     * @param itemJobObj if data is already present (for edit mode)
     */
    public void getForm(String postType, String itemJobObj) {
        createFragment(postType, itemJobObj);
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

    /**
     * @param postType   the type of for required (ex: "Posts")
     * @param itemJobObj if data is already present (for edit mode)
     *                   after getting form by using post. setting the view.
     */
    private void createFragment(String postType, String itemJobObj) {
        ServiceFormFragmentForm serviceFormFragment = new ServiceFormFragmentForm(dynamicServiceFormListener);
        Bundle bundle = new Bundle();
        bundle.putString(FormConstants.postType, postType);
        if (!TextUtils.isEmpty(itemJobObj)) {
            bundle.putString(FormConstants.jobObj, itemJobObj);
        }
        serviceFormFragment.setArguments(bundle);
        boolean isInit =false;
        startTransaction(activity, isInit, contentFrame, serviceFormFragment, postType);
    }

    public interface DynamicServiceFormListener {
        void onSuccess(String status);
    }


}
