package com.cloudsys.smashintl.scheduledwork;

import com.cloudsys.smashintl.base.AppBaseActivity;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public interface UserActions {
    void showNoInternetConnectionLayout(boolean isInternet);

    void showSnackBar(String message);

    void checkRunTimePermission(AppBaseActivity activity, String permission);

    void getScheduledWork();

    void initRecyclerView();

    void setData();
}
