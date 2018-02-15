package com.cloudsys.smashintl.userprofile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.main.MainActivity;
import com.cloudsys.smashintl.utiliti.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by azmin on 16/2/18.
 */

public class UserProfileActivity extends AppBaseActivity implements ActionView, View.OnClickListener {

    private static final String TAG = "UserProfileActivity";
    @BindView(R.id.EDTUserName)
    EditText EDTUserName;
    @BindView(R.id.EDTPassword)
    EditText EDTPassword;
    @BindView(R.id.BTNlogin)
    Button BTNlogin;
    @BindView(R.id.parent)
    LinearLayout parent;
    @BindView(R.id.LAYnointernet)
    ConstraintLayout LAYnointernet;
    @BindView(R.id.BTN_try)
    Button BTN_try;

    Presenter mPresenter;
    Dialog mLoading;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        buscinessLogic();
    }

    private void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_userprofile);
        ButterKnife.bind(this);
    }

    private void buscinessLogic() {
        mPresenter = new Presenter(this, getBaseInstence());
        BTNlogin.setOnClickListener(this);
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(UserProfileActivity.this, getString(R.string.loading));
        }
    }


    @Override
    public void setErrorUserNameMissing(int message) {
        EDTUserName.setError(getString(message));
        EDTUserName.requestFocus();
    }

    @Override
    public void setErrorPasswordMissing(int message) {
        EDTPassword.setError(getString(message));
        EDTPassword.requestFocus();
    }

    @Override
    public void setErrorUserNameInvalid(int message) {
        EDTUserName.setError(getString(message));
        EDTUserName.requestFocus();
    }

    @Override
    public void setErrorPassWordInvalid(int message) {
        EDTPassword.setError(getString(message));
        EDTPassword.requestFocus();
    }

    @Override
    public String getUserName() {
        return EDTUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return EDTPassword.getText().toString();
    }

    @Override
    public void loadHomePage() {
        startActivity(MainActivity.getStartIntent(UserProfileActivity.this));
        finish();
    }


    @Override
    public Context getViewContext() {
        return UserProfileActivity.this;
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return UserProfileActivity.this;
    }

    @Override
    public AppBaseFragment getViewFragment() {
        return null;
    }

    @Override
    public AppBaseFragment getBaseFragment() {
        return null;
    }

    @Override
    public AppBaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public LinearLayout getParentView() {
        return parent;
    }

    @Override
    public void onBackPressed() {
        onBackPressedLogic();
    }

    @Override
    public void showWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.show();
    }

    @Override
    public void showWait(int message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(message));
        mLoading.show();
    }

    @Override
    public void removeWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.show();
    }

    @Override
    public void removeWait(int message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(message));
        mLoading.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }

    @Override
    public void removeWait() {
        mLoading.dismiss();
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        snackbar.show();
    }

    @Override
    public void showSnackBar(int message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        snackbar.show();
    }

    @Override
    public String getStringRes(int string_id) {
        return getString(string_id);
    }



    @Override
    public void onFinishActivity() {
        finish();
    }

    @Override
    public void showInternetAlertLogic(boolean isInternet) {
        if (isInternet == false) {
            parent.setVisibility(View.GONE);
            LAYnointernet.setVisibility(View.VISIBLE);
        } else {
            parent.setVisibility(View.VISIBLE);
            LAYnointernet.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNodataAlertLogic(boolean isDataPresent) {

    }

    @Override
    public OnFragmentSwitchListener getFragmentSwitch() {
        return null;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void onBackPressedLogic() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.BTNlogin:
                mPresenter.onLoginClick();
                break;

            case R.id.BTN_try:
                showInternetAlertLogic(true);
                buscinessLogic();
                break;
        }
    }

    /////////////DEFAULT CALLBACKS///////////////////////

}
