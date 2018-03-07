package com.cloudsys.smashintl.scheduleworkdetails.location_service;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Azmin Purushotham on 12/7/2017.
 */

interface LocationAction {

    LatLng getLocation();

    void initLocation();

    void dismissLocationService();

    void showWait(int message);

    void removeWait();

    void enableLocation();

    void onFailure(String appErrorMessage);

}
