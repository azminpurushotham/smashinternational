package com.cloudsys.smashintl.home.async;

import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.home.Presenter;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.gson.JsonObject;
import com.webservicehelper.retrofit.RetrofitHelper;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 35.
 * Mfluid Mobile Apps Pvt Ltd
 */

public class ServiceCall implements ServiceAction {

    ServiceCallBack mServiceCallBack;

    public ServiceCall(Presenter presenter) {
        this.mServiceCallBack = presenter;
    }

    @Override
    public void getJson(String user_id, String tocken) {
        mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.loading));
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().getScheduledWorks(user_id, tocken)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getString("MessageType").equalsIgnoreCase("1")) {
                                mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.loading));
                                mServiceCallBack.setServices(mJsonObject);
                            } else {
                                mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.loading));
                                mServiceCallBack.showScnackBar(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
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
