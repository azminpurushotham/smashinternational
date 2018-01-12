package com.cloudsys.smashintl.login;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.LinearLayout;

import com.cloudsys.smashintl.base.AppBaseAction;
import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ActionView extends AppBaseActionView {

    /////////////DEFAULTS///////////////////////
    void showWait(String message);

    void removeWait();

    void onFailure(String appErrorMessage);

    Context getViewContext();

    LinearLayout getParentView();

    void showNoInternetConnectionLayout(boolean isInternet);

    void showSnackBar(Snackbar snackBar);

    void onFinishActivity();

    /////////////DEFAULTS///////////////////////

    void setErrorUserNameMissing(int message);

    void setErrorPasswordMissing(int message);

    void setErrorUserNameInvalid(int message);

    void setErrorPassWordInvalid(int message);


    String getUserName();

    String getPassword();

    void loadHomePage();
}
