package com.cloudsys.smashintl.scheduleworkdetails;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
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
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleWorkDetailFragment extends AppBaseFragment implements ActionView, View.OnClickListener
{

    //// DEFAULT///////
    @BindView(R.id.parent)
    RelativeLayout parent;
    @Nullable
    @BindView(R.id.LAYnointernet)
    ConstraintLayout LAYnointernet;
    @BindView(R.id.BTN_try)
    Button BTN_try;
    com.cloudsys.smashintl.scheduleworkdetails.Presenter mPresenter;
    Dialog mLoading;
    @BindView(R.id.LAYnodata)
    LinearLayout LAYnodatal;

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

    private GoogleMap googleMap;
    private String userId,token,id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container,savedInstanceState);
    }

    private View initView(LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceSate) {
        View mView = inflater.inflate(R.layout.fragment_schedule_work_detail, container, false);
        ButterKnife.bind(this, mView);

        id=getArguments().getString("id");
        userId=getArguments().getString("userId");
        token=getArguments().getString("token");

        MVmap.onCreate(savedInstanceSate);
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
        mPresenter = new Presenter(this,getBaseInstence());
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(getActivity(), getActivity().getString(R.string.loading));
        }
        mPresenter.getScheduledWorkDetails();
        mPresenter.initSpinner();
        BTN_try.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MVmap.onResume();
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

    /////////////DEFAULTS///////////////////////

    public AppBaseFragment getBaseInstence() {
        return ScheduleWorkDetailFragment.this;
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
        if (isInternet == false) {
            LAYnointernet.setVisibility(View.VISIBLE);
        } else {
            parent.setVisibility(View.VISIBLE);
            LAYnointernet.setVisibility(View.GONE);
        }
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
        return ETamount.getText().toString().trim();
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
        return userId;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public GoogleMap getMap() {
        return googleMap;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_try:
                mPresenter.showNoInternetConnectionLayout(true);
                buscinessLogic();
                break;
            case R.id.BTNupdateStatus:
                mPresenter.postData();
                break;
        }
    }

}
