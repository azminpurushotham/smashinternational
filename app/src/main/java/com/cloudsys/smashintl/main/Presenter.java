package com.cloudsys.smashintl.main;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.main.async.ServiceCall;
import com.cloudsys.smashintl.main.async.ServiceCallBack;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 * Mfluid Mobile Apps Pvt Ltd
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack {
    ActionView mView;
    Dialog mLoading;
    ServiceCall mServiceCall;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(mView.getViewContext(), mView.getViewContext().getString(R.string.loading));
        }
    }


    @Override
    public void logOut() {
        mServiceCall.logOut();
    }


    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }

    public void dismissLogOut() {
        mView.dismissLogOut();
    }

    @Override
    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return getSharedPreference();
    }

    @Override
    public void onSuccessLogout(JSONObject mJsonObject) {
        getSharedPreferenceHelper().clearPreferences();
        mView.dismissLogOut();
        mView.onstartLogin();
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return mView.getViewActivity();
    }

    /////////////DEFAULTS///////////////////////

    @Override
    public void onSuccess(JSONObject mJsonObject) {
        try {
            getSharedPreference().putString(mView.getViewContext().getString(R.string.user_id),
                    mJsonObject.getJSONObject("Result").getString("CustomerId"));
            getSharedPreference().putString(mView.getViewContext().getString(R.string.tocken),
                    mJsonObject.getJSONObject("Result").getJSONObject("LoginInfo").getString("Token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        mView.removeWait(mLoading);
    }

    @Override
    public void showScnackBar(String message) {
        showSnackBar(mView.getParentView(), message);
    }

    @Override
    public void removeWait() {
        mView.removeWait(mLoading);
    }


    @Override
    public void showWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mView.showWait(mLoading);
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

    public void showLogoutDialouge() {
        mView.showLogoutDialouge();
    }

    public void checkRunTimePermission(MainActivity mainActivity, String accessCoarseLocation) {    }


    /////////////DEFAULTS///////////////////////
}
