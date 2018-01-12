package com.cloudsys.smashintl.scheduleworkdetails;

import android.view.View;

import com.cloudsys.smashintl.base.AppBaseActivity;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface UserActions {
    void showNoInternetConnectionLayout(boolean isInternet);

    void showSnackBar(View parent, String message);

    void checkRunTimePermission(AppBaseActivity activity, String permission);

    void getScheduledWorkDetails();

    void setServiceData();

    void initDetails();

}
