package com.cloudsys.smashintl.login.async;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ServiceCallBack {

    /////DEFAULT/////////

    void onSuccess(JSONObject mJsonObject);

    void onFailer(String message);

    void onCallfailerFromServerside();

    void onException();

    void showWait(String message);

    void showScnackBar(String message);

    void removeWait();

    Context getViewContext();
    /////DEFAULT/////////


    String getUserName();

    String getPassword();

}
