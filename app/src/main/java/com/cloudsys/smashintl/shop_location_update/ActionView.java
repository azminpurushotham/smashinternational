package com.cloudsys.smashintl.shop_location_update;

import android.content.Intent;
import android.location.Location;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cloudsys.smashintl.base.AppBaseActionView;
import com.cloudsys.smashintl.shop_location_update.model.ShopDetail;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    String getToken();

    String getCustomerId();

    String getUserId();

    GoogleMap getMap();

    void returnToHome();

    void startActivityForResultPlacePicker(Intent intent, int requestPlacePicker);

    void setCurrentLocationFromLocationService(Location mLocation);

    void setMarkerFromPlacePicker(Location mLocation);

    void setMarkerForCurrentLocation(Location mLocation);

    void setMarkerFromDb(Location mLocation);

    Location getCurrentLocation();

    ShopDetail getPojo();

    void setPojo(ShopDetail mPojo);

    void setData(ShopDetail mPojo);

    String getAddress1();

    String getAddress2();

    Location getShopLocation();
}
