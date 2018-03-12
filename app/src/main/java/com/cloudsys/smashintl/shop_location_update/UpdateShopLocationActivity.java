package com.cloudsys.smashintl.shop_location_update;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.log.LogUtils;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.shop_location_update.model.ShopDetail;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateShopLocationActivity extends AppBaseActivity implements ActionView, View.OnClickListener {

    //// DEFAULT///////
    @BindView(R.id.parent)
    RelativeLayout parent;
    @Nullable
    @BindView(R.id.LAYnointernet)
    ConstraintLayout LAYnointernet;
    @BindView(R.id.BTN_try)
    Button BTN_try;
    @BindView(R.id.BTNSelectPlace)
    Button BTNSelectPlace;
    @BindView(R.id.LAYnodata)
    LinearLayout LAYnodata;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.TVtitle)
    TextView TVtitle;

    @BindView(R.id.MVmap)
    MapView MVmap;
    @BindView(R.id.TVid)
    TextView TVid;
    @BindView(R.id.TVEmail)
    TextView TVEmail;
    @BindView(R.id.TVMobile)
    TextView TVMobile;
    @BindView(R.id.BTNupdateStatus)
    Button BTNupdateStatus;
    @BindView(R.id.TVSmsMobile)
    TextView TVSmsMobile;

    @BindView(R.id.ETAddress1)
    EditText ETAddress1;
    @BindView(R.id.ETAddress2)
    TextView ETAddress2;
    @BindView(R.id.TVname)
    TextView TVname;


    private GoogleMap googleMap;
    private String shop_id;
    Presenter mPresenter;

    private Location mLocationCurrent = null;
    private Location mLocationShop = null;

    public static final int REQUEST_PLACE_PICKER = 666;
    private static final int REQUEST_PERMISSIONS_LOCATION = 6;
    ShopDetail mPojo = new ShopDetail();
    public static String TAG = "UpdateShopL";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        initParentView(parent);
        shop_id = getIntent().getStringExtra("shop_id");
        MVmap.onCreate(savedInstanceState);
        MVmap.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(UpdateShopLocationActivity.this.getApplicationContext());
        } catch (Exception e) {
            LogUtils.v(TAG, e.getMessage());
        }
        checkPermission();
    }


    private void buscinessLogic() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.menu_arrow_back_24dp);
        TVtitle.setText(R.string.shop_details);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPresenter = new Presenter(this, getBaseInstence());
        mPresenter.getShopDetails();
        BTNupdateStatus.setOnClickListener(this);
        BTN_try.setOnClickListener(this);
        BTNSelectPlace.setOnClickListener(this);
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getViewActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_PERMISSIONS_LOCATION);
            }
        } else {
            buscinessLogic();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MVmap.onResume();
        if (mPresenter != null) {
            mPresenter.enableLocation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MVmap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MVmap.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        MVmap.onLowMemory();
    }

    @Override
    public GoogleMap getMap() {
        return googleMap;
    }


    @Override
    public void setData(ShopDetail mPojo) {
        TVtitle.setText(mPojo.getResult().get(0).getName());
        TVname.setText(mPojo.getResult().get(0).getName());
        TVSmsMobile.setText(mPojo.getResult().get(0).getSmsNo());
        TVMobile.setText(mPojo.getResult().get(0).getTelephoneNumber());
        TVEmail.setText(mPojo.getResult().get(0).getEmail());
        TVid.setText(mPojo.getResult().get(0).getCustomerId());
        ETAddress1.setText(mPojo.getResult().get(0).getAddress1());
        ETAddress2.setText(mPojo.getResult().get(0).getAddress2());
    }

    @Override
    public String getAddress1() {
        return ETAddress1.getText().toString();
    }

    @Override
    public String getAddress2() {
        return ETAddress2.getText().toString();
    }

    @Override
    public Location getShopLocation() {
        Location mLocation = new Location("");
        if (mLocationShop != null) {
            return mLocationShop;
        } else if (mLocationCurrent != null) {
            return mLocationCurrent;
        }
        return mLocation;
    }

    @Override
    public String getCustomerId() {
        return shop_id;
    }


    @Override
    public String getUserId() {
        return getSharedPreferenceHelper().getString(getString(R.string.user_id), null);
    }

    @Override
    public ShopDetail getPojo() {
        return mPojo;
    }

    @Override
    public void setPojo(ShopDetail mPojo) {
        this.mPojo = mPojo;
    }

    @Override
    public void startActivityForResultPlacePicker(Intent intent, int requestPlacePicker) {
        startActivityForResult(intent, requestPlacePicker);
    }

    @Override
    public void setCurrentLocationFromLocationService(Location mLocation) {
        mLocationCurrent = mLocation;
        setGoogleMapMarker(mLocationCurrent, mLocationShop);
    }


    private void setGoogleMapMarker(final Location mLocationCurrent, final Location mLocationShop) {
        MVmap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                mMap.clear();
                // For zooming automatically to the location of the marker

                if (mLocationCurrent != null) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(mLocationCurrent.getLatitude(), mLocationCurrent.getLongitude()))
                            .icon(
                                    getBitmapFromVectorDrawable(
                                            UpdateShopLocationActivity.this, R.drawable.current_location_24dp)));
                }

                if (mLocationShop != null) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(mLocationShop.getLatitude(), mLocationShop.getLongitude()))
                            .icon(
                                    getBitmapFromVectorDrawable(
                                            UpdateShopLocationActivity.this, R.drawable.shop_24dp)));
                }

                if (mLocationShop != null) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(mLocationShop.getLatitude(), mLocationShop.getLongitude())).zoom(12).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                } else if (mLocationCurrent != null) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(mLocationCurrent.getLatitude(), mLocationCurrent.getLongitude())).zoom(12).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        });
    }


    public BitmapDescriptor getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable vectorDrawable = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            vectorDrawable = context.getDrawable(drawableId);
        }

        int h = 80;
        int w = 80;
        vectorDrawable.setBounds(0, 0, w, h);
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bm);
    }

    @Override
    public void setMarkerFromPlacePicker(Location mLocation) {

    }

    @Override
    public void setMarkerForCurrentLocation(Location mLocation) {

    }

    @Override
    public void setMarkerFromDb(Location mLocation) {
        setGoogleMapMarker(mLocationCurrent, mLocation);
    }

    @Override
    public Location getCurrentLocation() {
        return mLocationCurrent;
    }


    private void setShopLocation(Location mLocationSelected) {
        mLocationShop = mLocationSelected;
        setGoogleMapMarker(mLocationCurrent, mLocationSelected);
    }

    @Override
    public void returnToHome() {
        finish();
    }

    @Override
    public String getToken() {
        return getSharedPreferenceHelper().getString(getString(R.string.tocken), null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    buscinessLogic();
                } else {
                    showSnackBar(getString(R.string.permission_required));
                    getFragmentManager().popBackStack();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(UpdateShopLocationActivity.this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                LogUtils.v("location", toastMsg);
                showSnackBar(toastMsg);
                mLocationShop = new Location("dummyprovider");
                mLocationShop.setLongitude(place.getLatLng().longitude);
                mLocationShop.setLatitude(place.getLatLng().latitude);

                if (ETAddress1.getText().toString().equalsIgnoreCase("")) {
                    ETAddress1.setText(toastMsg);
                }

                setShopLocation(mLocationShop);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_try:
                showInternetAlertLogic(true);
                buscinessLogic();
                break;
            case R.id.BTNupdateStatus:
                mPresenter.postData();
                break;
            case R.id.BTNSelectPlace:
                mPresenter.selectPlace();
                break;
            case R.id.BTNnavigate:

                break;
        }
    }

    /////////////*******************


    public AppBaseActivity getBaseInstence() {
        return UpdateShopLocationActivity.this;
    }

    @Override
    public Context getViewContext() {
        return UpdateShopLocationActivity.this;
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return (AppBaseActivity) UpdateShopLocationActivity.this;
    }

    @Override
    public AppBaseActivity getBaseActivity() {
        return UpdateShopLocationActivity.this;
    }

    @Override
    public void showInternetAlertLogic(boolean isInternet) {
        if (isInternet == false) {
            LAYnointernet.setVisibility(View.VISIBLE);
        } else {
            parent.setVisibility(View.VISIBLE);
            LAYnointernet.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNodataAlertLogic(boolean isDataPresent) {

    }

    @Override
    public AppBaseActivity.OnFragmentSwitchListener getFragmentSwitch() {
        return null;
    }


}
