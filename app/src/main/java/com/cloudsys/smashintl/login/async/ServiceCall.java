package com.cloudsys.smashintl.login.async;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.asynck.AppBaseServiceCall;
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

public class ServiceCall extends AppBaseServiceCall implements ServiceAction {

    ServiceCallBack mServiceCallBack;

    public ServiceCall(Presenter presenter) {
        this.mServiceCallBack = presenter;
    }

    @Override
    public void postLogin(String userName, String password, String tocken) {
        getApis().postLogin(
                userName,
                password,
                tocken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    LoginPojo mPojo = new Gson().fromJson(response.body().toString(), LoginPojo.class);
                    if (mPojo.getStatus()) {
                        mServiceCallBack.getSharedPreferenceHelper().putString(mServiceCallBack.getStringRes(R.string.user_id),
                                mPojo.getResult().getUserId());
                        mServiceCallBack.getSharedPreferenceHelper().putString(mServiceCallBack.getStringRes(R.string.user_name),
                                mPojo.getResult().getName());
                        mServiceCallBack.getSharedPreferenceHelper().putString(mServiceCallBack.getStringRes(R.string.user_image),
                                mPojo.getResult().getImage());
                        mServiceCallBack.onSuccessCallBack();
                    } else {
                        mServiceCallBack.userNamePasswordinCorrect(R.string.username_or_password_incorrect);
                    }

                } catch (Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    mServiceCallBack.onExceptionCallBack(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mServiceCallBack.onFailerCallBack(t.getMessage());
            }
        });
    }
}
