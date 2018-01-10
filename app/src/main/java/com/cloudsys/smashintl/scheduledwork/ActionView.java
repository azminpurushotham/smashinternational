package com.cloudsys.smashintl.scheduledwork;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
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
    RecyclerView getRecyclerView();

    LinearLayoutManager getLinearLayoutManager();
}
