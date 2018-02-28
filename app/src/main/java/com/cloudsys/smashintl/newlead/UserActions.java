package com.cloudsys.smashintl.newlead;

import android.location.Location;
import android.view.View;

import com.cloudsys.smashintl.base.AppBaseAction;
import com.cloudsys.smashintl.base.AppBaseFragment;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface UserActions extends AppBaseAction{
    void submitData();

    void setLocationOfShop();

    void selectLocationPlacePicker();

    void setLocation(Location location);
}
