package com.cloudsys.smashintl.login;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.login.async.ServiceCall;
import com.cloudsys.smashintl.login.async.ServiceCallBack;
import com.cloudsys.smashintl.utiliti.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 * Mfluid Mobile Apps Pvt Ltd
 */

public class Presenter implements UserActions, ServiceCallBack {
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    ActionView mView;
    ServiceCall mServiceCall;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    @Override
    public void onLoginClick() {
        if (Utilities.isInternet(mView.getViewContext())) {
            if (isValidate()) {
                showWait(mView.getViewContext().getString(R.string.loading));
                mServiceCall.postLogin();
            }
        } else {
            mView.showNoInternetConnectionLayout(false);
        }
    }


    private boolean isValidate() {
        if (mView.getUserName().equalsIgnoreCase("")) {
            mView.setErrorUserNameMissing(R.string.missing_user_name);
            return false;
        } else if (mView.getPassword().equalsIgnoreCase("")) {
            mView.setErrorPasswordMissing(R.string.missing_password);
            return false;
        }
        return true;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }


    ////////////DEAFULTS////////////////////

    @Override
    public void showNoInternetConnectionLayout(boolean isInternet) {
        mView.showNoInternetConnectionLayout(isInternet);
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

    @Override
    public void onSuccess(JSONObject mJsonObject) {
        mView.loadHomePage();
    }

    @Override
    public void onFailer(String message) {
        showSnackBar(mView.getParentView(), message);
    }

    @Override
    public void onCallfailerFromServerside() {

    }

    @Override
    public void onException() {
        mView.removeWait();
    }

    @Override
    public void showScnackBar(String message) {
        showSnackBar(mView.getParentView(), message);
    }

    @Override
    public void removeWait() {
        mView.removeWait();
    }

    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }


    @Override
    public void showWait(String message) {
        mView.showWait(message);
    }

    ////////////DEAFULTS////////////////////
}
