package com.cloudsys.smashintl.shop_location_update.async;

import com.base.log.LogUtils;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.asynck.AppBaseServiceCall;
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
                                 LogUtils.v(TAG, e.getMessage());
                            }
                            mServiceCallBack.onExceptionCallBack(R.string.api_default_error);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        LogUtils.v("onFailure", t.getMessage());
                        mServiceCallBack.onFailerCallBack(R.string.api_default_error);
                    }
                });

    }

    @Override
    public void updateShopLocation(String user_id,
                                   String token,
                                   String customer_id,
                                   String address_1,
                                   String address_2,
                                   String latitude,
                                   String longitude) {

        getApis().updateShopLocation(user_id,
                token,
                customer_id,
                address_1,
                address_2,
                latitude,
                longitude).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                    if (mJsonObject.getBoolean("status")) {
                        mServiceCallBack.completPosting(mJsonObject);
                    } else {
                        mServiceCallBack.onCallfailerFromServerside(mJsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    if (e != null) {
                         LogUtils.v(TAG, e.getMessage());
                    }
                    mServiceCallBack.onExceptionCallBack(R.string.api_default_error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                LogUtils.v("onFailure", t.getMessage());
                mServiceCallBack.onFailerCallBack(R.string.api_default_error);
            }
        });
    }

}
