package com.cloudsys.smashintl.scheduledwork.async;

import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.scheduledwork.Presenter;
import com.cloudsys.smashintl.scheduledwork.model.ScheduledWorkPojo;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.gson.Gson;
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
    public void getJson(String user_id, String tocken) {
        mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.loading));
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().getScheduledWorks(user_id, tocken)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getBoolean("status")) {
                                mServiceCallBack.showWait(R.string.please_waite);
                                mServiceCallBack.removeWait();
                                mServiceCallBack.onSuccess(mJsonObject);
                            } else {
                                mServiceCallBack.showWait(mJsonObject.getString("message"));
                                mServiceCallBack.showScnackBar(mJsonObject.getString("message"));
                                mServiceCallBack.removeWait();
                                mServiceCallBack.onCallfailerFromServerside(mJsonObject);
                            }

                        } catch (Exception e) {
                            if (e != null) {
                                e.printStackTrace();
                            }
                            mServiceCallBack.showScnackBar(R.string.api_default_error);
                            mServiceCallBack.removeWait();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.v("onFailure", t.getMessage());
                        mServiceCallBack.showScnackBar(R.string.api_default_error);
                        mServiceCallBack.onFailer(t.getMessage());
                        mServiceCallBack.removeWait();

                        try {
                            JSONObject mJsonObject = new JSONObject("{\"result\":[{\"customer_id\":\"3\",\"name\":\"more\",\"id\":\"1\",\"address\":\"near highcourt,ernakulam\",\"lat\":\"55.54\",\"lon\":\"0.1\",\"status\":\"pending\",\"date\":\"2018-01-17 00:04:00\",\"amount\":\"3000\",\"currency\":\"INR\"}],\"status\":true,\"message\":\"Success\",\"error_code\":\"200\"}\"");
                            if (mJsonObject.getBoolean("status")) {
                                mServiceCallBack.showWait(R.string.please_waite);
                                mServiceCallBack.removeWait();
                                mServiceCallBack.onSuccess(mJsonObject);
                            } else {
                                mServiceCallBack.showWait(mJsonObject.getString("message"));
                                mServiceCallBack.showScnackBar(mJsonObject.getString("message"));
                                mServiceCallBack.removeWait();
                                mServiceCallBack.onCallfailerFromServerside(mJsonObject);
                            }
                        } catch (Exception e) {

                        }


                    }
                });

    }
}
