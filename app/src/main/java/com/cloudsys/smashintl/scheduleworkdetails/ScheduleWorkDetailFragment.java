package com.cloudsys.smashintl.scheduleworkdetails;

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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.cloudsys.smashintl.scheduleworkdetails.model.WorkDetailsPojo;
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

import static android.app.Activity.RESULT_OK;

public class ScheduleWorkDetailFragment extends AppBaseFragment implements ActionView, View.OnClickListener {

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
    @BindView(R.id.BTNnavigate)
    Button BTNnavigate;
    @BindView(R.id.Layreason)
    LinearLayout Layreason;
    @BindView(R.id.EDTreason)
    EditText EDTreason;
    @BindView(R.id.TVSmsMobile)
    TextView TVSmsMobile;
    @BindView(R.id.ETcurrency)
    EditText ETcurrency;


    private GoogleMap googleMap;
    private String id;
    Presenter mPresenter;


    LatLng latLngCurrent;
    private Location mLocationCurrent;
    LatLng latLngSelected;
    private Location mLocationSelected;

    public static final int REQUEST_PLACE_PICKER = 666;
    private static final int REQUEST_PERMISSIONS_LOCATION = 6;
    WorkDetailsPojo mPojo = new WorkDetailsPojo();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    private View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceSate) {
        View mView = inflater.inflate(R.layout.fragment_schedule_work_detail, container, false);
        ButterKnife.bind(this, mView);
        initParentView(parent);

        id = getArguments().getString("id");
        MVmap.onCreate(savedInstanceSate);
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

    private void buscinessLogic() {
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(getActivity(), getActivity().getString(R.string.loading));
        }

        ETamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    if (Integer.parseInt(getPojo().getResult().get(0).getAmount()) > Integer.parseInt(ETamount.getText().toString())) {
                        SPreason.setVisibility(View.VISIBLE);
                    } else {
                        SPreason.setVisibility(View.GONE);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    SPreason.setVisibility(View.GONE);
                }
            }
        });

        mPresenter = new Presenter(this, getBaseInstence());
        mPresenter.getScheduledWorkDetails();
        mPresenter.initSpinner();
        BTNupdateStatus.setOnClickListener(this);
        BTN_try.setOnClickListener(this);
        BTNnavigate.setOnClickListener(this);
        BTNSelectPlace.setOnClickListener(this);
        RBcomplete.setChecked(true);
        RBpending.setEnabled(false);
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
    public TextView getAmountTextView() {
        return TVamount;
    }

    @Override
    public RadioButton getPendingStatus() {
        return RBpending;
    }

    @Override
    public RadioButton getCompleteStatus() {
        return RBcomplete;
    }

    @Override
    public String getPendingAmount() {
        int temp = 0;
        if (mPojo.getResult().get(0).getAmount() != null
                && !mPojo.getResult().get(0).getAmount().equalsIgnoreCase("")
                && Integer.parseInt(mPojo.getResult().get(0).getAmount()) > 0
                && mPojo.getResult().get(0).getAmount() != null
                && !ETamount.getText().toString().equalsIgnoreCase("")
                && Integer.parseInt(ETamount.getText().toString()) > 0
                && Integer.parseInt(mPojo.getResult().get(0).getAmount()) > Integer.parseInt(ETamount.getText().toString())) {
            temp = Integer.parseInt(mPojo.getResult().get(0).getAmount()) - Integer.parseInt(ETamount.getText().toString());
        }
        return temp + "";
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
        return id;
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
    public WorkDetailsPojo getPojo() {
        return mPojo;
    }

    @Override
    public void setPojo(WorkDetailsPojo mPojo) {
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
        getActivity().onBackPressed();
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
                Place place = PlacePicker.getPlace(getActivity(), data);
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

                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

                break;
        }
    }

    /////////////*******************


    public AppBaseFragment getBaseInstence() {
        return ScheduleWorkDetailFragment.this;
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
        return ScheduleWorkDetailFragment.this;
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
