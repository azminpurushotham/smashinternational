package com.cloudsys.smashintl.newlead.location_service;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Azmin Purushotham on 12/7/2017.
 */

public class LocationUtilities {

    static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_locaction_updates";
    private static Location mLastLocation;

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The {@link Context}.
     */
    static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }

    /**
     * Stores the location updates state in SharedPreferences.
     *
     * @param requestingLocationUpdates The location updates state.
     */
    static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }

    /**
     * Returns the {@code location} object as a human readable string.
     *
     * @param location The {@link Location}.
     */
    static String getLocationText(Location location) {
        return location == null ? "Unknown location" :
                "(" + location.getLatitude() + ", " + location.getLongitude() + ")";
    }

    static String getLocationTitle(Context context) {
//        return context.getString(R.string.location_updated,
//                DateFormat.getDateTimeInstance().format(new Date()));
        return context.getString(R.string.location_updated);
    }

    @SuppressWarnings("MissingPermission")
    public static Location getLastLocation(FusedLocationProviderClient mFusedLocationClient, AppBaseActivity context) {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(context, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                        } else {
                            Log.w("LocationUtilities", "getLastLocation:exception", task.getException());
                        }
                    }
                });

        return mLastLocation;
    }
}