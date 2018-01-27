package com.cloudsys.smashintl.login.async;

import android.content.Context;

import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ServiceCallBack {

    /////DEFAULT/////////

    void onSuccess();

    void onFailer();

    void onCallfailerFromServerside();

    void showWait(String message);

    void showScnackBar(String message);

    void removeWait();

    Context getViewContext();

    String getStringRes(int please_waite);
    /////DEFAULT/////////


    String getUserName();

    String getPassword();

    SharedPreferenceHelper getSharedPreference();
}
