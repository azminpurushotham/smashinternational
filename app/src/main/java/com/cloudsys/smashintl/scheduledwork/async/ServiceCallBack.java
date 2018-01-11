package com.cloudsys.smashintl.scheduledwork.async;

import android.content.Context;

import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 */

public interface ServiceCallBack {
    ////////////DEFAULTS///////////////
    void onSuccess(JSONObject mJsonObject);

    void onFailer(String message);

    void onCallfailerFromServerside(JSONObject mJsonObject);

    void showWait(String message);

    void showScnackBar(String message);

    void removeWait();

    Context getViewContext();

    String getStringRes(int string_id);

    ////////////DEFAULTS///////////////

}
