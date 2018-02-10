package com.cloudsys.smashintl.base;

import android.content.Context;

/**
 * Created by AzminPurushotham on 11/15/2017 time 15 : 43.
 */

public interface AppBaseActionView {

    void showWait(String message);

    void showWait(int message);

    void removeWait(String message);

    void removeWait(int message);

    void removeWait();

    void showSnackBar(String message);

    void showSnackBar(int message);

    String getStringRes(int string_id);

    Context getViewContext();

    AppBaseActivity getViewActivity();

    AppBaseFragment getViewFragment();

    AppBaseFragment getBaseFragment();

    AppBaseActivity getBaseActivity();

    void onFinishActivity();

    void showInternetAlertLogic(boolean isInternet);

    void showNodataAlertLogic(boolean isDataPresent);

    AppBaseActivity.OnFragmentSwitchListener getFragmentSwitch();


}
