package com.cloudsys.smashintl.newlead;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewLeadFragment extends AppBaseFragment implements ActionView, View.OnClickListener {

    //// DEFAULT///////
    @BindView(R.id.parent)
    RelativeLayout parent;
    @Nullable
    @BindView(R.id.LAYnointernet)
    ConstraintLayout LAYnointernet;
    @BindView(R.id.BTN_try)
    Button BTN_try;
    Presenter mPresenter;
    Dialog mLoading;
    @BindView(R.id.LAYnodata)
    LinearLayout LAYnodatal;

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
    @BindView(R.id.RDcomplete)
    RadioButton RDcomplete;
    @BindView(R.id.ETamount)
    EditText ETamount;
    @BindView(R.id.ETbillId)
    EditText ETbillId;
    @BindView(R.id.SPreason)
    Spinner SPreason;
    @BindView(R.id.BTNupdateStatus)
    Button BTNupdateStatus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View mView = inflater.inflate(R.layout.fragment_schedule_work_detail, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buscinessLogic();
    }

    private void buscinessLogic() {
        mPresenter = new com.cloudsys.smashintl.newlead.Presenter(this,getBaseInstence());
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(getActivity(), getActivity().getString(R.string.loading));
        }
        mPresenter.setServiceData();
        BTN_try.setOnClickListener(this);
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
    public String getStatus() {
        return null;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_try:
                mPresenter.showNoInternetConnectionLayout(true);
                buscinessLogic();
                break;
            case R.id.BTNupdateStatus:
                break;
        }
    }
}
