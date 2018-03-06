package com.cloudsys.smashintl.completd_work;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.main.MainActivity;
import com.cloudsys.smashintl.utiliti.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by User on 11/30/2017.
 */

public class CompletdWorkFragment extends AppBaseFragment implements ActionView, View.OnClickListener,
        MainActivity.SearchQueryCompletedWork {

    private static final String TAG = "CompletdWork";
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
    //// DEFAULT///////

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.TVpendingAmount)
    TextView TVpendingAmount;
    @BindView(R.id.TVcompletedAmount)
    TextView TVcompletedAmount;
    @BindView(R.id.TVtotalAmount)
    TextView TVtotalAmount;
    String currency = "";


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
        View mView = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buscinessLogic();
    }

    private void buscinessLogic() {
        mPresenter = new Presenter(this, getBaseInstence());
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(getActivity(), getActivity().getString(R.string.loading));
        }
        mPresenter.initRecyclerView();
        mPresenter.getScheduledWork();
        BTN_try.setOnClickListener(this);
    }


    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void setPendingAmount(String value) {
        TVpendingAmount.setText(value +" "+currency);
    }

    @Override
    public void setCompletedAmount(String value) {
        TVcompletedAmount.setText(value+" "+currency);
    }

    @Override
    public void setTotalAmount(String value) {
        TVtotalAmount.setText(value+" "+currency);
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void onSearchQueryCompletedWork(String query) {
        mPresenter.searchItems(query);
    }


    public AppBaseFragment getBaseInstence() {
        return CompletdWorkFragment.this;
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return (AppBaseActivity) getActivity();
    }

    @Override
    public AppBaseFragment getViewFragment() {
        return CompletdWorkFragment.this;
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
    public void onFinishActivity() {
        getActivity().finish();
    }


    @Override
    public String getStringRes(int string_id) {
        return getString(string_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_try:
                showInternetAlertLogic(true);
                buscinessLogic();
                break;
        }
    }

}
