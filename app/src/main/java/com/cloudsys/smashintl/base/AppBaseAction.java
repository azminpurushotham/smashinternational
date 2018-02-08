package com.cloudsys.smashintl.base;


import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

/**
 * Created by AzminPurushotham on 11/15/2017 time 15 : 43.
 */

public interface AppBaseAction {
    SharedPreferenceHelper getSharedPreference();

    void checkRunTimePermission(AppBaseActivity activity, String permission);

    void permissionGranded(String permission);

    void permissionDenaid(String permission);

    String getStringRec(int string_id);
}


