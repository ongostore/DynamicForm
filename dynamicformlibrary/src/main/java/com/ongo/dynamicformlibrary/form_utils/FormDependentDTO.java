package com.ongo.dynamicformlibrary.form_utils;

/*
 * Created by CX on 27-11-2018.
 */

import android.widget.TextView;

import java.util.ArrayList;

public class FormDependentDTO {
    String tagName;
    String dependentName;
    String selectedId;
    TextView textView;
    ArrayList<String> keyArray;
    private ArrayList<String> valueArray;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getDependentName() {
        return dependentName;
    }

    public void setDependentName(String dependentName) {
        this.dependentName = dependentName;
    }

    public String getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ArrayList<String> getKeyArray() {
        return keyArray;
    }

    public void setKeyArray(ArrayList<String> keyArray) {
        this.keyArray = keyArray;
    }

    public void setValueArray(ArrayList<String> valueArray) {
        this.valueArray = valueArray;
    }

    public ArrayList<String> getValueArray() {
        return valueArray;
    }
}
