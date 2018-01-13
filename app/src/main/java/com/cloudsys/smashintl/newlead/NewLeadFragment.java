package com.cloudsys.smashintl.newlead;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.utiliti.Utilities;
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

public class NewLeadFragment extends AppBaseFragment implements ActionView, View.OnClickListener {

    //// DEFAULT///////
    @BindView(R.id.parent)
    RelativeLayout parent;

    Presenter mPresenter;
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
    private double lat=0.0,lon=0.0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container,savedInstanceState);
    }

    private View initView(LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceState) {
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
                        lat=latLng.latitude;
                        lon=latLng.longitude;
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
        mPresenter = new com.cloudsys.smashintl.newlead.Presenter(this,getBaseInstence());
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(getActivity(), getActivity().getString(R.string.please_waite));
        }
//        mPresenter.setServiceData();
    }

    /////////////DEFAULTS///////////////////////

    public AppBaseFragment getBaseInstence() {
        return NewLeadFragment.this;
    }

    @Override
    public void showWait(Dialog mLoading) {
        mLoading.show();
    }

    @Override
    public void removeWait(Dialog mLoading) {
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
    }

    @Override
    public void showSnackBar(Snackbar snackBar) {
        snackBar.show();
    }

    @Override
    public void onFinishActivity() {
        getActivity().finish();
    }

    @Override
    public Dialog getLoading() {
        return mLoading;
    }

    @Override
    public String getStatus() {
        if(RBpending.isChecked()){
            return "pending";
        }else{
            return "completed";
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTNupdateStatus:
                mPresenter.submitData();
                break;
        }
    }
}
