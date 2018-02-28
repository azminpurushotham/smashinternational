package com.cloudsys.smashintl.workdetailview;

import android.location.Location;

import com.cloudsys.smashintl.base.AppBaseAction;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public interface UserActions extends AppBaseAction{

    void getScheduledWorkDetails();

    void postData();

    void initSpinner();

    void selectLocationPlacePicker();

    void setLocation(Location location);

    void setLocationOfShop();
}
