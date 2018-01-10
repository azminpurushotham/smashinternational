package com.cloudsys.smashintl.main.async;

import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.main.Presenter;
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
    public void getJson() {
//        try {
//
//            JSONObject mJsonObject = new JSONObject(jsonObject.toString());
//            if (mJsonObject.getJSONObject("VerifyInfo").getString("CodeState").equalsIgnoreCase("1")) {
//
//            } else {
//                mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.invalid_otp));
//                mServiceCallBack.onFailer(mServiceCallBack.getViewContext().getString(R.string.invalid_otp));
//                mServiceCallBack.removeWait();
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void logOut() {
        new RetrofitHelper(mServiceCallBack.getViewContext()).getApis().postLogout(
                mServiceCallBack.getSharedPreferenceHelper().getString(mServiceCallBack.getViewContext().getString(R.string.user_id), null),
                mServiceCallBack.getSharedPreferenceHelper().getString(mServiceCallBack.getViewContext().getString(R.string.tocken), null))
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getString("Value").contains("successfully")) {
                                mServiceCallBack.showWait(mJsonObject.getString("Value"));
                                mServiceCallBack.onSuccessLogout(mJsonObject);
                            } else {
                                mServiceCallBack.showWait(mJsonObject.getString("Value"));
                                mServiceCallBack.showScnackBar(mJsonObject.getString("Value"));
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
