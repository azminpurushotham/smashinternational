package com.cloudsys.smashintl.scheduleworkdetails;

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
import com.cloudsys.smashintl.scheduleworkdetails.location_service.LocationPresenter;
import com.cloudsys.smashintl.scheduleworkdetails.location_service.LocationView;
import com.cloudsys.smashintl.scheduleworkdetails.async.ServiceCall;
import com.cloudsys.smashintl.scheduleworkdetails.async.ServiceCallBack;
import com.cloudsys.smashintl.scheduleworkdetails.model.WorkDetailsPojo;
import com.cloudsys.smashintl.scheduleworkdetails.model.scheduleWorkPojo;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

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
        mPojo = new Gson().fromJson(mJsonObject.toString(), WorkDetailsPojo.class);
        mView.getIdTextView().setText(mPojo.getResult().get(0).getCustomerId());
        mView.getLocationTextView().setText(mPojo.getResult().get(0).getAddress());
        mView.getEmailTextView().setText(mPojo.getResult().get(0).getEmail());
        mView.getPhoneTextView().setText(mPojo.getResult().get(0).getPhoneNumber());
        mView.getSmsPhoneTextView().setText(mPojo.getResult().get(0).getPhoneNumber());
        mView.getAmountTextView().setText(mPojo.getResult().get(0).getAmount() + " " + mPojo.getResult().get(0).getCurrency());
        mView.getCurrencyEditText().setText(mPojo.getResult().get(0).getCurrency());

        mView.setPlacePickerLocation(new LatLng(
                Double.parseDouble(mPojo.getResult().get(0).getLat()),
                Double.parseDouble(mPojo.getResult().get(0).getLon())));

        Location mLocation = new Location("");
        mLocation.setLatitude(Double.parseDouble(mPojo.getResult().get(0).getLat()));
        mLocation.setLongitude(Double.parseDouble(mPojo.getResult().get(0).getLat()));

        mView.setPlacePickerLocation(mLocation);

        if (mPojo.getResult().get(0).getWorkstatus().equals("pending")) {
            mView.getPendingStatus().setChecked(true);
        } else {
            mView.getCompleteStatus().setChecked(true);
        }

        mView.setPojo(mPojo);

        mView.getDateTextView().setText(
                Utilities.getFormatedDate(
                        mPojo.getResult().get(0).getDate(), Utilities.REQ_FORMAT, Utilities.SERVER_DATE_FORMAT));

    }

    @Override
    public void completPosting() {

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
            showSnackBar(mView.getString(R.string.pending_amount_cannot_be_blank));
        } else if (mView.getBillId().equals("")) {
            showSnackBar(mView.getString(R.string.billid_cannot_be_blank));
        } else if (mView.getReasonSpinner().getSelectedItemPosition() == 0
                && Integer.parseInt(mView.getPojo().getResult().get(0).getAmount()) > mView.getAmount()) {
            showSnackBar(mView.getString(R.string.please_select_a_reason));
        } else {

            if (Utilities.isInternet(getViewContext())) {
                scheduleWorkPojo data = new scheduleWorkPojo();
                data.setUserId(mView.getCustomerId());
                data.setToken(getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null));
                if (mView.getCompleteStatus().isChecked()) {
                    data.setStatus("pending");
                } else {
                    data.setStatus("completed");
                }
                data.setBranch_id(mPojo.getResult().get(0).getCustomerId());
                data.setEmail(mView.getEmailTextView().getText().toString().trim());
                data.setSms_no(mView.getPhoneTextView().getText().toString().trim());
                data.setBranch_name(mPojo.getResult().get(0).getName());
                data.setAddress(mView.getLocationTextView().getText().toString().trim());
                data.setTelephone_no(mView.getPhoneTextView().getText().toString().trim());
                data.setCollection_amount(mView.getPendingAmount());
                data.setReason(reasons.get(mView.getReasonSpinner().getSelectedItemPosition()));
                data.setBill_id(mView.getBillId());
                mServiceCall.sendData(data);
            } else {
                mView.showInternetAlertLogic(false);
            }
        }
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

    /////////////DEFAULTS///////////////////////

    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }

    @Override
    public AppBaseFragment getViewBaseContext() {
        return mView.getViewBaseContext();
    }

    @Override
    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return getSharedPreference();
    }

    @Override
    public void onSuccess(JSONObject mJsonObject) {

    }

    @Override
    public void onFailer(String message) {
        Log.v("exception", message);
        showSnackBar(message);
    }

    @Override
    public void onCallfailerFromServerside() {

    }

    @Override
    public void onException(String message) {
        mView.removeWait();
    }

    @Override
    public void showScnackBar(String message) {
        showSnackBar(message);
    }

    @Override
    public void removeWait() {
        mView.removeWait();
    }


    @Override
    public void showWait(String message) {
        mView.showWait(message);
    }


    @Override
    public void showWait(int message) {
        showWait(mView.getViewActivity().getString(message));
    }

    @Override
    public void showNoInternetConnectionLayout(boolean isInternet) {
        mView.showInternetAlertLogic(isInternet);
    }

    @Override
    public void showSnackBar(String message) {
        mView.showSnackBar(message);
    }

    @Override
    public void checkRunTimePermission(AppBaseFragment activity, String permission) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity.getContext(), permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity.getActivity(), permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity.getActivity(),
                        new String[]{permission},
                        REQUEST_PERMISSIONS_REQUEST_CODE);
                // REQUEST_PERMISSIONS_REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

        }
    }

    /////////////DEFAULTS///////////////////////


    @Override
    public AppBaseActivity getViewActivity() {
        return mView.getViewActivity();
    }

    @Override
    public void setCurrentLocation(Location location) {
        mView.setCurrentLocation(location);
    }

    @Override
    public void showSnackBar(int message) {
        showScnackBar(mView.getViewActivity().getString(message));
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

            BTNok.setText(mView.getString(R.string.open_location_settings));
            TVtitle.setText(mView.getString(R.string.enabe_gps));
            TVmessage.setText(mView.getString(R.string.gps_network_not_enabled));

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

    public void initReason() {
        reasons.add("Select a reason");
        reasons.add("Reason 1");
        reasons.add("Reason 2");
        reasons.add("Reason 3");
    }
}
