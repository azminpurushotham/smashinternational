package com.cloudsys.smashintl.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.utiliti.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by User on 11/30/2017.
 */

public class HomeFragment extends AppBaseFragment implements ActionView, View.OnClickListener {

    private static final String TAG = "SelectProviderFragment";
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
    //// DEFAULT///////


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
        View mView = inflater.inflate(R.layout.home_fragment, container, false);
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
//        mPresenter.checkRunTimePermission(getBaseInstence(),"");
        mPresenter.setJson();
        mPresenter.getJson();
        BTN_try.setOnClickListener(this);
    }


    /////////////DEFAULTS///////////////////////

    public AppBaseFragment getBaseInstence() {
        return HomeFragment.this;
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
    public ViewPager getViewPager() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_try:
                mPresenter.showNoInternetConnectionLayout(true);
                buscinessLogic();
                break;
        }
    }

    /////////////DEFAULTS///////////////////////
}
