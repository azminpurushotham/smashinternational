package com.cloudsys.smashintl.main.async;

import android.content.Context;

import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

import org.json.JSONObject;

import java.util.List;

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

    AppBaseActivity getViewActivity();

    ////////////DEFAULTS///////////////
    void onSuccessLogout(JSONObject mJsonObject);
}
