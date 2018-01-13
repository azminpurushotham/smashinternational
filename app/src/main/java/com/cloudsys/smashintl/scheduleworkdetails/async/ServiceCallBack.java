package com.cloudsys.smashintl.scheduleworkdetails.async;

import android.content.Context;

import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ServiceCallBack {
    ////////////DEFAULTS///////////////
    void onSuccess(JSONObject mJsonObject);

    void onException(String message);

    void onFailer(String message);

    void onCallfailerFromServerside();

    void showWait(String message);

    void showScnackBar(String message);

    void removeWait();

    Context getViewContext();

    SharedPreferenceHelper getSharedPreferenceHelper();
    ////////////DEFAULTS///////////////
    void setServices(JSONObject mJsonObject);

    void completPosting();
}
