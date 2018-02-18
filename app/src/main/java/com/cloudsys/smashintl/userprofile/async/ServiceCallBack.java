package com.cloudsys.smashintl.userprofile.async;

import com.cloudsys.smashintl.base.asynck.AppBaseServiceCallBack;

import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ServiceCallBack extends AppBaseServiceCallBack {

    String getUserName();

    String getPassword();

    String getOldPassword();

    String getImageUrl();

    void onSuccessPasswordUpdate();

    void onSuccessPasswordUpdate(String message);

    void onSuccessPasswordUpdate(int message);

    void onSuccessPasswordUpdate(JSONObject message);

    void onFailPasswordUpdate();

    void onFailPasswordUpdate(String message);

    void onFailPasswordUpdate(int message);

    void onFailPasswordUpdate(JSONObject message);

}
