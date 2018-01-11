package com.cloudsys.smashintl.fcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by AzminPurushotham on 11/27/2017 time 15 : 12.
 * Mfluid Mobile Apps Pvt Ltd
 */

public class MyFirebaseMessagingService extends Service {
    private static final String TAG = "MyFirebase";
    SharedPreferenceHelper mSharedPreferenceHelper;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        mSharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext());
        if (refreshedToken != null && !refreshedToken.equalsIgnoreCase("")) {
            mSharedPreferenceHelper.putString(getString(R.string.tocken), refreshedToken);
        }
        return null;
    }
}
