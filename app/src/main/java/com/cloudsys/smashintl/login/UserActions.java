package com.cloudsys.smashintl.login;

import android.view.View;
/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface UserActions {

    void onLoginClick();

    void showNoInternetConnectionLayout(boolean isInternet);

    void showSnackBar(View parent, String message);
}
