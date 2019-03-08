package com.ongo.dynamicformlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gufran khan on 30-10-2018.
 */

public abstract class FormBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        registerListeners();
    }

    protected abstract void initViews();

    protected abstract void registerListeners();

    /**
     * This is to add/replace the new fragment in view.
     *
     * @param isInit         boolean for add/replace the fragment
     * @param contentFrame   frame layout in which the parse fragment will be added
     * @param screenFragment fragment which will be adding to contentFrame
     * @param tagName        for fragment
     */

    public void startTransaction(boolean isInit, int contentFrame, Fragment screenFragment, String tagName) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        if (isInit) {
            fragmentTransaction.add(contentFrame, screenFragment, tagName);
        } else {
            fragmentTransaction.replace(contentFrame, screenFragment, tagName);
        }
        fragmentTransaction.commit();

    }


}
