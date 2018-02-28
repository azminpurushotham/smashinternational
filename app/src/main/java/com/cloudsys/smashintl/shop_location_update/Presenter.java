package com.cloudsys.smashintl.shop_location_update;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.shop_location_update.async.*;
import com.cloudsys.smashintl.shop_location_update.location_service.*;
import com.cloudsys.smashintl.shop_location_update.model.*;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack, LocationView {
    ActionView mView;
    ServiceCall mServiceCall;
    ShopDetail mPojo = new ShopDetail();
    CustomSpinnerAdapter customSpinnerAdapter;
    ArrayList<String> reasons = new ArrayList();
    public static final int REQUEST_PLACE_PICKER = 666;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private Location mLocation;
    private LocationPresenter mLocationPresenter;
    Dialog dialougeGps = null;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mLocationPresenter = new LocationPresenter(this, baseInstence);
        mLocationPresenter.initLocation();
        mServiceCall = new ServiceCall(this);

    }

    @Override
    public void setServices(JSONObject mJsonObject) {
        mView.removeWait();
        mPojo = new Gson().fromJson(mJsonObject.toString(), ShopDetail.class);
        mView.setData(mPojo);
        if (mPojo.getResult().get(0).getLat()!=null && Integer.parseInt(mPojo.getResult().get(0).getLat())>0) {
            Location mLocation = new Location("");
            mLocation.setLatitude(Double.parseDouble(mPojo.getResult().get(0).getLat()));
            mLocation.setLongitude(Double.parseDouble(mPojo.getResult().get(0).getLat()));
            mView.setMarkerFromDb(mLocation);
        }
        mView.setPojo(mPojo);
    }

    @Override
    public void completPosting(JSONObject mJsonObject) {
        mView.showWait(R.string.please_waite);
        mView.removeWait();
        try {
            mView.showSnackBar(mJsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getShopDetails() {
        mView.showWait(R.string.loading);
        if (Utilities.isInternet(mView.getViewContext())) {
            mServiceCall.getJson(
                    mView.getCustomerId(),
                    mView.getUserId(),
                    mView.getToken());
        } else {
            mView.removeWait();
            mView.showInternetAlertLogic(false);
        }
    }

    @Override
    public void postData() {
        Utilities.hideKeyboard((Activity) getViewContext());
        mView.showWait(R.string.update_customer_location);
        if (Utilities.isInternet(getViewContext())) {

            mServiceCall.updateShopLocation(
                    mView.getUserId(),
                    mView.getToken(),
                    mView.getPojo().getResult().get(0).getId(),
                    mView.getAddress1(),
                    mView.getAddress2(),
                    mView.getShopLocation().getLatitude() + "",
                    mView.getShopLocation().getLongitude() + "");
        } else {
            mView.showInternetAlertLogic(false);
        }
    }

    @Override
    public void selectPlace() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(mView.getViewActivity());
            // Start the Intent by requesting a result, identified by a request code.
            mView.startActivityForResultPlacePicker(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), mView.getViewActivity(), 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(mView.getViewActivity(), "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
    @Override
    public AppBaseActivity getViewActivity() {
        return mView.getViewActivity();
    }

    @Override
    public AppBaseFragment getViewBaseContext() {
        return mView.getBaseFragment();
    }

    @Override
    public void setCurrentLocation(Location location) {
        mView.setCurrentLocationFromLocationService(location);
    }

    @Override
    public void showSnackBar(int message) {
        mView.showSnackBar(message);
    }


    @Override
    public void enableLocation() {
        LocationManager lm = (LocationManager) mView.getViewContext().getSystemService(Context.LOCATION_SERVICE);
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

            dialougeGps = new Dialog(mView.getViewActivity());
            dialougeGps.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialougeGps.setContentView(R.layout.dialouge_message_with_positive_and_negative_button);

            TVtitle = (TextView) dialougeGps.findViewById(R.id.TVtitle);
            TVmessage = (TextView) dialougeGps.findViewById(R.id.TVmessage);
            BTNclose = (Button) dialougeGps.findViewById(R.id.BTNclose);
            BTNok = (Button) dialougeGps.findViewById(R.id.BTNok);

            BTNclose.setVisibility(View.GONE);

            BTNok.setText(mView.getStringRes(R.string.open_location_settings));
            TVtitle.setText(mView.getStringRes(R.string.enabe_gps));
            TVmessage.setText(mView.getStringRes(R.string.gps_network_not_enabled));

            BTNok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mView.getViewContext().startActivity(myIntent);
                    dialougeGps.dismiss();
                }
            });

            dialougeGps.setCancelable(false);
            dialougeGps.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialougeGps.show();
        } else {
            if (dialougeGps != null && dialougeGps.isShowing()) {
                dialougeGps.dismiss();
                mLocationPresenter.initLocation();
            } else {
                mLocationPresenter.initLocation();
            }
        }
    }

    @Override
    public void removeWaiteLocation() {
        mView.removeWait();
    }


    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }


    @Override
    public void setJson(JSONObject mJsonObject) {

    }

    @Override
    public void onSuccessCallBack(JSONObject mJsonObject) {
        try {
            mView.showSnackBar(mJsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mView.removeWait();
    }

    @Override
    public void onSuccessCallBack(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onSuccessCallBack() {
        mView.removeWait();
    }

    @Override
    public void onExceptionCallBack(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onExceptionCallBack(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onExceptionCallBack() {
        mView.removeWait();
    }

    @Override
    public void onFailerCallBack(String message) {
        Log.v("exception", message);
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onFailerCallBack(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onFailerCallBack() {
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside() {
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return super.getSharedPreference();
    }

    @Override
    public void onCallfailerFromServerside(JSONObject mJsonObject) {
        mView.removeWait();
        try {
            mView.showSnackBar(mJsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showWait(String message) {
        mView.showWait(message);
    }

    @Override
    public void showWait(JSONObject message) {
        try {
            mView.showWait(message.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessCallBack(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void showWait(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }


    @Override
    public void permissionGranded(String permission) {

    }

    @Override
    public void permissionDenaid(String permission) {

    }

    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        REQUEST_PERMISSIONS_REQUEST_CODE);
                // REQUEST_PERMISSIONS_REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

        }
    }

}
