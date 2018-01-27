package com.cloudsys.smashintl.workdetailview.async;

import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.cloudsys.smashintl.workdetailview.Presenter;
import com.cloudsys.smashintl.workdetailview.model.scheduleWorkPojo;
import com.google.gson.JsonObject;
import com.webservicehelper.retrofit.RetrofitHelper;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 35.
 */

public class ServiceCall implements ServiceAction {

    ServiceCallBack mServiceCallBack;

    public ServiceCall(Presenter presenter) {
        this.mServiceCallBack = presenter;
    }

    @Override
    public void getJson(String userId,String token,String id) {
        mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.loading));
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().getScheduledWorksDetail(id,userId,token)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getBoolean("status")) {
                                mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.please_waite));
                                mServiceCallBack.removeWait();
                                mServiceCallBack.setServices(mJsonObject);
                            } else {
                                mServiceCallBack.showWait(mJsonObject.getString("message"));
                                mServiceCallBack.showScnackBar(mJsonObject.getString("message"));
                                mServiceCallBack.removeWait();
                                mServiceCallBack.onCallfailerFromServerside();
                            }
                        } catch (JSONException e) {
                            if (e != null) {
                                e.printStackTrace();
                            }
                            mServiceCallBack.showScnackBar(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
                        }
                        mServiceCallBack.removeWait();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.v("onFailure", t.getMessage());
                        mServiceCallBack.showScnackBar(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
                        mServiceCallBack.removeWait();
                    }
                });

    }

    @Override
    public void sendData(scheduleWorkPojo data) {
        mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.loading));
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().updateWorkStatus(data.getUserId(),data.getToken(),data.getBranch_id(),
                data.getEmail(),data.getSms_no(),data.getBranch_name(),data.getAddress(),data.getTelephone_no(),
                data.getStatus(),data.getCollection_amount(),data.getReason(),data.getBill_id()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                    if (mJsonObject.getBoolean("status")) {
                        mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.please_waite));
                        mServiceCallBack.removeWait();
                        mServiceCallBack.showScnackBar(mJsonObject.getString("message"));
                        mServiceCallBack.completPosting();
                    } else {
                        mServiceCallBack.showWait(mJsonObject.getString("message"));
                        mServiceCallBack.showScnackBar(mJsonObject.getString("message"));
                        mServiceCallBack.removeWait();
                        mServiceCallBack.onCallfailerFromServerside();
                    }
                } catch (JSONException e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    mServiceCallBack.showScnackBar(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
                }
                mServiceCallBack.removeWait();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.v("onFailure", t.getMessage());
                mServiceCallBack.showScnackBar(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
                mServiceCallBack.removeWait();
            }
        });
    }
}