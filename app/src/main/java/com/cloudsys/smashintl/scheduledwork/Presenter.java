package com.cloudsys.smashintl.scheduledwork;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.itemdecorator.SpacesItemDecoration;
import com.cloudsys.smashintl.scheduledwork.async.ServiceCall;
import com.cloudsys.smashintl.scheduledwork.async.ServiceCallBack;
import com.cloudsys.smashintl.scheduledwork.model.Result;
import com.cloudsys.smashintl.scheduledwork.model.ScheduledWorkPojo;
import com.cloudsys.smashintl.scheduleworkdetails.ScheduleWorkDetailFragment;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack, ListItemAdapter.OnAdapterItemClick {
    ActionView mView;
    ServiceCall mServiceCall;
    private ListItemAdapter adapter;
    private ListItemAdapter.OnAdapterItemClick listner;
    ScheduledWorkPojo mPojo = new ScheduledWorkPojo();
    AppBaseActivity.OnFragmentSwitchListener onFragmentSwitchListener;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
        onFragmentSwitchListener= (AppBaseActivity.OnFragmentSwitchListener) getViewContext();
    }

    public Presenter(ActionView mView, AppBaseFragment baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
        onFragmentSwitchListener= (AppBaseActivity.OnFragmentSwitchListener) getViewContext();
    }


    @Override
    public void getScheduledWork() {
        mView.showWait(mView.getStringRes(R.string.loading));
        if (Utilities.isInternet(mView.getViewContext())) {
            mServiceCall.getJson(
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.user_id), "1"),
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null)
            );
        } else {
            mView.removeWait();
            mView.showInternetAlertLogic(false);
        }
    }

    @Override
    public void initRecyclerView() {
        mView.getRecyclerView().setLayoutManager(mView.getLinearLayoutManager());
        listner = (ListItemAdapter.OnAdapterItemClick) this;
        adapter = new ListItemAdapter(new ArrayList<Result>(), mView.getViewContext(), listner);
        mView.getRecyclerView().addItemDecoration(new SpacesItemDecoration(getViewContext().getResources().getInteger(R.integer.item_spacing)));
        mView.getRecyclerView().setAdapter(adapter);
    }

    @Override
    public void setData() {
        adapter = new ListItemAdapter(mPojo.getResult(), mView.getViewContext(), listner);
        mView.getRecyclerView().setAdapter(adapter);
    }


    /////////////DEFAULTS///////////////////////

    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }

    @Override
    public String getStringRes(int string_id) {
        return mView.getStringRes(string_id);
    }

    @Override
    public void onSuccess(JSONObject mJsonObject) {
        mPojo = new Gson().fromJson(mJsonObject.toString(), ScheduledWorkPojo.class);
        setData();
    }

    @Override
    public void onFailer(String message) {
        Log.v("exception", message);
        showSnackBar(message);
    }

    @Override
    public void onCallfailerFromServerside(JSONObject mJsonObject) {

    }

    @Override
    public void showScnackBar(String message) {
        showSnackBar(message);
    }

    @Override
    public void removeWait() {
        mView.removeWait();
    }


    @Override
    public void showWait(String message) {
        mView.showWait(message);
    }

    @Override
    public void showNoInternetConnectionLayout(boolean isInternet) {
        mView.showInternetAlertLogic(isInternet);
    }

    @Override
    public void showSnackBar(String message) {
        mView.showSnackBar(message);
    }


    private boolean checkAndRequestPermissions() {
        return true;
    }


    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {
    }

    @Override
    public void onAdapterItemClick(Result Result, int adapterPosition) {
        String userId=Result.getId();
        ScheduleWorkDetailFragment fragment=new ScheduleWorkDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putString("userId",userId);
        bundle.putString("token",getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null));
        fragment.setArguments(bundle);
        onFragmentSwitchListener.onFragmentSwitch(fragment,
                true,
                getViewContext().getString(R.string.tag_sheduled_work_detail),
                true,
                getViewContext().getString(R.string.tag_sheduled_work_detail));
    }

    /////////////DEFAULTS///////////////////////
}
