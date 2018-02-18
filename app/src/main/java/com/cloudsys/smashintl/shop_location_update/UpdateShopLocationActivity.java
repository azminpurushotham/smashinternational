package com.cloudsys.smashintl.shop_location_update;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.shop_location_update.model.ShopDetail;
import com.cloudsys.smashintl.utiliti.Utilities;
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
    Dialog mLoading;
    @BindView(R.id.LAYnodata)
    LinearLayout LAYnodata;

    @BindView(R.id.MVmap)
    MapView MVmap;
    @BindView(R.id.TVid)
    TextView TVid;
    @BindView(R.id.TVdate)
    TextView TVdate;
    @BindView(R.id.TVlocation)
    TextView TVlocation;
    @BindView(R.id.TVEmail)
    TextView TVEmail;
    @BindView(R.id.TVMobile)
    TextView TVMobile;
    @BindView(R.id.TVamount)
    TextView TVamount;
    @BindView(R.id.RBpending)
    RadioButton RBpending;
    @BindView(R.id.RBcomplete)
    RadioButton RBcomplete;
    @BindView(R.id.ETamount)
    EditText ETamount;
    @BindView(R.id.ETbillId)
    EditText ETbillId;
    @BindView(R.id.SPreason)
    Spinner SPreason;
    @BindView(R.id.BTNupdateStatus)
    Button BTNupdateStatus;
    @BindView(R.id.Layreason)
    LinearLayout Layreason;
    @BindView(R.id.EDTreason)
    EditText EDTreason;
    @BindView(R.id.TVSmsMobile)
    TextView TVSmsMobile;
    @BindView(R.id.ETcurrency)
    EditText ETcurrency;


    private GoogleMap googleMap;
    private String shop_id;
    Presenter mPresenter;


    LatLng latLngCurrent;
    private Location mLocationCurrent;
    LatLng latLngSelected;
    private Location mLocationSelected;

    public static final int REQUEST_PLACE_PICKER = 666;
    private static final int REQUEST_PERMISSIONS_LOCATION = 6;
    ShopDetail mPojo = new ShopDetail();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        shop_id = getIntent().getStringExtra("shop_id");
        MVmap.onCreate(savedInstanceState);
        MVmap.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(UpdateShopLocationActivity.this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkPermission();
    }


    private void buscinessLogic() {
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(UpdateShopLocationActivity.this, UpdateShopLocationActivity.this.getString(R.string.loading));
        }

        mPresenter = new Presenter(this, getBaseInstence());
        mPresenter.getScheduledWorkDetails();
        mPresenter.initSpinner();
        BTNupdateStatus.setOnClickListener(this);
        BTN_try.setOnClickListener(this);
        BTNSelectPlace.setOnClickListener(this);
        RBcomplete.setChecked(true);
        RBpending.setEnabled(false);
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
    public int getAmount() {
        if (ETamount.getText().toString().equalsIgnoreCase("")) {
            return 0;
        } else {
            return Integer.parseInt(ETamount.getText().toString());
        }
    }

    @Override
    public TextView getIdTextView() {
        return TVid;
    }

    @Override
    public TextView getLocationTextView() {
        return TVlocation;
    }

    @Override
    public TextView getDateTextView() {
        return TVdate;
    }

    @Override
    public TextView getEmailTextView() {
        return TVEmail;
    }

    @Override
    public TextView getPhoneTextView() {
        return TVMobile;
    }

    @Override
    public String getBillId() {
        return ETbillId.getText().toString().trim();
    }

    @Override
    public String getReason() {
        return "";
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
    public EditText getCurrencyEditText() {
        return ETcurrency;
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
    public void setCurrentLocation(Location mLocation) {
        mLocationCurrent = mLocation;
        setGoogleMapMarker(mLocationCurrent, true);
    }

    private void setGoogleMapMarker(final Location location, final boolean isCurrentLocation) {
        MVmap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                // For zooming automatically to the location of the marker
                mPresenter.setLocationOfShop();

                if (isCurrentLocation) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .icon(
                                    getBitmapFromVectorDrawable(
                                            UpdateShopLocationActivity.this, R.drawable.current_location_24dp)));
                } else {

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .icon(
                                    getBitmapFromVectorDrawable(
                                            UpdateShopLocationActivity.this, R.drawable.shop_24dp)));
                }


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    private void setGoogleMapMarker(final LatLng location, final boolean isCurrentLocation) {
        MVmap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                // For zooming automatically to the location of the marker
                mPresenter.setLocationOfShop();

                if (isCurrentLocation) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.latitude, location.longitude))
                            .icon(
                                    getBitmapFromVectorDrawable(
                                            UpdateShopLocationActivity.this, R.drawable.current_location_24dp)));
                } else {

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.latitude, location.longitude))
                            .icon(
                                    getBitmapFromVectorDrawable(
                                            UpdateShopLocationActivity.this, R.drawable.shop_24dp)));
                }


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.latitude, location.longitude)).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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

    private void setGoogleMapMarker(final LatLng location) {
        MVmap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                // For zooming automatically to the location of the marker
                mPresenter.setLocationOfShop();
                googleMap.addMarker(new MarkerOptions().position(
                        new LatLng(24.2282003, 55.7466362)));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.latitude, location.longitude)).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public void setPlacePickerLocation(Location mLocation) {
        this.mLocationSelected = mLocation;
        setGoogleMapMarker(mLocationSelected, false);
    }

    @Override
    public void setCurrentLocation(LatLng mLocation) {
        latLngCurrent = mLocation;
    }

    @Override
    public LatLng getCurrentLatLng() {
        return latLngSelected;
    }

    @Override
    public Location getCurrentLocation() {
        return mLocationCurrent;
    }

    @Override
    public void setPlacePickerLocation(LatLng mLocation) {
        latLngSelected = mLocation;
        setGoogleMapMarker(latLngSelected, false);
    }

    @Override
    public TextView getSmsPhoneTextView() {
        return TVSmsMobile;
    }

    @Override
    public EditText getReasonEditText() {
        return EDTreason;
    }

    @Override
    public LinearLayout getReasonLinearLay() {
        return Layreason;
    }

    private void setShopLocation(Location mLocationSelected) {
        this.mLocationSelected = mLocationSelected;
        setGoogleMapMarker(mLocationCurrent, false);
    }

    @Override
    public void returnToHome() {
        UpdateShopLocationActivity.this.onBackPressed();
    }

    @Override
    public Spinner getReasonSpinner() {
        return SPreason;
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(UpdateShopLocationActivity.this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                showSnackBar(toastMsg);
                mLocationSelected = new Location("dummyprovider");
                mLocationSelected.setLongitude(place.getLatLng().longitude);
                mLocationSelected.setLatitude(place.getLatLng().latitude);
                setShopLocation(mLocationSelected);

//                TVlocation.setText(place.getName());
                TVlocation.setText(place.getAddress());
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
                mPresenter.selectLocationPlacePicker();
                break;
            case R.id.BTNnavigate:
                Uri gmmIntentUri = null;
                String google_nav = "google.navigation:q=";

                if (latLngCurrent != null && latLngSelected != null) {
                    gmmIntentUri = Uri.parse(google_nav +
                            +latLngSelected.latitude + "," + latLngSelected.longitude);
                } else if (mLocationCurrent != null && mLocationSelected != null) {
                    gmmIntentUri = Uri.parse(google_nav
                            + mLocationSelected.getLatitude() + "," + mLocationSelected.getLongitude());
                }

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(UpdateShopLocationActivity.this.getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

                break;
        }
    }

    /////////////*******************


    public AppBaseActivity getBaseInstence() {
        return UpdateShopLocationActivity.this;
    }

    @Override
    public void showWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.show();
    }

    @Override
    public void showWait(int string_id) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(string_id));
        mLoading.show();
    }

    @Override
    public void removeWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.dismiss();
    }

    @Override
    public void removeWait(int message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(message));
        mLoading.dismiss();
    }

    @Override
    public void removeWait() {
        mLoading.dismiss();
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
    public AppBaseFragment getViewFragment() {
        return null;
    }

    @Override
    public AppBaseFragment getBaseFragment() {
        return null;
    }

    @Override
    public AppBaseActivity getBaseActivity() {
        return UpdateShopLocationActivity.this;
    }

    @Override
    public RelativeLayout getParentView() {
        return parent;
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

    @Override
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        snackbar.show();
    }

    @Override
    public void showSnackBar(int message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        snackbar.show();

    }

    @Override
    public void onFinishActivity() {
        UpdateShopLocationActivity.this.finish();
    }


    @Override
    public String getStringRes(int string_id) {
        return getString(string_id);
    }

}
