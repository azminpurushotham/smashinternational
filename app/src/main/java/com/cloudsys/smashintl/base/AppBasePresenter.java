package com.cloudsys.smashintl.base;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.base.log.LogUtils;
import com.cloudsys.smashintl.base.asynck.AppBaseServiceCallBack;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 11/15/2017 time 15 : 39.
 */

public class AppBasePresenter implements AppBaseServiceCallBack {
    AppBaseActionView mView;
    AppBaseActivity activity;
    AppBaseFragment fragment;


    public AppBasePresenter(AppBaseActionView baseActionView, AppBaseActivity activity) {
        this.mView = baseActionView;
        this.activity = activity;
    }

    public AppBasePresenter(AppBaseActionView baseActionView, FragmentActivity activity) {
        this.mView = baseActionView;
        this.activity = (AppBaseActivity) activity;
    }

    public AppBasePresenter(AppBaseActionView baseActionView, AppBaseFragment fragment) {
        this.mView = baseActionView;
        this.fragment = fragment;
    }


    public SharedPreferenceHelper getSharedPreference() {
        if (activity == null) {
            return fragment.getSharedPreferenceHelper();
        } else {
            return activity.getSharedPreferenceHelper();
        }
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
        mView.showWait(message);
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

    @Override
    public void onFailerCallBack(String message) {

    }

    @Override
    public void onFailerCallBack(int message) {
        LogUtils.v("exception", mView.getStringRes(message));
        mView.showSnackBar(message);
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
        if (activity == null) {
            return fragment.getSharedPreferenceHelper();
        } else {
            return activity.getSharedPreferenceHelper();
        }
    }

    @Override
    public String getStringRes(int string) {
        return mView.getStringRes(string);
    }
}
