package com.cloudsys.smashintl.newlead;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class NewLeadFragment extends AppBaseFragment implements ActionView, View.OnClickListener, OnMapReadyCallback {

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
    @BindView(R.id.ETAddress1)
    EditText ETAddress1;
    @BindView(R.id.ETAddress2)
    EditText ETAddress2;
    @BindView(R.id.RBexisting)
    RadioButton RBexisting;
    @BindView(R.id.RBnew)
    RadioButton RBnew;
    @BindView(R.id.ETamount)
    EditText ETamount;
    @BindView(R.id.ETbillId)
    EditText ETbillId;
    @BindView(R.id.ETcompletedAmount)
    EditText ETcompletedAmount;
    @BindView(R.id.BTNupdateStatus)
    Button BTNupdateStatus;
    @BindView(R.id.BTNSelectPlace)
    Button BTNSelectPlace;


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
        initParentView(parent);

        MVmap.onCreate(savedInstanceState);
        MVmap.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getViewActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_LOCATION);
        } else {
            buscinessLogic();
        }
    }

    private void buscinessLogic() {
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(getActivity(), getActivity().getString(R.string.please_waite));
        }

        RBexisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RBexisting.isChecked()) {
                    ETCustomerId.setVisibility(View.VISIBLE);
                } else {
                    ETCustomerId.setVisibility(View.GONE);
                }
            }
        });

        RBnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RBexisting.isChecked()) {
                    ETCustomerId.setVisibility(View.VISIBLE);
                } else {
                    ETCustomerId.setVisibility(View.GONE);
                }
            }
        });


        BTNupdateStatus.setOnClickListener(this);
        BTNSelectPlace.setOnClickListener(this);
        mPresenter = new Presenter(this, getBaseInstence());


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
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
    public void startActivityForResultPlacePicker(Intent intent, int requestPlacePicker) {
        startActivityForResult(intent, requestPlacePicker);
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
    public void setPlacePickerLocation(LatLng mLocation) {
        latLngSelected = mLocation;
        setGoogleMapMarker(latLngSelected, false);
    }


    @Override
    public void setCurrentLocation(Location mLocation) {
        mLocationCurrent = mLocation;
        setGoogleMapMarker(mLocationCurrent, true);
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
    public String getAddress1() {
        return ETAddress1.getText().toString().trim();
    }

    @Override
    public String getAddress2() {
        return ETAddress2.getText().toString();
    }

    @Override
    public String getCompletedAmount() {
        return ETcompletedAmount.getText().toString();
    }

    @Override
    public Double getLat() {
        return mLocationCurrent.getLatitude();
    }

    @Override
    public Double getLon() {
        return mLocationCurrent.getLongitude();
    }

    @Override
    public String getCurrency() {
        return "INR";
    }

    @Override
    public void returnToHome() {
        getActivity().onBackPressed();
    }


    @Override
    public String getStatus() {
        return getString(R.string.completed_);
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
                showSnackBar(toastMsg);
                mLocationSelected = new Location("dummyprovider");
                mLocationSelected.setLongitude(place.getLatLng().longitude);
                mLocationSelected.setLatitude(place.getLatLng().latitude);
                setShopLocation(mLocationSelected);

                ETAddress1.setText(place.getAddress());
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTNupdateStatus:
                mPresenter.submitData();
                break;

            case R.id.BTNSelectPlace:
                mPresenter.selectLocationPlacePicker();
                break;

        }
    }
//////////////////////******************


    public AppBaseFragment getBaseInstence() {
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
        return getActivity();
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return (AppBaseActivity) getActivity();
    }

    @Override
    public AppBaseFragment getViewFragment() {
        return NewLeadFragment.this;
    }

    @Override
    public AppBaseFragment getBaseFragment() {
        return this;
    }

    @Override
    public AppBaseActivity getBaseActivity() {
        return (AppBaseActivity) getActivity();
    }

    @Override
    public RelativeLayout getParentView() {
        return parent;
    }

    @Override
    public void showInternetAlertLogic(boolean isInternet) {
        if (isInternet == false) {
            showSnackBar(R.string.no_internet);
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
        getActivity().finish();
    }


    @Override
    public String getStringRes(int string_id) {
        return getString(string_id);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Do other setup activities here too, as described elsewhere in this tutorial.
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents, null);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });
    }

}
