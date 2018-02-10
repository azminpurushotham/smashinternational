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

        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().getScheduledWorks(user_id, tocken)
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
    public void getSearchScheduledWorks(String user_id, String tocken, String work_type, String query) {
        mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.searching));
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis()
                .getSearchScheduledWorks(user_id,
                        tocken,
                        work_type,
                        query)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getBoolean("status")) {
                                mServiceCallBack.showWait(R.string.please_waite);
                                mServiceCallBack.onSuccessCallBack(mJsonObject);
                            } else {
                                mServiceCallBack.showWait(mJsonObject.getString("message"));
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
