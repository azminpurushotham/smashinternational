package com.cloudsys.smashintl.login.async;

import android.content.Context;

import com.cloudsys.smashintl.base.asynck.AppBaseServiceCallBack;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 */

public interface ServiceCallBack extends AppBaseServiceCallBack{

    String getUserName();

    String getPassword();

    String getStringRes(int string_id);

    void userNamePasswordinCorrect(int username_or_password_incorrect);
}
