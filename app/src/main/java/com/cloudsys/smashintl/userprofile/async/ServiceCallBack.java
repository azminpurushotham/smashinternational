package com.cloudsys.smashintl.userprofile.async;

import com.cloudsys.smashintl.base.asynck.AppBaseServiceCallBack;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 45.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ServiceCallBack extends AppBaseServiceCallBack {

    String getUserName();

    String getPassword();

    String getOldPassword();

    String getImageUrl();

}
