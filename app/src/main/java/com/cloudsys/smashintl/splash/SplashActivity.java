package com.cloudsys.smashintl.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.login.LoginActivity;
import com.cloudsys.smashintl.main.MainActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;


public class SplashActivity extends AppBaseActivity {

    private String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        buscinessLoagic();
    }

    private void buscinessLoagic() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.v("user_id", getSharedPreferenceHelper().getString(getString(R.string.user_id), ""));
                if (getSharedPreferenceHelper().getString(getString(R.string.user_id), null) == null) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 3000);
    }
}
