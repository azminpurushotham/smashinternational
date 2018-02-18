package com.cloudsys.smashintl.base;


/**
 * Created by AzminPurushotham on 11/15/2017 time 15 : 43.
 */

public interface AppBaseAction {

    void permissionGranded(String permission);

    void permissionDenaid(String permission);

    void checkRunTimePermission(AppBaseActivity activity, String permission);
}


