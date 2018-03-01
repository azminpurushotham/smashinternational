package com.cloudsys.smashintl.newlead;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.widget.RelativeLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    String getToken();

    void setCurrentLocation(Location mLocation);

    String getStatus();

    String getPendingAmount();

    String getBillId();

    String getCustomerName();

    String getCustomerId();

    String getTelephoneNumber();

    String getEmail();

    String getSMS();

    String getAddress1();

    String getAddress2();

    Double getLat();

    Double getLon();

    String getCurrency();

    String getCompletedAmount();

    void returnToHome();

    void startActivityForResultPlacePicker(Intent intent, int requestPlacePicker);

    void setPlacePickerLocation(Location mLocation);

    void setCurrentLocation(LatLng mLocation);

    void setPlacePickerLocation(LatLng mLocation);

    boolean isExistingCustomer();
}
