package com.cloudsys.smashintl.completd_work;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.completd_work.async.*;
import com.cloudsys.smashintl.completd_work.model.*;
import com.cloudsys.smashintl.itemdecorator.SpacesItemDecoration;
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
    CompletedWorkPojo mPojo = new CompletedWorkPojo();
    AppBaseActivity.OnFragmentSwitchListener onFragmentSwitchListener;
    List<Item> list = new ArrayList<Item>();

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
    public void getScheduledWork() {
        mView.showWait(mView.getStringRes(R.string.loading));
        if (Utilities.isInternet(mView.getViewContext())) {
            mServiceCall.getJson(
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.user_id), null),
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null));
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
        list = mPojo.getResult().getList();
        adapter = new ListItemAdapter(list, mView.getViewContext(), listner);
        mView.getRecyclerView().setAdapter(adapter);
        mView.setTotalAmount(mPojo.getResult().getTotalAmount());
        mView.setCompletedAmount(mPojo.getResult().getCompletedAmount());
        mView.setPendingAmount(mPojo.getResult().getPendingAmount());
        mView.setCurrency(mPojo.getResult().getCurrency());
    }

    @Override
    public void searchItems(String query) {
        if (Utilities.isInternet(mView.getViewContext())) {
            mServiceCall.getSearchCompletedWorks(
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.user_id), null),
                    getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null),
                    mView.getViewContext().getString(R.string.worktype_completed),
                    query);
        } else {
            mView.showSnackBar(R.string.no_network_connection);
        }
    }

    @Override
    public void onAdapterItemClick(Item Result, int adapterPosition) {
        String id = Result.getId();
        CompletdWorkFragment fragment = new CompletdWorkFragment();
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
        list = new ArrayList<Item>();
        adapter = new ListItemAdapter(list, mView.getViewContext(), listner);
        mView.getRecyclerView().setAdapter(adapter);
    }


    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }


    @Override
    public void onSuccessCallBack(JSONObject mJsonObject) {
        mView.removeWait();
        mPojo = new Gson().fromJson(mJsonObject.toString(), CompletedWorkPojo.class);
        setData();
    }

    @Override
    public void onSuccessCallBack(int message) {

    }

    @Override
    public void onSuccessCallBack() {

    }

    @Override
    public void onExceptionCallBack(String message) {

    }

    @Override
    public void onExceptionCallBack(int message) {

    }

    @Override
    public void onExceptionCallBack() {

    }

    @Override
    public void onFailerCallBack(String message) {
        Log.v("exception", message);
        mView.showSnackBar(message);
    }

    @Override
    public void onFailerCallBack(int message) {

    }

    @Override
    public void onFailerCallBack() {

    }

    @Override
    public void onCallfailerFromServerside() {

    }

    @Override
    public void onCallfailerFromServerside(String message) {

    }

    @Override
    public void onCallfailerFromServerside(int message) {

    }

    @Override
    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return super.getSharedPreference();
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
