package com.cloudsys.smashintl.base;


import com.base.BaseApplication;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.FirebaseApp;

/**
 * Created by azmin on 12/1/17.
 */

public class AppBaseApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }

}
