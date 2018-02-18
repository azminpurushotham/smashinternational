package com.cloudsys.smashintl.shop_location_update.async;

import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.asynck.AppBaseServiceCall;
import com.cloudsys.smashintl.shop_location_update.model.scheduleWorkPojo;
import com.cloudsys.smashintl.shop_location_update.Presenter;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 35.
 */

public class ServiceCall extends AppBaseServiceCall implements ServiceAction {

    ServiceCallBack mServiceCallBack;

    public ServiceCall(Presenter presenter) {
        this.mServiceCallBack = presenter;
    }

    @Override
    public void getJson(String id, String userId, String token) {
        getApis().getShopDetail(
                id,
                userId,
                token)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getBoolean("status")) {
                                mServiceCallBack.setServices(mJsonObject);
                            } else {
                                mServiceCallBack.onCallfailerFromServerside(mJsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            if (e != null) {
                                e.printStackTrace();
                            }
                            mServiceCallBack.onExceptionCallBack(R.string.api_default_error);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.v("onFailure", t.getMessage());
                        mServiceCallBack.onFailerCallBack(R.string.api_default_error);
                    }
                });

    }

    @Override
    public void postUpdateWorkStatus(scheduleWorkPojo data) {
        getApis().updateWorkStatus(
                data.getUserId(),
                data.getToken(),
                data.getBranch_id(),
                data.getBranch_name(),
                data.getAddress1(),
                data.getAddress2(),
                data.getEmail(),
                data.getSms_no(),
                data.getTelephone_no(),
                data.getStatus(),
                data.getCollection_amount(),
                data.getPendingAmount(),
                data.getReason(),
                data.getBill_id()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                    if (mJsonObject.getBoolean("status")) {
                        mServiceCallBack.completPosting(mJsonObject);
                    } else {
                        mServiceCallBack.onCallfailerFromServerside(mJsonObject.getString("message"));
                    }
                } catch (
                        JSONException e)

                {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    mServiceCallBack.onExceptionCallBack(R.string.api_default_error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.v("onFailure", t.getMessage());
                mServiceCallBack.onFailerCallBack(R.string.api_default_error);
            }
        });
    }
}
