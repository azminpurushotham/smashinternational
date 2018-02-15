package com.cloudsys.smashintl.userprofile;

import android.widget.LinearLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ActionView extends AppBaseActionView {


    LinearLayout getParentView();

    void setErrorUserNameMissing(int message);

    void setErrorPasswordMissing(int message);

    void setErrorUserNameInvalid(int message);

    void setErrorPassWordInvalid(int message);

    String getUserName();

    String getPassword();

    void loadHomePage();
}
