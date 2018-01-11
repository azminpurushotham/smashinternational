package com.cloudsys.smashintl.login.async;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.login.Presenter;
import com.cloudsys.smashintl.login.model.LoginPojo;
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
    public void postLogin(String userName, String password, String tocken) {
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().postLogin(
                userName,
                password,
                tocken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    LoginPojo mPojo = new Gson().fromJson(response.toString(),LoginPojo.class);
                    if (mPojo.getStatus()) {
                        mServiceCallBack.showWait(mServiceCallBack.getStringRes(R.string.please_waite));
                        mServiceCallBack.onSuccess();
                    } else {
                        mServiceCallBack.showWait(mServiceCallBack.getStringRes(R.string.authentication_failed));
                        mServiceCallBack.showScnackBar(mServiceCallBack.getStringRes(R.string.authentication_failed));
                        mServiceCallBack.onCallfailerFromServerside();
                    }

                }catch (Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    mServiceCallBack.showScnackBar(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
                }
                mServiceCallBack.removeWait();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mServiceCallBack.showScnackBar(t.getMessage());
                mServiceCallBack.onFailer();
                mServiceCallBack.removeWait();
            }
        });
    }
}
