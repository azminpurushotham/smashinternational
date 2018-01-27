package com.cloudsys.smashintl.location_service;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.scheduleworkdetails.Presenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static android.support.v4.app.ActivityCompat.requestPermissions;


/**
 * Created by Azmin Purushotham on 12/7/2017.
 */

public class LocationPresenter implements LocationAction {

    AppBaseFragment mActivity;
    LocationView mView;
    Location mLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_PERMISSIONS_LOCATION = 6;

    public LocationPresenter(Presenter view, AppBaseFragment baseInstence) {
        this.mActivity = baseInstence;
        this.mView = view;
    }

    @Override
    public Location getLocation() {
        return mLocation;
    }

    @Override
    public void initLocation() {
        mView.showWait(R.string.fetching_current_location);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mView.getViewBaseContext().getContext());
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mView.getViewBaseContext().getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?

        } else {

//            mFusedLocationClient.getLastLocation()
//                    .addOnCompleteListener(mView.getViewActivity(), new OnCompleteListener<Location>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Location> task) {
//                            if (task.isSuccessful() && task.getResult() != null) {
//                                mLocation = task.getResult();
//                                Log.v("Location", mLocation.getLatitude() + "" + mLocation.getLongitude());
//                                if (mLocation != null) {
//                                    mView.setCurrentLocation(mLocation);
//                                } else {
//                                    mView.setCurrentLocation(LocationUtilities.getLastLocation(mFusedLocationClient, mView.getViewActivity()));
//                                }
//                            } else {
//                                Log.w("LocationUtilities", "getLastLocation:exception", task.getException());
//                                mView.showSnackBar(R.string.no_location_detected);
//                                mView.enableLocation();
//                            }
//                        }
//                    });

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
                                } else {
                                    mView.setCurrentLocation(LocationUtilities.getLastLocation(mFusedLocationClient, mView.getViewActivity()));
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
