package com.cloudsys.smashintl.login;

import android.content.Context;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.login.async.ServiceCall;
import com.cloudsys.smashintl.login.async.ServiceCallBack;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 * Mfluid Mobile Apps Pvt Ltd
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack {
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    ActionView mView;
    ServiceCall mServiceCall;
    private String TAG = "LoginP";

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    @Override
    public void onLoginClick() {
        if (Utilities.isInternet(mView.getViewContext())) {
            if (isValidate()) {
                showWait(mView.getViewContext().getString(R.string.loading));
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Refreshed token: " + refreshedToken);
                if (refreshedToken != null && !refreshedToken.equalsIgnoreCase("")) {
                    getSharedPreference().putString(mView.getStringRes(R.string.tocken), refreshedToken);
                    mServiceCall.postLogin(
                            mView.getUserName(),
                            mView.getPassword(),
                            getSharedPreference().getString(mView.getStringRes(R.string.tocken), null));
                }else {
                    mView.removeWait();
                    mView.showSnackBar(R.string.please_try_again);
                }

            }
        } else {
            mView.showSnackBar(R.string.no_network_connection);
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
        return mView.getUserName();
    }

    @Override
    public String getPassword() {
        return mView.getPassword();
    }

       @Override
    public void permissionGranded(String permission) {

    }

    @Override
    public void permissionDenaid(String permission) {

    }

    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {

    }


    @Override
    public void onSuccessCallBack() {
        mView.loadHomePage();
    }

    @Override
    public void onExceptionCallBack(String message) {

    }

    @Override
    public void onExceptionCallBack(int message) {

    }

    @Override
    public void onExceptionCallBack() {

    }

    @Override
    public void onFailerCallBack(String message) {

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
    public void onCallfailerFromServerside(JSONObject mJsonObject) {

    }


    @Override
    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return super.getSharedPreference();
    }

    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }

    @Override
    public void setJson(JSONObject mJsonObject) {

    }

    @Override
    public void showWait(int message) {
        mView.showSnackBar(message);
    }

    @Override
    public String getStringRes(int string_id) {
        return mView.getStringRes(string_id);
    }

    @Override
    public void userNamePasswordinCorrect(int username_or_password_incorrect) {
        mView.showSnackBar(username_or_password_incorrect);
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
        mView.loadHomePage();
    }

    @Override
    public void onSuccessCallBack(JSONObject message) {
        try {
            mView.showWait(message.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mView.loadHomePage();
    }

    @Override
    public void onSuccessCallBack(int message) {
        mView.showSnackBar(message);
        mView.loadHomePage();
    }
}
