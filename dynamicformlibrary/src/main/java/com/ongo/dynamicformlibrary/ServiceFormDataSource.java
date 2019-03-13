package com.ongo.dynamicformlibrary;

/*
 * Created by CX on 14-11-2018.
 */

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.ongo.dynamicformlibrary.asynctasks.FormWebServices;
import com.ongo.dynamicformlibrary.form_utils.FormMyMap;
import com.ongo.dynamicformlibrary.form_utils.FormServiceFieldsDto;
import com.ongo.dynamicformlibrary.utils.FormUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class ServiceFormDataSource {

    private ArrayList<FormServiceFieldsDto> formServiceFieldsDtos;
    private Context mContext;
    private SaleRegisterDataSourceListener sourceListener;

    ServiceFormDataSource(Context mContext, SaleRegisterDataSourceListener sourceListener) {
        this.mContext = mContext;
        this.sourceListener = sourceListener;
    }

    void getServiceFields(String baseUrl, String postType, String mallId,String dt) {
        FormWebServices ws = new FormWebServices(mContext);
        String url = /*FormUtils.getBaseUrl(mContext)*/ baseUrl+ "/Services/getMasters";
        RequestParams params = new RequestParams();
        params.put("type", "allJobTypes|"+postType);
        params.put("mallId", mallId); //FormUtils.getMallId(mContext));
        params.put("dt", dt);
        ws.invokeWebService(params, url, true, new FormWebServices.WSResponse() {
            @Override
            public void onResponse(boolean success, String response) {
                if (success) {
                    if (!response.isEmpty()) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray jArray = jObj.getJSONArray("orgs");
                            if (jArray.length() > 0) {
                                formServiceFieldsDtos = new ArrayList<>();

                                JSONObject jInner = jArray.getJSONObject(0);
                                JSONArray jFiledsArray = jInner.getJSONArray("Fields");

                                if (jFiledsArray.length() > 0) {
                                    for (int i = 0; i < jFiledsArray.length(); i++) {
                                        FormServiceFieldsDto formServiceFieldsDto = new FormServiceFieldsDto();

                                        if (jInner.has("type")) {
                                            formServiceFieldsDto.setServiceType(jInner.getString("type"));
                                        }

                                        JSONObject jInnerFields = jFiledsArray.getJSONObject(i);
                                        if (jInnerFields.has("name")) {
                                            formServiceFieldsDto.setName(jInnerFields.getString("name"));
                                        }
                                        if (jInnerFields.has("addMore")) {
                                            formServiceFieldsDto.setAddMore(String.valueOf(jInnerFields.getBoolean("addMore")));
                                        }
                                        if (jInnerFields.has("type")) {
                                            formServiceFieldsDto.setType(jInnerFields.getString("type"));
                                        }
                                        if (jInnerFields.has("mandatory")) {
                                            formServiceFieldsDto.setMandatory(jInnerFields.getString("mandatory"));
                                        }
                                        if (jInnerFields.has("allowedValues")) {
                                            formServiceFieldsDto.setAllowedValues(jInnerFields.getString("allowedValues"));
                                        }
                                        if (jInnerFields.has("allowedValuesResults")) {

                                            JSONObject jAllowedResults = jInnerFields.getJSONObject("allowedValuesResults");

                                            Type typeOfObjectsListNew = new TypeToken<HashMap<String, String>>() {
                                            }.getType();

                                            ArrayList<FormMyMap> arry = new ArrayList<>();
                                            FormMyMap map = new FormMyMap();
                                            map.setKey(jAllowedResults.toString());
                                            map.setValue(typeOfObjectsListNew.toString());
                                            arry.add(map);

                                            formServiceFieldsDto.setAllowedValuesResults(arry);
                                        }
                                        if (jInnerFields.has("multiselect")) {
                                            formServiceFieldsDto.setMultiselect(jInnerFields.getString("multiselect"));
                                        }
                                        if (jInnerFields.has("groupName")) {
                                            formServiceFieldsDto.setGroupName(jInnerFields.getString("groupName"));
                                        }
                                        if (jInnerFields.has("dependentFields")) {
                                            formServiceFieldsDto.setDependentFields(jInnerFields.getString("dependentFields"));
                                        }
                                        if (jInnerFields.has("propgateValueToSubFormFields")) {
                                            formServiceFieldsDto.setPropgateValueToSubFormFields(jInnerFields.getString("propgateValueToSubFormFields"));
                                        }
                                        if (jInnerFields.has("allowedValuesResults")) {
                                            JSONObject jsonObject = jInnerFields.getJSONObject("allowedValuesResults");
                                            Iterator<?> keys = jsonObject.keys();
                                            ArrayList<FormMyMap> arry = new ArrayList<>();
                                            while (keys.hasNext()) {
                                                FormMyMap map = new FormMyMap();
                                                String key = (String) keys.next();
                                                map.setKey(key);
                                                if (jsonObject.get(key) instanceof String) {
                                                    String value = jsonObject.getString(key);
                                                    map.setValue(value);
                                                    arry.add(map);
                                                }
                                            }
                                            formServiceFieldsDto.setAllowedValuesResults(arry);
                                        }
                                        formServiceFieldsDtos.add(formServiceFieldsDto);
                                    }
                                    sourceListener.onResponse(formServiceFieldsDtos);
                                } else {
                                    FormUtils.toast("No Form", mContext);
                                }
                            } else {
                                FormUtils.toast("No Form", mContext);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    interface SaleRegisterDataSourceListener {
        void onResponse(ArrayList<FormServiceFieldsDto> formServiceFieldsDtos);
    }

}
