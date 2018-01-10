package com.cloudsys.smashintl.home;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ActionView extends AppBaseActionView {
    /////////////DEFAULTS///////////////////////

    void showWait(Dialog mLoading);

    void removeWait(Dialog mLoading);

    void onFailure(String appErrorMessage);

    Context getViewContext();

    RelativeLayout getParentView();

    void showInternetAlertLogic(boolean isInternet);

    void showSnackBar(Snackbar snackBar);

    void onFinishActivity();

    Dialog getLoading();

    /////////////DEFAULTS///////////////////////
    ViewPager getViewPager();

}
