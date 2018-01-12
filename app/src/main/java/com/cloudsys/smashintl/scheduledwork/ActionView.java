package com.cloudsys.smashintl.scheduledwork;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    /////////////DEFAULTS///////////////////////

    void showWait(String message);

    void showWait(int string_id);

    void removeWait();

    void onFailure(String appErrorMessage);

    Context getViewContext();

    RelativeLayout getParentView();

    void showInternetAlertLogic(boolean isInternet);

    void showSnackBar(String message);

    void onFinishActivity();

    String getStringRes(int string_id);

    /////////////DEFAULTS///////////////////////
    RecyclerView getRecyclerView();

    LinearLayoutManager getLinearLayoutManager();
}
