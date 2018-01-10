package com.cloudsys.smashintl.fcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by AzminPurushotham on 11/27/2017 time 15 : 12.
 * Mfluid Mobile Apps Pvt Ltd
 */

public class MyFirebaseMessagingService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
