package com.cloudsys.smashintl.scheduleworkdetails;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.scheduleworkdetails.async.ServiceCall;
import com.cloudsys.smashintl.scheduleworkdetails.async.ServiceCallBack;
import com.cloudsys.smashintl.scheduleworkdetails.model.WorkDetailsPojo;
import com.cloudsys.smashintl.scheduleworkdetails.model.scheduleWorkPojo;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack {
    ActionView mView;
    ServiceCall mServiceCall;
    WorkDetailsPojo mPojo = new WorkDetailsPojo();
    CustomSpinnerAdapter customSpinnerAdapter;
    ArrayList<String> reasons = new ArrayList();

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    public Presenter(ActionView mView, AppBaseFragment baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    @Override
    public void setServices(JSONObject mJsonObject) {
        mPojo = new Gson().fromJson(mJsonObject.toString(), WorkDetailsPojo.class);
        mView.getIdTextView().setText(mPojo.getResult().getCustomerId());
        mView.getLocationTextView().setText(mPojo.getResult().getAddress());
        mView.getAmountTextView().setText(mPojo.getResult().getAmount());
        if (mPojo.getResult().getStatus().equals("pending")) {
            mView.getPendingStatus().setChecked(true);
        } else {
            mView.getCompleteStatus().setChecked(true);
        }
        mView.getDateTextView().setText(mPojo.getResult().getDate());
        mView.getMap().addMarker(new MarkerOptions().position(
                new LatLng(Double.parseDouble(mPojo.getResult().getLat()), Double.parseDouble(mPojo.getResult().getLon()))));

    }

    @Override
    public void completPosting() {

    }

    /////////////DEFAULTS///////////////////////

    @Override
    public Context getViewContext() {
        return mView.getViewContext();
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
        showSnackBar(mView.getParentView(), message);
    }

    @Override
    public void onCallfailerFromServerside() {

    }

    @Override
    public void onException(String message) {
        mView.removeWait(mView.getLoading());
    }

    @Override
    public void showScnackBar(String message) {
        showSnackBar(mView.getParentView(), message);
    }

    @Override
    public void removeWait() {
        mView.removeWait(mView.getLoading());
    }


    @Override
    public void showWait(String message) {
        TextView TVmessage = (TextView) mView.getLoading().findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mView.showWait(mView.getLoading());
    }

    @Override
    public void showNoInternetConnectionLayout(boolean isInternet) {
        mView.showInternetAlertLogic(isInternet);
    }

    @Override
    public void showSnackBar(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(mView.getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(mView.getViewContext(), R.color.snack_bar_text_color));
        mView.showSnackBar(snackbar);
    }


    private boolean checkAndRequestPermissions() {
        return true;
    }


    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {
    }

    @Override
    public void getScheduledWorkDetails() {
        mView.showWait(mView.getLoading());
        if (Utilities.isInternet(mView.getViewContext())) {
            mServiceCall.getJson(mView.getUserId(), mView.getToken(), mView.getCustomerId());
        } else {
            mView.removeWait(mView.getLoading());
            mView.showInternetAlertLogic(false);
        }
    }

    @Override
    public void postData() {
        Utilities.hideKeyboard((Activity) getViewContext());
        if (mView.getPendingAmount().equals("")) {
            showSnackBar(mView.getParentView(), mView.getString(R.string.pending_amount_cannot_be_blank));
        } else if (mView.getBillId().equals("")) {
            showSnackBar(mView.getParentView(), mView.getString(R.string.billid_cannot_be_blank));
        } else if (mView.getReasonSpinner().getSelectedItemPosition() == 0) {
            showSnackBar(mView.getParentView(), mView.getString(R.string.please_select_a_reason));
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
                data.setBranch_id("1");
                data.setEmail(mView.getEmailTextView().getText().toString().trim());
                data.setSms_no(mView.getPhoneTextView().getText().toString().trim());
                data.setBranch_name(mPojo.getResult().getName());
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

    public void initReason() {
        reasons.add("Select a reason");
        reasons.add("Reason 1");
        reasons.add("Reason 2");
        reasons.add("Reason 3");
    }
}
