package com.cloudsys.smashintl.newlead;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.widget.RelativeLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    /////////////DEFAULTS///////////////////////

    void removeWait(Dialog mLoading);

    void showSnackBar(Snackbar snackBar);

    String getString(int string);

    AppBaseActivity getViewActivity();

    AppBaseFragment getViewBaseContext();

    void showWait(String message);

    void showWait(int message);

    void removeWait();

    void onFailure(String appErrorMessage);

    Context getViewContext();

    RelativeLayout getParentView();

    void showInternetAlertLogic(boolean isInternet);

    void showSnackBar(String snackBar);

    void onFinishActivity();

    Dialog getLoading();

    String getToken();

    /////////////DEFAULTS///////////////////////

    void setCurrentLocation(Location mLocation);

    String getStatus();

    String getPendingAmount();

    String getBillId();

    String getCustomerName();

    String getCustomerId();

    String getTelephoneNumber();

    String getEmail();

    String getSMS();

    String getAddress();

    Double getLat();

    Double getLon();

    String getCurrency();

    void returnToHome();

}
