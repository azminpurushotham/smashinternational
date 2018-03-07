package com.cloudsys.smashintl.scheduleworkdetails;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.scheduleworkdetails.async.ServiceCall;
import com.cloudsys.smashintl.scheduleworkdetails.async.ServiceCallBack;
import com.cloudsys.smashintl.scheduleworkdetails.location_service.LocationPresenter;
import com.cloudsys.smashintl.scheduleworkdetails.location_service.LocationView;
import com.cloudsys.smashintl.scheduleworkdetails.model.WorkDetailsPojo;
import com.cloudsys.smashintl.scheduleworkdetails.model.scheduleWorkPojo;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
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
    WorkDetailsPojo mPojo = new WorkDetailsPojo();
    CustomSpinnerAdapter customSpinnerAdapter;
    ArrayList<String> reasons = new ArrayList();
    public static final int REQUEST_PLACE_PICKER = 666;
    public static final int REQUEST_PERMISSIONS_LOCATION = 6;
    public LocationPresenter mLocationPresenter;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    public Presenter(ActionView mView, AppBaseFragment baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mLocationPresenter = new LocationPresenter(this, baseInstence);
        mServiceCall = new ServiceCall(this);
        mLocationPresenter.enableLocation();
    }

    @Override
    public void setServices(JSONObject mJsonObject) {
        mView.removeWait();
        mPojo = new Gson().fromJson(mJsonObject.toString(), WorkDetailsPojo.class);
        mView.getIdTextView().setText(mPojo.getResult().get(0).getCustomerId());
        mView.getLocationTextView().setText(mPojo.getResult().get(0).getAddress());
        mView.getEmailTextView().setText(mPojo.getResult().get(0).getEmail());
        mView.getPhoneTextView().setText(mPojo.getResult().get(0).getPhoneNumber());
        mView.getSmsPhoneTextView().setText(mPojo.getResult().get(0).getPhoneNumber());
        mView.getAmountTextView().setText(mPojo.getResult().get(0).getAmount() + " " + mPojo.getResult().get(0).getCurrency());

        if (mPojo.getResult().get(0).getLat() != null) {
            mView.setPlacePickerLocation(new LatLng(
                    Double.parseDouble(mPojo.getResult().get(0).getLat()),
                    Double.parseDouble(mPojo.getResult().get(0).getLon())));
        } else if(mView.getCurrentLatLng()==null){
            mLocationPresenter.initLocation();
        }else {
            mView.setMarker(mView.getCurrentLatLng());
        }

        mView.setPojo(mPojo);
        mView.getDateTextView().setText(
                Utilities.getFormatedDate(
                        mPojo.getResult().get(0).getDate(), Utilities.REQ_FORMAT, Utilities.SERVER_DATE_FORMAT));

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
    public void getScheduledWorkDetails() {
        mView.showWait(R.string.loading);
        if (Utilities.isInternet(mView.getViewContext())) {
            mServiceCall.getJson(mView.getUserId(), mView.getToken(), mView.getCustomerId());
        } else {
            mView.removeWait();
            mView.showInternetAlertLogic(false);
        }
    }

    @Override
    public void postData() {
        Utilities.hideKeyboard((Activity) getViewContext());
        if (mView.getPendingAmount().equals("")) {
            mView.showSnackBar(R.string.pending_amount_cannot_be_blank);
        } else if (mView.getBillId().equals("")) {
            mView.showSnackBar(R.string.billid_cannot_be_blank);
        } else if (mView.getReasonSpinner().getSelectedItemPosition() == 0
                && Integer.parseInt(mView.getPojo().getResult().get(0).getAmount()) > mView.getAmount()) {
            mView.showSnackBar(R.string.please_select_a_reason);
        } else {

            if (Utilities.isInternet(getViewContext())) {
                scheduleWorkPojo data = new scheduleWorkPojo();
                data.setUserId(getSharedPreference().getString(mView.getViewContext().getString(R.string.user_id), null));
                data.setToken(getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null));
//                if (mView.getCompleteStatus().isChecked()) {
//                    data.setStatus("pending");
//                } else {
//                    data.setStatus("completed");initLocation
//                }
                data.setStatus(mView.getStringRes(R.string.completed_));

                data.setBranch_id(mPojo.getResult().get(0).getId());
                data.setEmail(mPojo.getResult().get(0).getEmail());
                data.setSms_no(mPojo.getResult().get(0).getSmsNumber());
                data.setBranch_name(mPojo.getResult().get(0).getName());
                data.setAddress1(mPojo.getResult().get(0).getAddress());
                data.setAddress2(mPojo.getResult().get(0).getAddress());
                data.setTelephone_no(mPojo.getResult().get(0).getPhoneNumber());
                data.setCollection_amount(mView.getAmount() + "");
                data.setReason(mView.getReason());
                if (mView.getinvoiceEditText().getText().toString().length() != 0) {
                    data.setInvoice(mView.getinvoiceEditText().getText().toString());
                }
                int temp = 0;
                try {
                    temp = Integer.parseInt(mPojo.getResult().get(0).getAmount()) - mView.getAmount();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                data.setPendingAmount(temp + "");

                data.setBill_id(mView.getBillId());
                mView.showWait(R.string.loading);
                if (mPojo.getResult().get(0).getLat() == null || isAgentInRange()) {
                    mServiceCall.postUpdateWorkStatus(data);
                } else {
                    mView.removeWait(R.string.not_on_the_premises);
                    mView.showSnackBar(R.string.not_on_the_premises);
                }
            } else {
                mView.showInternetAlertLogic(false);
            }
        }
    }

    private boolean isAgentInRange() {
        Location startPoint = new Location("locationA");
        if (mView.getPojo().getResult().get(0).getLat() != null) {
            startPoint.setLatitude(Double.parseDouble(mView.getPojo().getResult().get(0).getLat()));//9.9666543
            startPoint.setLongitude(Double.parseDouble(mView.getPojo().getResult().get(0).getLon())); //76.3168134

            Location endPoint = new Location("locationB");

            if (mView.getCurrentLatLng() != null) {
                endPoint.setLatitude(mView.getCurrentLatLng().latitude);
                endPoint.setLongitude(mView.getCurrentLatLng().longitude);
            }

            Log.v("Location Range",
                    "Location START  lat " + startPoint.getLatitude() + " -- " + startPoint.getLongitude()
                            + "\n" + " Location END lat " + endPoint.getLatitude() + " -- " + endPoint.getLongitude());

            double distance = startPoint.distanceTo(endPoint);
            if (distance > getViewActivity().getResources().getInteger(R.integer.maximum_permises)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initSpinner() {
        initReason();
        customSpinnerAdapter = new CustomSpinnerAdapter(getViewContext(), R.layout.item_reason_spinner, reasons);
        mView.getReasonSpinner().setAdapter(customSpinnerAdapter);
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

    @Override
    public void setLocationOfShop() {
//        mView.getMap().addMarker(new MarkerOptions().position(
//                new LatLng(Double.parseDouble(mPojo.getResult().getLat()), Double.parseDouble(mPojo.getResult().getLon()))));

    }

    @Override
    public void getCurrentLocation() {
        mLocationPresenter.initLocation();
    }

    /////////////DEFAULTS///////////////////////


    @Override
    public AppBaseActivity getViewActivity() {
        return mView.getViewActivity();
    }

    @Override
    public AppBaseFragment getViewBaseContext() {
        return mView.getBaseFragment();
    }

    @Override
    public void setCurrentLocation(LatLng location) {
        mView.setCurrentLocation(location);
        getScheduledWorkDetails();
    }

    @Override
    public void showSnackBar(int message) {
        mView.showSnackBar(message);
    }

    @Override
    public void removeWaiteLocation() {
        mView.removeWait();
    }

    @Override
    public void locationEnabled(boolean isLocationEnabled) {
        if (isLocationEnabled) {
            checkRunTimePermission(mView.getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            mLocationPresenter.enableLocation();
        }
    }

    public void initReason() {
        reasons.add(mView.getStringRes(R.string.reason_title));
        reasons.add(mView.getStringRes(R.string.reason_1));
        reasons.add(mView.getStringRes(R.string.reason_2));
        reasons.add(mView.getStringRes(R.string.reason_3));
    }


    //////////********************


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
        mView.showWait(message);
        mView.showSnackBar(message);
    }


    @Override
    public void permissionGranded(String permission) {

    }

    @Override
    public void permissionDenaid(String permission) {

    }

    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {
        if (ContextCompat.checkSelfPermission(getViewActivity(), permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    REQUEST_PERMISSIONS_LOCATION);
        } else {
            mLocationPresenter.initLocation();
        }
    }

}
