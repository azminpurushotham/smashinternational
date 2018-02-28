package com.cloudsys.smashintl.newlead.async;

import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.asynck.AppBaseServiceCall;
import com.cloudsys.smashintl.newlead.Presenter;
import com.cloudsys.smashintl.newlead.model.newlead;
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
    public void postNewLead(newlead data) {
        getApis().postNewLead(
                data.getUserId(),
                data.getCustomerName(),
                data.getEmail(),
                data.getSms(),
                data.getAddress1(),
                data.getAddress2(),
                data.getTelephone(),
                data.getPending(),
                data.getCurrency(),
                data.getCollecting(),
                data.getBill(),
                data.getToken(),
                data.getLat(),
                data.getLon()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject mJsonObject = new JSONObject(Utilities.getNullAsEmptyString(response));
                    if (mJsonObject.getBoolean("status")) {
                        mServiceCallBack.showWait(mServiceCallBack.getViewContext().getString(R.string.please_waite));
                        mServiceCallBack.completPosting();
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
                Log.v("onFailure", t.getMessage());
                mServiceCallBack.onFailerCallBack(R.string.api_default_error);
            }
        });
    }
}
