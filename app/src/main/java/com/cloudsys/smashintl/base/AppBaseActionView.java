package com.cloudsys.smashintl.base;

import android.content.Context;

/**
 * Created by AzminPurushotham on 11/15/2017 time 15 : 43.
 */

public interface AppBaseActionView {

    void showWait(String mesage);

    void showWait(int mesage);

    void removeWait(String message);

    void removeWait(int message);

    void removeWait();

    void onFailure(String appErrorMessage);

    void onFailure(int appErrorMessage);

    void onFailure();

    void showSnackBar(String message);

    void showSnackBar(int message);

    String getStringRes(int string_id);

    Context getViewContext();

    AppBaseActivity getViewActivity();

    AppBaseFragment getViewFragment();

    void onFinishActivity();

    void showInternetAlertLogic(boolean isInternet);

    void showNodataAlertLogic(boolean isDataPresent);

    AppBaseActivity.OnFragmentSwitchListener getFragmentSwitch();


}
