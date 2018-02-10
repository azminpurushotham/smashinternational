package com.cloudsys.smashintl.completd_work.async;

import com.cloudsys.smashintl.base.asynck.AppBaseServiceCallBack;

import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 */

public interface ServiceCallBack  extends AppBaseServiceCallBack{
    void onCallfailerFromServerside(JSONObject mJsonObject);

    void onCallfailerSearch(JSONObject mJsonObject);
}
