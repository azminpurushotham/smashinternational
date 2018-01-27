package com.cloudsys.smashintl.location_service;

import android.location.Location;

/**
 * Created by Azmin Purushotham on 12/7/2017.
 */

interface LocationAction {

    Location getLocation();

    void initLocation();

    void dismissLocationService();

    void showWait(int message);

    void removeWait();

    void onFailure(String appErrorMessage);

}
