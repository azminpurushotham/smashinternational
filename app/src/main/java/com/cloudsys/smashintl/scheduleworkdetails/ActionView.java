package com.cloudsys.smashintl.scheduleworkdetails;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cloudsys.smashintl.base.AppBaseActionView;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    /////////////DEFAULTS///////////////////////

    void showWait(String message);

    void showWait(int message);

    void removeWait();

    void onFailure(String appErrorMessage);

    Context getViewContext();

    RelativeLayout getParentView();

    void showInternetAlertLogic(boolean isInternet);

    void showSnackBar(String snackBar);

    void onFinishActivity();

    Dialog getLoading();

    /////////////////////////////////////


    TextView getIdTextView();

    TextView getLocationTextView();

    TextView getDateTextView();

    TextView getEmailTextView();

    TextView getPhoneTextView();

    TextView getAmountTextView();

    String getString(int string);

    AppBaseActivity getViewActivity();

    AppBaseFragment getViewBaseContext();

    ////////////////////////////////////////////

    RadioButton getPendingStatus();

    RadioButton getCompleteStatus();

    String getPendingAmount();

    String getBillId();

    String getReason();

    String getCustomerId();

    String getUserId();

    String getToken();

    GoogleMap getMap();

    void returnToHome();

    Spinner getReasonSpinner();

    void startActivityForResultPlacePicker(Intent intent, int requestPlacePicker);


    void setCurrentLocation(Location mLocation);

    void setPlacePickerLocation(Location mLocation);

    void setCurrentLocation(LatLng mLocation);

    void setPlacePickerLocation(LatLng mLocation);

    TextView getSmsPhoneTextView();

    EditText getReasonEditText();

    LinearLayout getReasonLinearLay();

    EditText getCurrencyEditText();

}
