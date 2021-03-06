package com.cloudsys.smashintl.newlead;

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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.base.log.LogUtils;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.newlead.async.ServiceCall;
import com.cloudsys.smashintl.newlead.async.ServiceCallBack;
import com.cloudsys.smashintl.newlead.location_service.LocationPresenter;
import com.cloudsys.smashintl.newlead.location_service.LocationView;
import com.cloudsys.smashintl.newlead.model.newlead;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cloudsys.smashintl.newlead.NewLeadFragment.TAG;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack, LocationView {

    ActionView mView;
    ServiceCall mServiceCall;

    public static final int REQUEST_PLACE_PICKER = 666;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private Location mLocation;
    private LocationPresenter mLocationPresenter;
    Dialog dialougeGps = null;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    public Presenter(ActionView mView, AppBaseFragment baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mLocationPresenter = new LocationPresenter(this, baseInstence);
        mLocationPresenter.initLocation();
        mServiceCall = new ServiceCall(this);

    }

    @Override
    public void setServices(JSONObject mJsonObject) {
    }

    @Override
    public void completPosting() {
        mView.showSnackBar(R.string.successfully_new_lead_created);
        mView.returnToHome();
        mView.removeWait();
    }

    @Override
    public void setCurrentLocation(Location location) {
        mView.setCurrentLocation(location);
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
    public AppBaseActivity getViewActivity() {
        return mView.getViewActivity();
    }

    @Override
    public AppBaseFragment getViewBaseContext() {
        return mView.getBaseFragment();
    }


    @Override
    public void showSnackBar(int message) {
        mView.showSnackBar(message);
    }

    @Override
    public void submitData() {

        Utilities.hideKeyboard((Activity) getViewContext());
        if (Utilities.isInternet(getViewContext())) {
            if (isValidate()) {
                newlead data = new newlead();
                data.setUserId(getSharedPreferenceHelper().getString(mView.getStringRes(R.string.user_id), null));
                data.setToken(getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null));
                data.setStatus(mView.getStatus());
                data.setCustomerName(mView.getCustomerName());
                data.setBranch("");
                data.setTelephone(mView.getTelephoneNumber());
                data.setEmail(mView.getEmail());
                data.setSms(mView.getSMS());
                data.setAddress1(mView.getAddress1());
                data.setAddress2(mView.getAddress2());
                data.setPending(mView.getPendingAmount());
                data.setCollecting(mView.getCompletedAmount());
                data.setCurrency(mView.getCurrency());
                data.setLat(mView.getLat() + "");
                data.setLon(mView.getLon() + "");
                data.setBill(mView.getBillId());
                mServiceCall.postNewLead(data);
            }
        } else {
            mView.showInternetAlertLogic(false);
        }
    }

    private boolean isValidate() {
        if (mView.getCustomerName().equals("")) {
            Utilities.hideKeyboard((Activity) getViewContext());
            mView.showSnackBar("Customer Name cannot be blank");
            return false;
        }

        if (mView.isExistingCustomer()) {
            if (mView.getCustomerId().equals("")) {
                mView.showSnackBar("Customer id cannot be blank");
                return false;
            }
        }

        if (mView.getTelephoneNumber().length() == 0) {
            mView.showSnackBar("Telephone number cannot be blank");
            return false;
        } else if (!isValidMobile(mView.getTelephoneNumber())) {
            mView.showSnackBar("Please enter a valid phone number");
            return false;
        } else if (mView.getEmail().equals("")) {
            mView.showSnackBar("Email cannot be blank");
            return false;
        } else if (!isValidMail(mView.getEmail())) {
            mView.showSnackBar("Please enter a valid email");
            return false;
        } else if (mView.getSMS().equals("")) {
            mView.showSnackBar("SMS number cannot be blank");
            return false;
        } else if (!isValidMobile(mView.getSMS())) {
            mView.showSnackBar("Please enter a valid SMS number");
            return false;
        } else if (mView.getAddress1().equals("")) {
            mView.showSnackBar("Address cannot be blank");
            return false;
        } else if (mView.getPendingAmount().equals("")) {
            mView.showSnackBar("Pending amount cannot be blank");
            return false;
        } else if (mView.getBillId().equals("")) {
            mView.showSnackBar("Bill id cannot be blank");
            return false;
        } else if (mView.getCompletedAmount().equals("")) {
            mView.showSnackBar("Completed cannot be blank");
            return false;
        } else if (mView.getLat() == 0 || mView.getLon() == 0) {
            mView.showSnackBar("Please select a location");
            return false;
        }
        return true;
    }

    @Override
    public void setLocationOfShop() {
//        mView.getMap().addMarker(new MarkerOptions().position(
//                new LatLng(Double.parseDouble(mPojo.getResult().getLat()), Double.parseDouble(mPojo.getResult().getLon()))));

    }

    @Override
    public void selectLocationPlacePicker() {
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
    public void setLocation(Location location) {

    }
    /////////////VALIDATION///////////////////////

    private boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        return check;
    }

    private boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 6 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    //////////////////////////////****************
    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }


    @Override
    public void setJson(JSONObject mJsonObject) {

    }

    @Override
    public void onSuccessCallBack(JSONObject mJsonObject) {
        mView.removeWait();
    }

    @Override
    public void onSuccessCallBack(int message) {

    }

    @Override
    public void onSuccessCallBack() {

    }

    @Override
    public void onExceptionCallBack(String message) {
        mView.removeWait(message);
    }

    @Override
    public void onExceptionCallBack(int message) {

    }

    @Override
    public void onExceptionCallBack() {

    }

    @Override
    public void onFailerCallBack(String message) {
        LogUtils.v("exception", message);
        mView.showSnackBar(message);
    }

    @Override
    public void onFailerCallBack(int message) {

    }

    @Override
    public void onFailerCallBack() {

    }

    @Override
    public void onCallfailerFromServerside() {

    }

    @Override
    public void onCallfailerFromServerside(String message) {

    }

    @Override
    public void onCallfailerFromServerside(int message) {

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
             LogUtils.v(TAG, e.getMessage());
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
             LogUtils.v(TAG, e.getMessage());
        }
    }

    @Override
    public void onSuccessCallBack(String message) {

    }

    @Override
    public void showWait(int message_id) {
        mView.showWait(message_id);
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
