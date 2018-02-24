package com.cloudsys.smashintl.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.main.MainActivity;

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
        initParentView(parent);
    }

    private void buscinessLogic() {
        mPresenter = new Presenter(this, getBaseInstence());
        BTNlogin.setOnClickListener(this);
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


    @Override
    public Context getViewContext() {
        return LoginActivity.this;
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return LoginActivity.this;
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
    public void onBackPressed() {
        onBackPressedLogic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        removeWait();
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
        return super.getFragmentSwitch();
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

}
