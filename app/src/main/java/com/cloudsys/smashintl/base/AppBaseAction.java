package com.cloudsys.smashintl.base;


import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

/**
 * Created by AzminPurushotham on 11/15/2017 time 15 : 43.
 */

public interface AppBaseAction {
    SharedPreferenceHelper getSharedPreference();

    String getStringRec(int string_id);

    void permissionGranded(String permission);

    void permissionDenaid(String permission);

    void checkRunTimePermission(AppBaseActivity activity, String permission);

    void showNoInternetConnectionLayout(boolean isInternet);

    void showNoDataLayout(boolean isNodata);
}


