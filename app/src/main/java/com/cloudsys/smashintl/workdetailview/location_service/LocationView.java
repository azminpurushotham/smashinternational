package com.cloudsys.smashintl.workdetailview.location_service;

import android.location.Location;

import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;


/**
 * Created by Azmin Purushotham on 12/7/2017.
 */

public interface LocationView {

    AppBaseActivity getViewActivity();

    AppBaseFragment getViewBaseContext();

    void setCurrentLocation(Location mLocation);

    void showSnackBar(int no_location_detected);

    void showWait(int message);

    void enableLocation();

    void removeWaiteLocation();
}
