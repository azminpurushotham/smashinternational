package com.cloudsys.smashintl.login;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.LinearLayout;

import com.cloudsys.smashintl.base.AppBaseAction;
import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {

    void setErrorUserNameMissing(int message);

    void setErrorPasswordMissing(int message);

    void setErrorUserNameInvalid(int message);

    void setErrorPassWordInvalid(int message);

    String getUserName();

    String getPassword();

    void loadHomePage();
}
