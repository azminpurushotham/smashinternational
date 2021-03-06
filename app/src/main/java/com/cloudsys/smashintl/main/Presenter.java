package com.cloudsys.smashintl.main;

import android.content.Context;

import com.base.log.LogUtils;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.main.async.ServiceCall;
import com.cloudsys.smashintl.main.async.ServiceCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import static com.cloudsys.smashintl.main.MainActivity.TAG;

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
    public void logOut() {
        mServiceCall.logOut();
    }

    public void dismissLogOut() {
        mView.dismissLogOut();
    }


    public void showLogoutDialouge() {
        mView.showLogoutDialouge();
    }

    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }

    @Override
    public void onSuccessLogout(JSONObject mJsonObject) {
        try {
            mView.showWait(mJsonObject.getString("Value"));
        } catch (JSONException e) {
             LogUtils.v(TAG, e.getMessage());
        }
        getSharedPreferenceHelper().clearPreferences();
        mView.dismissLogOut();
        mView.onstartLogin();
    }


    @Override
    public void setJson(JSONObject mJsonObject) {

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
        LogUtils.v("exception", message);
        mView.showSnackBar(message);

    }

    @Override
    public void onCallfailerFromServerside(int message) {
        mView.showSnackBar(message);
    }

    @Override
    public void onCallfailerFromServerside(JSONObject mJsonObject) {
        try {
            mView.showWait(mJsonObject.getString("message"));
        } catch (JSONException e) {
             LogUtils.v(TAG, e.getMessage());
        }
    }


    @Override
    public void onSuccessCallBack(String message) {

    }

    @Override
    public void onSuccessCallBack(JSONObject message) {

    }

    @Override
    public void onSuccessCallBack(int message) {

    }

    @Override
    public void onSuccessCallBack() {

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


    public void checkRunTimePermission(MainActivity mainActivity, String accessCoarseLocation) {
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

}
