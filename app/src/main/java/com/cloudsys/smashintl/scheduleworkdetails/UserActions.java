package com.cloudsys.smashintl.scheduleworkdetails;

import android.location.Location;
import android.view.View;

import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.scheduleworkdetails.model.WorkDetailsPojo;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public interface UserActions {
    void showNoInternetConnectionLayout(boolean isInternet);

    void showSnackBar(String message);

    void showSnackBar(int message);

    void showWait(String message);

    void showWait(int message);

    void checkRunTimePermission(AppBaseFragment activity, String permission);

    void getScheduledWorkDetails();

    void postData();

    void initSpinner();

    void selectLocationPlacePicker();

    void setLocation(Location location);

    void setLocationOfShop();

}
