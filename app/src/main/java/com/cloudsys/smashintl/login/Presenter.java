package com.cloudsys.smashintl.login;

import android.content.Context;

import com.base.log.LogUtils;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.login.async.ServiceCall;
import com.cloudsys.smashintl.login.async.ServiceCallBack;
import com.cloudsys.smashintl.login.model.LoginPojo;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.cloudsys.smashintl.login.LoginActivity.TAG;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack {
    ActionView mView;
    ServiceCall mServiceCall;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    @Override
    public void onLoginClick() {
        if (Utilities.isInternet(mView.getViewContext())) {
            if (isValidate()) {
                mView.showWait(mView.getViewContext().getString(R.string.loading));
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                LogUtils.d(TAG, "Refreshed token: " + refreshedToken);
                if (refreshedToken != null && !refreshedToken.equalsIgnoreCase("")) {
                    getSharedPreference().putString(mView.getStringRes(R.string.tocken), refreshedToken);
                    mServiceCall.postLogin(
                            mView.getUserName(),
                            mView.getPassword(),
                            getSharedPreference().getString(mView.getStringRes(R.string.tocken), null));
                } else {
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
    public void onCallfailerFromServerside(JSONObject mJsonObject) {
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
    public void userNamePasswordinCorrect(int username_or_password_incorrect) {
        mView.showSnackBar(username_or_password_incorrect);
        mView.removeWait();
    }


    @Override
    public void onSuccessCallBack(String message) {
        LoginPojo mPojo = new Gson().fromJson(message, LoginPojo.class);
        if (mPojo.getStatus()) {
            getSharedPreferenceHelper().putString(getStringRes(R.string.user_id),
                    mPojo.getResult().getUserId());
            getSharedPreferenceHelper().putString(getStringRes(R.string.user_name),
                    mPojo.getResult().getName());
            getSharedPreferenceHelper().putString(getStringRes(R.string.user_image),
                    mPojo.getResult().getImage());
            mView.showSnackBar(mPojo.getMessage());
            mView.loadHomePage();
        }
    }

    @Override
    public void onSuccessCallBack(JSONObject message) {
        try {
            mView.showWait(message.getString("message"));
        } catch (JSONException e) {
             LogUtils.v(TAG, e.getMessage());
        }
        mView.loadHomePage();
    }

    @Override
    public void onSuccessCallBack(int message) {
        mView.showSnackBar(message);
        mView.loadHomePage();
    }
}
