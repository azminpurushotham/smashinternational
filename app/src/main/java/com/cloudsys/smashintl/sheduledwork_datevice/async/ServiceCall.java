package com.cloudsys.smashintl.sheduledwork_datevice.async;

import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.asynck.AppBaseServiceCall;
import com.cloudsys.smashintl.sheduledwork_datevice.Presenter;
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

public class ServiceCall extends AppBaseServiceCall implements ServiceAction {

    ServiceCallBack mServiceCallBack;

    public ServiceCall(Presenter presenter) {
        this.mServiceCallBack = presenter;
    }

    @Override
    public void getJson(String user_id, String tocken,String date) {

        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().getScheduledWorksDateVice(user_id, tocken,date)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getBoolean("status")) {
                                mServiceCallBack.showWait(R.string.please_waite);
                                mServiceCallBack.onSuccessCallBack(mJsonObject);
                            } else {
                                mServiceCallBack.onCallfailerFromServerside(mJsonObject);
                            }

                        } catch (Exception e) {
                            if (e != null) {
                                e.printStackTrace();
                            }
                            mServiceCallBack.onExceptionCallBack(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.v("onFailure", t.getMessage());
                        mServiceCallBack.onFailerCallBack(t.getMessage());
                    }
                });

    }

    @Override
    public void getSearchScheduledWorks(String user_id, String tocken, String work_type, String query,String date) {
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis()
                .getSearchCompletedWorksDateVice(user_id,
                        tocken,
                        work_type,
                        query,
                        date)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getBoolean("status")) {
                                mServiceCallBack.onSuccessCallBack(mJsonObject);
                            } else {
                                mServiceCallBack.onCallfailerSearch(mJsonObject);
                            }

                        } catch (Exception e) {
                            if (e != null) {
                                e.printStackTrace();
                            }
                            mServiceCallBack.onExceptionCallBack(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.v("onFailure", t.getMessage());
                        mServiceCallBack.onFailerCallBack(t.getMessage());
                    }
                });

    }
}
