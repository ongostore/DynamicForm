package com.ongo.dynamicformlibrary;

/*
 * Created by CX on 14-11-2018.
 */

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.ongo.dynamicformlibrary.asynctasks.WebServices;
import com.ongo.dynamicformlibrary.form_utils.MyMap;
import com.ongo.dynamicformlibrary.form_utils.ServiceFieldsDto;
import com.ongo.dynamicformlibrary.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class ServiceFormDataSource {

    private ArrayList<ServiceFieldsDto> serviceFieldsDtos;
    private Context mContext;
    private SaleRegisterDataSourceListener sourceListner;

    public ServiceFormDataSource(Context mContext, SaleRegisterDataSourceListener sourceListner) {
        this.mContext = mContext;
        this.sourceListner = sourceListner;
    }

    public void getServiceFields(String postType) {
        WebServices ws = new WebServices(mContext);
        String url = Utils.getBaseUrl(mContext) + "/Services/getMasters";
        RequestParams params = new RequestParams();
        params.put("type", "allJobTypes|"+postType);
        params.put("mallId", Utils.getMallId(mContext));
        params.put("dt", "CAMPAIGNS");
        ws.invokeWebService(params, url, true, new WebServices.WSResponse() {
            @Override
            public void onResponse(boolean success, String response) {
                if (success) {
                    if (!response.isEmpty()) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray jArray = jObj.getJSONArray("orgs");
                            if (jArray.length() > 0) {
                                serviceFieldsDtos = new ArrayList<>();

                                JSONObject jInner = jArray.getJSONObject(0);
                                JSONArray jFiledsArray = jInner.getJSONArray("Fields");

                                if (jFiledsArray.length() > 0) {
                                    for (int i = 0; i < jFiledsArray.length(); i++) {
                                        ServiceFieldsDto serviceFieldsDto = new ServiceFieldsDto();

                                        if (jInner.has("type")) {
                                            serviceFieldsDto.setServiceType(jInner.getString("type"));
                                        }

                                        JSONObject jInnerFileds = jFiledsArray.getJSONObject(i);
                                        if (jInnerFileds.has("name")) {
                                            serviceFieldsDto.setName(jInnerFileds.getString("name"));
                                        }
                                        if (jInnerFileds.has("addMore")) {
                                            serviceFieldsDto.setAddMore(String.valueOf(jInnerFileds.getBoolean("addMore")));
                                        }
                                        if (jInnerFileds.has("type")) {
                                            serviceFieldsDto.setType(jInnerFileds.getString("type"));
                                        }
                                        if (jInnerFileds.has("mandatory")) {
                                            serviceFieldsDto.setMandatory(jInnerFileds.getString("mandatory"));
                                        }
                                        if (jInnerFileds.has("allowedValues")) {
                                            serviceFieldsDto.setAllowedValues(jInnerFileds.getString("allowedValues"));
                                        }
                                        if (jInnerFileds.has("allowedValuesResults")) {

                                            JSONObject jAllowedResults = jInnerFileds.getJSONObject("allowedValuesResults");

                                            Type typeOfObjectsListNew = new TypeToken<HashMap<String, String>>() {
                                            }.getType();

                                            ArrayList<MyMap> arry = new ArrayList<>();
                                            MyMap map = new MyMap();
                                            map.setKey(jAllowedResults.toString());
                                            map.setValue(typeOfObjectsListNew.toString());
                                            arry.add(map);

                                            serviceFieldsDto.setAllowedValuesResults(arry);
                                        }
                                        if (jInnerFileds.has("multiselect")) {
                                            serviceFieldsDto.setMultiselect(jInnerFileds.getString("multiselect"));
                                        }
                                        if (jInnerFileds.has("groupName")) {
                                            serviceFieldsDto.setGroupName(jInnerFileds.getString("groupName"));
                                        }
                                        if (jInnerFileds.has("dependentFields")) {
                                            serviceFieldsDto.setDependentFields(jInnerFileds.getString("dependentFields"));
                                        }
                                        if (jInnerFileds.has("propgateValueToSubFormFields")) {
                                            serviceFieldsDto.setPropgateValueToSubFormFields(jInnerFileds.getString("propgateValueToSubFormFields"));
                                        }
                                        if (jInnerFileds.has("allowedValuesResults")) {
                                            JSONObject jsonObject = jInnerFileds.getJSONObject("allowedValuesResults");
                                            Iterator<?> keys = jsonObject.keys();
                                            ArrayList<MyMap> arry = new ArrayList<>();
                                            while (keys.hasNext()) {
                                                MyMap map = new MyMap();
                                                String key = (String) keys.next();
                                                map.setKey(key);
                                                if (jsonObject.get(key) instanceof String) {
                                                    String value = jsonObject.getString(key);
                                                    map.setValue(value);
                                                    arry.add(map);
                                                }
                                            }
                                            serviceFieldsDto.setAllowedValuesResults(arry);
                                        }
                                        serviceFieldsDtos.add(serviceFieldsDto);
                                    }
                                    sourceListner.onResponse(serviceFieldsDtos);
                                } else {
                                    Utils.toast("No Form", mContext);
                                }
                            } else {
                                Utils.toast("No Form", mContext);
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
        void onResponse(ArrayList<ServiceFieldsDto> serviceFieldsDtos);
    }

}
