package com.cloudsys.smashintl.userprofile;

import android.widget.RelativeLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ActionView extends AppBaseActionView {


    RelativeLayout getParentView();

    void setErrorOldPasswordMissing(int message);

    void setErrorNewPasswordMissing(int message);

    void setErrorConfirmPasswordMissing(int message);

    void setErrorPassWordInvalid(int message);

    void setPasswordNotMaching(int message);

    String getUserName();

    void loadHomePage();

    String getOldPassword();

    String getNewPassword();

    String getConfirmPassword();

    String getImageUrl();

    CircleImageView getCircleImageView();

    void setName();

    boolean isPassWordChange();

    void dimissImagePregress();

    void showImagePregress();

    String getImagePathForUpload();
}
