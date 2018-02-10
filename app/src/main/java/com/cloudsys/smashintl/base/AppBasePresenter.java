package com.cloudsys.smashintl.base;

import android.support.v4.app.FragmentActivity;

import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

/**
 * Created by AzminPurushotham on 11/15/2017 time 15 : 39.
 */

public class AppBasePresenter {
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
}
