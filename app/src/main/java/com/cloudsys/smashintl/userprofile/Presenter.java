package com.cloudsys.smashintl.userprofile;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.userprofile.async.ServiceCall;
import com.cloudsys.smashintl.userprofile.async.ServiceCallBack;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack {
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    ActionView mView;
    ServiceCall mServiceCall;
    private String TAG = "LoginP";

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    @Override
    public void onUpdateUserClick() {
        if (Utilities.isInternet(mView.getViewContext())) {
            if (isValidate()) {
                showWait(mView.getViewContext().getString(R.string.updating));
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Refreshed token: " + refreshedToken);
                if (refreshedToken != null && !refreshedToken.equalsIgnoreCase("")) {
                    getSharedPreference().putString(mView.getStringRes(R.string.tocken), refreshedToken);
                    mServiceCall.postUpdatePassword(
                            getSharedPreferenceHelper().getString(mView.getStringRes(R.string.user_id), null),
                            mView.getNewPassword(),
                            mView.getOldPassword(),
                            getSharedPreference().getString(mView.getStringRes(R.string.tocken), null));
                } else {
                    mView.removeWait();
                    mView.showSnackBar(R.string.please_try_again);
                }

            }
        } else {
            mView.showSnackBar(R.string.no_network_connection);
        }
    }

    @Override
    public void setData() {

        Glide.with(mView.getBaseActivity())
                .load(mView.getImageUrl())
                .error(R.drawable.user_placeholder).dontAnimate()
                .placeholder(R.drawable.user_placeholder).dontAnimate()
                .into(mView.getCircleImageView());
        mView.setName();
        mView.dimissImagePregress();
        mView.removeWait();

    }


    private boolean isValidate() {
        if (mView.isPassWordChange()) {
            if (mView.getOldPassword().equalsIgnoreCase(mView.getNewPassword())) {
                mView.setErrorOldPasswordMissing(R.string.old_and_new_password_notmatching);
                return false;
            } else if (mView.getNewPassword().equalsIgnoreCase("")) {
                mView.setErrorNewPasswordMissing(R.string.missing_password);
                return false;
            } else if (mView.getConfirmPassword().equalsIgnoreCase("")) {
                mView.setErrorNewPasswordMissing(R.string.missing_password);
                return false;
            } else if (mView.getOldPassword().equalsIgnoreCase(mView.getNewPassword())) {
                mView.setErrorOldPasswordMissing(R.string.old_and_new_password_notmatching);
                return false;
            }
        } else if (mView.getImageUrl().contains("http") == false && mView.getImageUrl().equalsIgnoreCase("")) {
            return false;
        }

        return true;
    }

    @Override
    public String getUserName() {
        return mView.getUserName();
    }

    @Override
    public String getPassword() {
        return mView.getConfirmPassword();
    }

    @Override
    public String getOldPassword() {
        return mView.getOldPassword();
    }

    @Override
    public String getImageUrl() {
        return getSharedPreferenceHelper().getString(mView.getStringRes(R.string.user_image), "");
    }

    @Override
    public void onSuccessPasswordUpdate() {
        mView.removeWait();
    }

    @Override
    public void onSuccessPasswordUpdate(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onSuccessPasswordUpdate(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onSuccessPasswordUpdate(JSONObject message) {
        try {
            mView.showWait(message.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mView.removeWait();
        mView.loadHomePage();
    }

    @Override
    public void onFailPasswordUpdate() {
        mView.removeWait();
    }

    @Override
    public void onFailPasswordUpdate(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onFailPasswordUpdate(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onFailPasswordUpdate(JSONObject message) {
        try {
            mView.showWait(message.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mView.removeWait();
        mView.loadHomePage();
    }

    @Override
    public void onSuccessCallBack(String message) {
        mView.showSnackBar(message);
    }

    @Override
    public void onSuccessCallBack(JSONObject message) {
        try {
            mView.showWait(message.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mView.removeWait();
        mView.loadHomePage();
    }

    @Override
    public void onSuccessCallBack(int message) {
        mView.showSnackBar(message);
        mView.loadHomePage();
    }

    @Override
    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return super.getSharedPreference();
    }

    @Override
    public void onSuccessCallBack() {
        mView.loadHomePage();
    }

    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }

    @Override
    public void setJson(JSONObject mJsonObject) {

    }

    @Override
    public void onExceptionCallBack(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onExceptionCallBack(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onExceptionCallBack() {
        mView.removeWait();
    }

    @Override
    public void onFailerCallBack(String message) {
        Log.v("exception", message);
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onFailerCallBack(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onFailerCallBack() {
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside() {
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside(String message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }

    @Override
    public void onCallfailerFromServerside(JSONObject mJsonObject) {
        mView.removeWait();
        try {
            mView.showSnackBar(mJsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showWait(String message) {
        mView.showWait(message);
    }

    @Override
    public void showWait(JSONObject message) {
        try {
            mView.showWait(message.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showWait(int message) {
        mView.showSnackBar(message);
        mView.removeWait();
    }


    @Override
    public void permissionGranded(String permission) {

    }

    @Override
    public void permissionDenaid(String permission) {

    }

    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {
    }
}
