package com.cloudsys.smashintl.shop_location_update.location_service;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.shop_location_update.Presenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


/**
 * Created by Azmin Purushotham on 12/7/2017.
 */

public class LocationPresenter implements LocationAction {

    AppBaseActivity mActivity;
    LocationView mView;
    Location mLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_PERMISSIONS_LOCATION = 6;

    public LocationPresenter(Presenter view, AppBaseActivity baseInstence) {
        this.mActivity = baseInstence;
        this.mView = view;
    }

    @Override
    public Location getLocation() {
        return mLocation;
    }

    @Override
    public void initLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mView.getViewActivity());
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mView.getViewActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?

        } else {
            mView.showWait(R.string.fetching_current_location);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // GPS location can be null if GPS is switched off
                            if (location != null) {
                                mLocation = location;
                                Log.v("Location", mLocation.getLatitude() + "" + mLocation.getLongitude());
                                if (mLocation != null) {
                                    mView.setCurrentLocation(mLocation);
                                    mView.removeWaiteLocation();
                                } else {
                                    mView.setCurrentLocation(LocationUtilities.getLastLocation(mFusedLocationClient, mView.getViewActivity()));
                                    mView.removeWaiteLocation();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MapDemoActivity", "Error trying to get last GPS location");
                            e.printStackTrace();
                            mView.showSnackBar(R.string.no_location_detected);
                            mView.enableLocation();
                            mView.removeWaiteLocation();
                        }
                    });

        }
    }

    @Override
    public void dismissLocationService() {

    }

    @Override
    public void showWait(int message) {
        mView.showWait(message);
    }

    @Override
    public void removeWait() {
        mView.enableLocation();
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

}
