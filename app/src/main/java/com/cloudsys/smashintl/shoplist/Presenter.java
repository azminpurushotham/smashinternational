package com.cloudsys.smashintl.shoplist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.base.log.LogUtils;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.itemdecorator.SpacesItemDecoration;
import com.cloudsys.smashintl.shop_location_update.UpdateShopLocationActivity;
import com.cloudsys.smashintl.shoplist.async.ServiceCall;
import com.cloudsys.smashintl.shoplist.async.ServiceCallBack;
import com.cloudsys.smashintl.shoplist.model.Result;
import com.cloudsys.smashintl.shoplist.model.ShopListPojo;
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
        ListItemAdapter.OnAdapterItemClick, View.OnClickListener {
    ActionView mView;
    ServiceCall mServiceCall;
    private ListItemAdapter adapter;
    private ListItemAdapter.OnAdapterItemClick listner;
    ShopListPojo mPojo = new ShopListPojo();
    List<Result> list = new ArrayList<Result>();
    Result mResult = new Result();
    private Dialog mDialog;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    public Presenter(ActionView mView, AppBaseFragment baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }


    @Override
    public void getShopList() {
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
        mView.getRecyclerView().addItemDecoration(
                new SpacesItemDecoration(getViewContext().getResources().getInteger(R.integer.item_spacing_10)));
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
                    query);
        } else {
            mView.showSnackBar(R.string.no_network_connection);
        }
    }

    @Override
    public void onAdapterItemClick(Result Result, int adapterPosition) {
        String id = Result.getId();
        mResult = Result;
        if (Result.getIsLatLong().equalsIgnoreCase("0")) {
            Intent mIntent = new Intent(mView.getBaseActivity(), UpdateShopLocationActivity.class);
            mIntent.putExtra("shop_id", id);
            mView.getBaseActivity().startActivity(mIntent);
        }else {
            showConFirMationDialouge();
        }
    }

    private void showConFirMationDialouge() {
        if (mDialog == null) {
            mDialog = getConfirmationDialouge();
        }
        if (mDialog.isShowing() == false) {
            mDialog.show();
        }
    }

    private Dialog getConfirmationDialouge() {
        Dialog mDialog = null;
        Button BTNclose, BTNok;
        TextView TVtitle, TVmessage;

        mDialog = new Dialog(mView.getBaseActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialouge_message_with_positive_and_negative_button);

        TVtitle = (TextView) mDialog.findViewById(R.id.TVtitle);
        TVmessage = (TextView) mDialog.findViewById(R.id.TVmessage);
        BTNclose = (Button) mDialog.findViewById(R.id.BTNclose);
        BTNok = (Button) mDialog.findViewById(R.id.BTNok);

        BTNclose.setText(R.string.cancel);
        BTNok.setText(R.string.ok);
        TVtitle.setText(R.string.update_customer_location);
        TVmessage.setText(R.string.do_you_update_customer_location);

        BTNclose.setOnClickListener(this);
        BTNok.setOnClickListener(this);

        mDialog.setCancelable(true);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return mDialog;
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
        mPojo = new Gson().fromJson(mJsonObject.toString(), ShopListPojo.class);
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
        LogUtils.v("exception", message);
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
             LogUtils.v(TAG, e.getMessage());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.BTNclose:
                mDialog.dismiss();
                break;

            case R.id.BTNok:
                mDialog.dismiss();
                Intent mIntent = new Intent(mView.getBaseActivity(), UpdateShopLocationActivity.class);
                mIntent.putExtra("shop_id", mResult.getId());
                mView.getBaseActivity().startActivity(mIntent);
                break;

        }

    }
}
