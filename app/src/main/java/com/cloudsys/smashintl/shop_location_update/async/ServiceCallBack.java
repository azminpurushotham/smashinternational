package com.cloudsys.smashintl.shop_location_update.async;

import com.cloudsys.smashintl.base.asynck.AppBaseServiceCallBack;

import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ServiceCallBack extends AppBaseServiceCallBack{
    void setServices(JSONObject mJsonObject);

    void completPosting(JSONObject mJsonObject);
}
