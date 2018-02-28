package com.cloudsys.smashintl.main.async;

import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.asynck.AppBaseServiceCall;
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
 */

public class ServiceCall extends AppBaseServiceCall implements ServiceAction {

    ServiceCallBack mServiceCallBack;

    public ServiceCall(Presenter presenter) {
        this.mServiceCallBack = presenter;
    }

    @Override
    public void getJson() {
    }

    @Override
    public void logOut() {
        getApis().postLogout(
                mServiceCallBack.getSharedPreferenceHelper().getString(mServiceCallBack.getViewContext().getString(R.string.user_id), null),
                mServiceCallBack.getSharedPreferenceHelper().getString(mServiceCallBack.getViewContext().getString(R.string.tocken), null))
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                            if (mJsonObject.getString("Value").contains("successfully")) {
                                mServiceCallBack.onSuccessLogout(mJsonObject);
                            } else {
                                mServiceCallBack.showWait(mJsonObject.getString("Value"));
                                mServiceCallBack.onCallfailerFromServerside(mJsonObject.getString("Value"));
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
                        Log.v("onFailure", t.getMessage());
                        mServiceCallBack.onCallfailerFromServerside(R.string.api_default_error);
                    }
                });

    }

}
