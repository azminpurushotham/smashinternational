package com.cloudsys.smashintl.utiliti;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.base.log.LogUtils;
import com.cloudsys.smashintl.R;
import com.google.gson.JsonObject;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

/**
 * Created by azmin on 25/11/16.
 */

public class Utilities {


    public static final String API_PUBLIC_KEY = "azmin";
    private static final String TAG = "Utilities";

    public static final String REQ_FORMAT = "dd MMM yyyy";
    public static final String CURENT_FORMAT = "yyyy-MM-dd";
    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";//2017-12-20 12:00 PM

    String epocTime = generateEpochTime();
    String md5Value = generateMD5Hash(API_PUBLIC_KEY + epocTime);


    /**
     * Check for intenet connectivity
     *
     * @param context
     * @return
     */
    public static boolean isInternet(Context context) {
        ConnectivityManager zConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo zNetworkInfo = zConnectivityManager.getActiveNetworkInfo();

        return zNetworkInfo != null && zNetworkInfo.isConnectedOrConnecting();

    }

    /**
     * Hide keyboard from anywhere in the app
     *
     * @param context
     */
    public static void hideKeyboard(Activity context) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(context.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            LogUtils.v(TAG,e.getMessage());
        }
    }

    /**
     * Generate MD5 hash value for the string parameter passed
     *
     * @param key API_PUBLIC_KEY + epocTime
     * @return
     */
    public static String generateMD5Hash(String key) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(key.getBytes(), 0, key.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    /**
     * Generate Unix/Epoch time
     *
     * @return
     */
    public static String generateEpochTime() {
        String epochtime = null;

        try {
            epochtime = String.valueOf(System.currentTimeMillis() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return epochtime;
    }

    /**
     * Get Application Version Number
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {

        String versionName = null;

        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * Get Application Version Code
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {

        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    /**
     * Call this method to delete any cache created by app
     *
     * @param context context for your application
     */
    public static void clearApplicationData(Context context) {
        SharedPreferenceHelper mSharedPreferenceHelper = new SharedPreferenceHelper(context);
        mSharedPreferenceHelper.clearPreferences();
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                File f = new File(appDir, s);
                if (deleteDir(f))
                    LogUtils.i(TAG, String.format("**************** DELETED -> (%s) *******************", f.getAbsolutePath()));
            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    /**
     * Get OS Version
     *
     * @return
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Check whether email is valid
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Matcher matcher;
        Pattern pattern = Pattern.compile(emailPattern);

        matcher = pattern.matcher(email);

        if (matcher != null)
            return matcher.matches();
        else
            return false;
    }


    public static String getTodaysDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        return sdf.format(date);
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    public static String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String hourString = "";
        if (hours < 10)
            hourString = "0" + hours;
        else
            hourString = String.valueOf(hours);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hourString).append(":")
                .append(minutes).append(" ").append(timeSet).toString();

        return aTime;
    }

    public static String convertTo24hr(String _12HourTime) {
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm:a");

        Date _12HourDt = null;
        try {
            _12HourDt = _12HourSDF.parse(_12HourTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return _24HourSDF.format(_12HourDt);
    }

    public static String getFormatedDate(String parse_date, String reqFormat, String curentFormat) {
        String formattedDate = "";
        try {
            DateFormat originalFormat = new SimpleDateFormat(curentFormat, Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat(reqFormat);
            Date date = originalFormat.parse(parse_date);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LogUtils.v("date", "getFormatedDate : " + formattedDate + "parse_date : " + parse_date + "reqFormat : " + curentFormat + "reqFormat : " + curentFormat);
        return formattedDate;
    }


    public static Dialog showProgressBar(Context mContext, String message) {
        Dialog progress = new Dialog(mContext);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.progress_dialouge_asynck_task);
        TextView TVmessage = (TextView) progress.findViewById(R.id.TVmessage);
        progress.setCancelable(true);

        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return progress;
    }

    public static Dialog showNointernetConnection(Context mContext) {
        Dialog progress = new Dialog(mContext);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.dialouge_no_internet_connection);
        progress.setCancelable(false);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return progress;
    }


    public static String getNullAsEmptyString(Response<JsonObject> data) {
        String responce = "";
        try {
            if (data.body() != null) {
                responce = data.body().toString();
                LogUtils.v("responce", responce);
                return responce;
            } else {
                responce = data.errorBody().string();
                LogUtils.v("responce", " ERROR ### " + responce);
                return responce;
            }
        } catch (Exception e) {
            e.printStackTrace();
            responce = e.getMessage();
        } finally {
            return responce;
        }

    }
}
