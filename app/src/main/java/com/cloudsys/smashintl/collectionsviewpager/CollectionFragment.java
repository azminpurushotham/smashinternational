package com.cloudsys.smashintl.collectionsviewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by User on 11/30/2017.
 */

public class CollectionFragment extends AppBaseFragment implements ActionView {

    private static final String TAG = "CollectionFragment";
    Presenter mPresenter;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.tab)
    TabLayout tab;


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
        mPresenter.setData();
    }


    @Override
    public ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public TabLayout getTabLayOut() {
        return tab;
    }


    public AppBaseFragment getBaseInstence() {
        return CollectionFragment.this;
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
        return CollectionFragment.this;
    }

    @Override
    public AppBaseFragment getBaseFragment() {
        return CollectionFragment.this;
    }

    @Override
    public AppBaseActivity getBaseActivity() {
        return (AppBaseActivity) getActivity();
    }

    @Override
    public void onFinishActivity() {
        getActivity().finish();
    }

    @Override
    public void showInternetAlertLogic(boolean isInternet) {

    }

    @Override
    public void showNodataAlertLogic(boolean isDataPresent) {

    }

    @Override
    public AppBaseActivity.OnFragmentSwitchListener getFragmentSwitch() {
        return null;
    }


    @Override
    public void showWait(String mesage) {

    }

    @Override
    public void showWait(int mesage) {

    }

    @Override
    public void removeWait(String message) {

    }

    @Override
    public void removeWait(int message) {

    }

    @Override
    public void removeWait() {

    }

    @Override
    public void showSnackBar(String message) {

    }

    @Override
    public void showSnackBar(int message) {

    }

    @Override
    public String getStringRes(int string_id) {
        return getString(string_id);
    }

    /////////////DEFAULTS///////////////////////
}
