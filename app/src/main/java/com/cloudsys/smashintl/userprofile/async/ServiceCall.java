package com.cloudsys.smashintl.userprofile.async;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.asynck.AppBaseServiceCall;
import com.cloudsys.smashintl.userprofile.Presenter;
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
    public void postUpdatePassword(String user_id, String new_password, String old_password, String tocken) {
        getApis().postUpdatePassword(
                user_id,
                new_password,
                old_password,
                tocken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                    if (mJsonObject.getBoolean("status")) {
                        mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.please_waite));
                        mServiceCallBack.onSuccessCallBack(mJsonObject.getString("message"));
                    } else {
                        mServiceCallBack.showWait(mJsonObject.getString("message"));
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
                mServiceCallBack.onFailerCallBack(t.getMessage());
            }
        });
    }

    @Override
    public void postUpdateImage(String user_id, String image, String tocken) {
//        getApis().postUpdateImage(
//                user_id,
//                image,
//                tocken).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                try {
//                    JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
//                    if (mJsonObject.getBoolean("status")) {
//                        mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.please_waite));
//                        mServiceCallBack.onSuccessCallBack(mJsonObject.getString("message"));
//                    } else {
//                        mServiceCallBack.showWait(mJsonObject.getString("message"));
//                        mServiceCallBack.onCallfailerFromServerside(mJsonObject.getString("message"));
//                    }
//                } catch (JSONException e) {
//                    if (e != null) {
//                        e.printStackTrace();
//                    }
//                    mServiceCallBack.onExceptionCallBack(R.string.api_default_error);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mServiceCallBack.onFailerCallBack(t.getMessage());
//            }
//        });
    }
}
