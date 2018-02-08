package com.cloudsys.smashintl.newlead;

import android.location.Location;
import android.view.View;

import com.cloudsys.smashintl.base.AppBaseFragment;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface UserActions {
    void showNoInternetConnectionLayout(boolean isInternet);

    void showSnackBar(View parent, String message);

    void checkRunTimePermission(AppBaseFragment activity, String permission);

    void submitData();

    void setLocationOfShop();

    void selectLocationPlacePicker();

    void setLocation(Location location);

}
