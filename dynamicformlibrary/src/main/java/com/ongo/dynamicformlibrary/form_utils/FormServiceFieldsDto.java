package com.ongo.dynamicformlibrary.form_utils;

import java.util.ArrayList;

/*
 * Created by yasaswy on 03-01-2018.
 */

public class FormServiceFieldsDto {

    public String name;
    public String type;
    public String mandatory;
    public String allowedValues;
    private String multiselect;
    public String groupName;
    private String dependentFields;
    private String propgateValueToSubFormFields;
    private String addMore;
    public String id;
    private String mask;
    private String incrementalSearch;
    private String serviceType;
    private ArrayList<FormMyMap> allowedValuesResults;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(String allowedValues) {
        this.allowedValues = allowedValues;
    }

    public String getMultiselect() {
        return multiselect;
    }

    public void setMultiselect(String multiselect) {
        this.multiselect = multiselect;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDependentFields() {
        return dependentFields;
    }

    public void setDependentFields(String dependentFields) {
        this.dependentFields = dependentFields;
    }

    public String getPropgateValueToSubFormFields() {
        return propgateValueToSubFormFields;
    }

    public void setPropgateValueToSubFormFields(String propgateValueToSubFormFields) {
        this.propgateValueToSubFormFields = propgateValueToSubFormFields;
    }

    public String getAddMore() {
        return addMore;
    }

    public void setAddMore(String addMore) {
        this.addMore = addMore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getIncrementalSearch() {
        return incrementalSearch;
    }

    public void setIncrementalSearch(String incrementalSearch) {
        this.incrementalSearch = incrementalSearch;
    }

    public ArrayList<FormMyMap> getAllowedValuesResults() {
        return allowedValuesResults;
    }

    public void setAllowedValuesResults(ArrayList<FormMyMap> allowedValuesResults) {
        this.allowedValuesResults = allowedValuesResults;
    }
}
