package com.cloudsys.smashintl.about_status;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.login.LoginActivity;
import com.cloudsys.smashintl.main.MainActivity;


public class AboutActivity extends AppBaseActivity {

    private String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        buscinessLoagic();
    }

    private void buscinessLoagic() {

    }
}
