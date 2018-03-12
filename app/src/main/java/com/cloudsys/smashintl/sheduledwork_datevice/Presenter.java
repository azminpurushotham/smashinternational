package com.cloudsys.smashintl.sheduledwork_datevice;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.itemdecorator.SpacesItemDecoration;
import com.cloudsys.smashintl.sheduledwork_datevice.async.ServiceCall;
import com.cloudsys.smashintl.sheduledwork_datevice.async.ServiceCallBack;
import com.cloudsys.smashintl.sheduledwork_datevice.model.Result;
import com.cloudsys.smashintl.sheduledwork_datevice.model.ScheduledWorkPojo;
import com.cloudsys.smashintl.scheduleworkdetails.ScheduleWorkDetailFragment;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack,
        ListItemAdapter.OnAdapterItemClick {
    ActionView mView;
    ServiceCall mServiceCall;
    private ListItemAdapter adapter;
    private ListItemAdapter.OnAdapterItemClick listner;
    ScheduledWorkPojo mPojo = new ScheduledWorkPojo();
    AppBaseActivity.OnFragmentSwitchListener onFragmentSwitchListener;
    List<Result> list = new ArrayList<Result>();
    String date = "";

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
        onFragmentSwitchListener = (AppBaseActivity.OnFragmentSwitchListener) getViewContext();
    }

    public Presenter(ActionView mView, AppBaseFragment baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
        onFragmentSwitchListener = (AppBaseActivity.OnFragmentSwitchListener) getViewContext();
    }


    @Override
    public void getScheduledWorksDateVice(String date) {
        this.date = date;
        mView.showWait(mView.getStringRes(R.string.loading));
        if (Utilities.isInternet(mView.getViewContext())) {
            mServiceCall.getJson(
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.user_id), null),
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null),
                    date);
        } else {
            mView.removeWait();
            mView.showInternetAlertLogic(false);
        }
    }

    @Override
    public void initRecyclerView() {
        mView.getRecyclerView().setLayoutManager(mView.getLinearLayoutManager());
        listner = (ListItemAdapter.OnAdapterItemClick) this;
        adapter = new ListItemAdapter(list, mView.getViewContext(), listner);
        mView.getRecyclerView().addItemDecoration(new SpacesItemDecoration(getViewContext().getResources().getInteger(R.integer.item_spacing)));
        mView.getRecyclerView().setAdapter(adapter);
    }

    @Override
    public void setData() {
        list = mPojo.getResult();
        adapter = new ListItemAdapter(list, mView.getViewContext(), listner);
        mView.getRecyclerView().setAdapter(adapter);
    }

    @Override
    public void searchItems(String query) {
        if (Utilities.isInternet(mView.getViewContext())) {
            mServiceCall.getSearchScheduledWorks(
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.user_id), null),
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null),
                    mView.getViewContext().getString(R.string.worktype_pending),
                    query,
                    date);
        } else {
            mView.showSnackBar(R.string.no_network_connection);
        }
    }

    @Override
    public void onAdapterItemClick(Result Result, int adapterPosition) {
        String id = Result.getId();
        ScheduleWorkDetailFragment fragment = new ScheduleWorkDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("token", getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null));
        fragment.setArguments(bundle);
        onFragmentSwitchListener.onFragmentSwitch(fragment,
                true,
                getViewContext().getString(R.string.title_sheduled_work_detail),
                true,
                getViewContext().getString(R.string.title_sheduled_work_detail));
    }

    @Override
    public void onCallfailerSearch(JSONObject mJsonObject) {
        list = new ArrayList<Result>();
        adapter = new ListItemAdapter(list, mView.getViewContext(), listner);
        mView.getRecyclerView().setAdapter(adapter);
    }


    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }


    @Override
    public void setJson(JSONObject mJsonObject) {

    }

    @Override
    public void onSuccessCallBack(JSONObject mJsonObject) {
        mView.removeWait();
        mPojo = new Gson().fromJson(mJsonObject.toString(), ScheduledWorkPojo.class);
        setData();
    }

    @Override
    public void onSuccessCallBack(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onSuccessCallBack() {
        mView.removeWait();
    }

    @Override
    public void onExceptionCallBack(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onExceptionCallBack(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onExceptionCallBack() {
        mView.removeWait();
    }

    @Override
    public void onFailerCallBack(String message) {
        Log.v("exception", message);
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onFailerCallBack(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onFailerCallBack() {
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside() {
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside(JSONObject mJsonObject) {
        mView.removeWait();
        try {
            mView.showSnackBar(mJsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessCallBack(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }


    @Override
    public void permissionGranded(String permission) {

    }

    @Override
    public void permissionDenaid(String permission) {

    }

    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {
    }

}
