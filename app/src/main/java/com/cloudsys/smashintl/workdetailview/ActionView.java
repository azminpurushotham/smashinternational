package com.cloudsys.smashintl.workdetailview;

import android.content.Intent;
import android.location.Location;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cloudsys.smashintl.base.AppBaseActionView;
import com.cloudsys.smashintl.workdetailview.model.WorkDetailsPojo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    TextView getIdTextView();

    TextView getLocationTextView();

    TextView getDateTextView();

    TextView getEmailTextView();

    TextView getPhoneTextView();

    TextView getAmountTextView();

    int getAmount();

    String getToken();

    RadioButton getPendingStatus();

    RadioButton getCompleteStatus();

    String getPendingAmount();

    String getBillId();

    String getReason();

    String getCustomerId();

    String getUserId();


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

    WorkDetailsPojo getPojo();

    void setPojo(WorkDetailsPojo mPojo);

}
