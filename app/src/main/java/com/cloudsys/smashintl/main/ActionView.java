package com.cloudsys.smashintl.main;

import android.widget.RelativeLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;
import com.cloudsys.smashintl.base.AppBaseActivity;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {

    String getPhoneNumber();

    String getEmail();

    String getFirstName();

    String getLastName();

    AppBaseActivity getViewActivity();

    String getImage();

    void setImage(String image);

    String getOtp();

    String getUserId();

    void dismissLogOut();

    void showLogoutDialouge();

    void onstartLogin();

}
