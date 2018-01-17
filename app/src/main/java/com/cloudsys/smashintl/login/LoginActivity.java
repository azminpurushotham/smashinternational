package com.cloudsys.smashintl.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.main.MainActivity;
import com.cloudsys.smashintl.utiliti.Utilities;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by azmin on 27/01/17.
 */

public class LoginActivity extends AppBaseActivity implements ActionView, View.OnClickListener {

    private static final String TAG = "LoginActivity";
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
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        buscinessLogic();
    }

    private void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void buscinessLogic() {
        mPresenter = new Presenter(this, getBaseInstence());
        BTNlogin.setOnClickListener(this);
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(LoginActivity.this, getString(R.string.loading));
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
        startActivity(MainActivity.getStartIntent(LoginActivity.this));
        finish();
    }


    /////////////DEFAULT CALLBACKS///////////////////////

    @Override
    public Context getViewContext() {
        return LoginActivity.this;
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
    public void onFailure(String appErrorMessage) {
        getSnackBar(parent, appErrorMessage).show();
    }


    @Override
    public void showNoInternetConnectionLayout(boolean isInternet) {
        if (isInternet == false) {
            parent.setVisibility(View.GONE);
            LAYnointernet.setVisibility(View.VISIBLE);
        } else {
            parent.setVisibility(View.VISIBLE);
            LAYnointernet.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSnackBar(Snackbar snackBar) {
        snackBar.show();
    }

    @Override
    public void onFinishActivity() {
        finish();
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
                mPresenter.showNoInternetConnectionLayout(true);
                buscinessLogic();
                break;
        }
    }

    /////////////DEFAULT CALLBACKS///////////////////////

}
