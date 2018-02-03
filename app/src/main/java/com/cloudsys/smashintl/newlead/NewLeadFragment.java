package com.cloudsys.smashintl.newlead;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.main.MainActivity;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class NewLeadFragment extends AppBaseFragment implements ActionView, View.OnClickListener {

    //// DEFAULT///////
    @BindView(R.id.parent)
    RelativeLayout parent;
    Dialog mLoading;

    @BindView(R.id.MVmap)
    MapView MVmap;
    @BindView(R.id.ETCustomerName)
    EditText ETCustomerName;
    @BindView(R.id.ETCustomerId)
    EditText ETCustomerId;
    @BindView(R.id.ETTelephone)
    EditText ETTelephone;
    @BindView(R.id.ETEmail)
    EditText ETEmail;
    @BindView(R.id.ETSms)
    EditText ETSms;
    @BindView(R.id.ETAddress)
    EditText ETAddress;
    @BindView(R.id.ETAddress1)
    EditText ETAddress1;
    @BindView(R.id.RBpending)
    RadioButton RBpending;
    @BindView(R.id.ETamount)
    EditText ETamount;
    @BindView(R.id.ETbillId)
    EditText ETbillId;
    @BindView(R.id.BTNupdateStatus)
    Button BTNupdateStatus;

    private GoogleMap googleMap;
    private String id;
    Presenter mPresenter;


    LatLng latLngCurrent;
    private Location mLocationCurrent;
    LatLng latLngSelected;
    private Location mLocationSelected;

    public static final int REQUEST_PLACE_PICKER = 666;
    private static final int REQUEST_PERMISSIONS_LOCATION = 6;
    private double lat;
    private double lon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    private View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_new_lead, container, false);
        ButterKnife.bind(this, mView);

        MVmap.onCreate(savedInstanceState);
        MVmap.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MVmap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        googleMap.clear();
                        lat = latLng.latitude;
                        lon = latLng.longitude;
                        googleMap.addMarker(new MarkerOptions().position(latLng));
                    }
                });
            }
        });

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buscinessLogic();
    }

    private void buscinessLogic() {
        BTNupdateStatus.setOnClickListener(this);
        mPresenter = new com.cloudsys.smashintl.newlead.Presenter(this, getBaseInstence());
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(getActivity(), getActivity().getString(R.string.please_waite));
        }
//        mPresenter.setServiceData();
    }

    @Override
    public String getPendingAmount() {
        return ETamount.getText().toString().trim();
    }

    @Override
    public String getBillId() {
        return ETbillId.getText().toString().trim();
    }

    @Override
    public String getCustomerName() {
        return ETCustomerName.getText().toString().trim();
    }

    @Override
    public String getCustomerId() {
        return ETCustomerId.getText().toString().trim();
    }

    @Override
    public String getTelephoneNumber() {
        return ETTelephone.getText().toString().trim();
    }

    @Override
    public String getEmail() {
        return ETEmail.getText().toString().trim();
    }

    @Override
    public String getSMS() {
        return ETSms.getText().toString().trim();
    }

    @Override
    public String getAddress() {
        return ETAddress.getText().toString().trim();
    }

    @Override
    public Double getLat() {
        return lat;
    }

    @Override
    public Double getLon() {
        return lon;
    }

    @Override
    public String getCurrency() {
        return "inr";
    }

    @Override
    public void returnToHome() {
//        getChildFragmentManager().popBackStack();
        getActivity().onBackPressed();
    }

    @Override
    public String getStatus() {
        if (RBpending.isChecked()) {
            return getString(R.string.pending_);
        } else {
            return getString(R.string.completed_);
        }
    }

    private void setShopLocation(Location mLocationSelected) {
        this.mLocationSelected = mLocationSelected;
        setGoogleMapMarker(mLocationCurrent, false);
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
                                            getActivity(), R.drawable.current_location_24dp)));
                } else {

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .icon(
                                    getBitmapFromVectorDrawable(
                                            getActivity(), R.drawable.shop_24dp)));
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
                                            getActivity(), R.drawable.current_location_24dp)));
                } else {

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.latitude, location.longitude))
                            .icon(
                                    getBitmapFromVectorDrawable(
                                            getActivity(), R.drawable.shop_24dp)));
                }


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.latitude, location.longitude)).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    /////////////DEFAULTS///////////////////////

    public AppBaseFragment getBaseInstence() {
        return NewLeadFragment.this;
    }

    @Override
    public AppBaseFragment getViewBaseContext() {
        return NewLeadFragment.this;
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
    public void removeWait() {
        mLoading.dismiss();
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public RelativeLayout getParentView() {
        return parent;
    }

    @Override
    public void onFailure(String appErrorMessage) {
        getSnackBar(parent, appErrorMessage).show();
    }

    @Override
    public void showInternetAlertLogic(boolean isInternet) {
        if (isInternet == false) {
            showSnackBar(getString(R.string.no_internet));
        }
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
    public void onFinishActivity() {
        getActivity().finish();
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
                Place place = PlacePicker.getPlace(getActivity(), data);
                String toastMsg = String.format("Place: %s", place.getName());
                mPresenter.showSnackBar(toastMsg);
                mLocationSelected = new Location("dummyprovider");
                mLocationSelected.setLongitude(place.getLatLng().longitude);
                mLocationSelected.setLatitude(place.getLatLng().latitude);
                setShopLocation(mLocationSelected);

                ETAddress.setText(place.getAddress());
            }
        }
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTNupdateStatus:
                mPresenter.submitData();
                break;
        }
    }

    /////////////DEFAULTS///////////////////////


}
