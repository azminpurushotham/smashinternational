package com.cloudsys.smashintl.userprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.userprofile.async.ServiceCall;
import com.cloudsys.smashintl.userprofile.async.ServiceCallBack;
import com.cloudsys.smashintl.utiliti.ImageUtility;
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
    public static String userChoosenTask;
    public static int REQUEST_CAMERA = 3;
    public static int SELECT_FILE = 6;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    @Override
    public void onUpdatePasswordClick() {
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
    public void onImageClick() {
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

    @Override
    public void pickImage() {
        selectImage();
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getBaseActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = ImageUtility.checkPermission(mView.getBaseActivity());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
//        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

    }

    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);
    }


    private boolean isValidate() {
        if (mView.isPassWordChange()) {
            if (mView.getOldPassword().equalsIgnoreCase("")) {
                mView.setErrorOldPasswordMissing(R.string.missing_password);
                return false;
            } else if (mView.getNewPassword().equalsIgnoreCase("")) {
                mView.setErrorNewPasswordMissing(R.string.missing_password);
                return false;
            } else if (mView.getConfirmPassword().equalsIgnoreCase("")) {
                mView.setErrorConfirmPasswordMissing(R.string.missing_password);
                return false;
            } else if (!mView.getNewPassword().equals(mView.getConfirmPassword())) {
                mView.setPasswordNotMaching(R.string.old_and_new_password_notmatching);
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
