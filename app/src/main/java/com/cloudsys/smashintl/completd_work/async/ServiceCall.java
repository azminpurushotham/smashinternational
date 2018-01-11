package com.cloudsys.smashintl.completd_work.async;

import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.completd_work.Presenter;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.gson.JsonObject;
import com.webservicehelper.retrofit.RetrofitHelper;

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
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().getScheduledWorks(user_id, /*tocken*/"1234")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getBoolean("status")) {
                                mServiceCallBack.showWait(mServiceCallBack.getStringRes(R.string.please_waite));
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
                            mServiceCallBack.showScnackBar(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
                            mServiceCallBack.removeWait();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.v("onFailure", t.getMessage());
                        mServiceCallBack.showScnackBar(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
                        mServiceCallBack.onFailer(t.getMessage());
                        mServiceCallBack.removeWait();
                    }
                });

    }
}
