package com.cloudsys.smashintl.scheduleworkdetails.location_service;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.scheduleworkdetails.Presenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


/**
 * Created by Azmin Purushotham on 12/7/2017.
 */

public class LocationPresenter implements LocationAction {

    AppBaseFragment mActivity;
    LocationView mView;
    LatLng mLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    Dialog dialougeGps = null;

    public LocationPresenter(Presenter view, AppBaseFragment baseInstence) {
        this.mActivity = baseInstence;
        this.mView = view;
    }

    @Override
    public LatLng getLocation() {
        return mLocation;
    }

    @Override
    public void initLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mView.getViewBaseContext().getContext());
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mView.getViewBaseContext().getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
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
                                mLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                Log.v("Location", mLocation.latitude + "" + mLocation.longitude);
                                mView.setCurrentLocation(mLocation);
                                mView.removeWaiteLocation();
                            }else {
                                mView.removeWaiteLocation();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MapDemoActivity", "Error trying to get last GPS location");
                            e.printStackTrace();
                            mView.showSnackBar(R.string.no_location_detected);
                            enableLocation();
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
        enableLocation();
    }

    @Override
    public void enableLocation() {
        LocationManager lm = (LocationManager) mActivity.getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            Button BTNclose, BTNok;
            TextView TVtitle, TVmessage;
            if (dialougeGps == null) {
                dialougeGps = new Dialog(mView.getViewActivity());
                dialougeGps.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialougeGps.setContentView(R.layout.dialouge_message_with_positive_and_negative_button);
            }

            TVtitle = (TextView) dialougeGps.findViewById(R.id.TVtitle);
            TVmessage = (TextView) dialougeGps.findViewById(R.id.TVmessage);
            BTNclose = (Button) dialougeGps.findViewById(R.id.BTNclose);
            BTNok = (Button) dialougeGps.findViewById(R.id.BTNok);


            BTNok.setText(mActivity.getActivity().getString(R.string.ok));
            TVtitle.setText(mActivity.getActivity().getString(R.string.enabe_gps));
            TVmessage.setText(mActivity.getActivity().getString(R.string.gps_network_not_enabled));

            BTNok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialougeGps.dismiss();
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mActivity.getActivity().startActivity(myIntent);
                }
            });

            BTNclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialougeGps.dismiss();
                    mView.locationEnabled(false);
                }
            });

            dialougeGps.setCancelable(false);
            dialougeGps.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            if (dialougeGps != null && dialougeGps.isShowing() == false) {
                dialougeGps.show();
            }
        } else {
            if (dialougeGps != null && dialougeGps.isShowing()) {
                dialougeGps.dismiss();
                mView.locationEnabled(true);
            } else {
                mView.locationEnabled(true);
            }
        }
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

}
