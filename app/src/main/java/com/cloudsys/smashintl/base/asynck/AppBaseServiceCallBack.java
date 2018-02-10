package com.cloudsys.smashintl.base.asynck;

import android.content.Context;


import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 */

public interface AppBaseServiceCallBack {

    Context getViewContext();

    void setJson(JSONObject mJsonObject);

    void showWait(int message);

    void showWait(String message);

    void showWait(JSONObject message);

    void onSuccessCallBack(String message);

    void onSuccessCallBack(JSONObject message);

    void onSuccessCallBack(int message);

    void onSuccessCallBack();

    void onExceptionCallBack(String message);

    void onExceptionCallBack(int message);

    void onExceptionCallBack();

    void onFailerCallBack(String message);

    void onFailerCallBack(int message);

    void onFailerCallBack();

    void onCallfailerFromServerside();

    void onCallfailerFromServerside(String message);

    void onCallfailerFromServerside(int message);

    void onCallfailerFromServerside(JSONObject mJsonObject);

    SharedPreferenceHelper getSharedPreferenceHelper();


}
