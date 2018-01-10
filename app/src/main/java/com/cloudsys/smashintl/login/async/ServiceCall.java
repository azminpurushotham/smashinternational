package com.cloudsys.smashintl.login.async;

import android.content.Context;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.login.Presenter;
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
    public void postLogin() {
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().postOtp().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                    if (mJsonObject.getString("MessageType").equalsIgnoreCase("1")) {
                        mServiceCallBack.showWait(mJsonObject.getJSONObject("Result").getJSONObject("Status").getString("description"));
                        mServiceCallBack.onSuccess(mJsonObject);
                    } else if (mJsonObject.getString("MessageType").equalsIgnoreCase("2")) {
                        mServiceCallBack.showWait(mJsonObject.getJSONObject("Message").getString("Value"));
                        mServiceCallBack.showScnackBar(mJsonObject.getJSONObject("Message").getString("Value"));
                        mServiceCallBack.onCallfailerFromServerside();
                    } else {
                        mServiceCallBack.showWait(mJsonObject.getJSONObject("Message").getString("Value"));
                        mServiceCallBack.showScnackBar(mJsonObject.getJSONObject("Message").getString("Value"));
                        mServiceCallBack.onCallfailerFromServerside();
                    }
                } catch (JSONException e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    mServiceCallBack.showScnackBar(mServiceCallBack.getViewContext().getString(R.string.api_default_error));
                }catch (Exception e){
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
                mServiceCallBack.onFailer(t.getMessage());
                mServiceCallBack.removeWait();
            }
        });
    }
}
