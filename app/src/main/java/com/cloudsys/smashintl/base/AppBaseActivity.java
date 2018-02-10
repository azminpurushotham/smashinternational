package com.cloudsys.smashintl.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.base.BaseActivity;
import com.base.BaseFragment;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by appzoc on 22/8/15.
 */
public class AppBaseActivity extends BaseActivity{

    private SharedPreferenceHelper sharedPreferenceHelper;
    private OnFragmentSwitchListener mFragmenntSwitchListner;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public AppBaseActivity getBaseInstence() {
        return AppBaseActivity.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public SharedPreferenceHelper getSharedPreferenceHelper() {
        if (sharedPreferenceHelper == null) {
            sharedPreferenceHelper = new SharedPreferenceHelper(this);
        }
        return sharedPreferenceHelper;
    }

    public void setSharedPreferenceHelper(SharedPreferenceHelper sharedPreferenceHelper) {
        this.sharedPreferenceHelper = sharedPreferenceHelper;
    }

    /**
     * @param parent  parent view of layout
     * @param message
     * @return
     */
    public Snackbar getSnackBar(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.snack_bar_text_color));
        snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.snack_bar_text_color));
        return snackbar;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public String getFragmentClassName() {
        String class_name = "";
        try {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                class_name = "";
            } else if (count > 0) {
                class_name = getSupportFragmentManager().findFragmentById(R.id.fragment).getClass().getSimpleName();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return class_name;
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment != null && fragment instanceof BaseFragment) {
            mFragmenntSwitchListner = (OnFragmentSwitchListener) fragment.getActivity();
        }

        hideKeyboard(fragment.getActivity());

    }


    public void initLogout(Class<?> destinationClass) {
        if (sharedPreferenceHelper == null) {
            sharedPreferenceHelper = new SharedPreferenceHelper(this);
        }
        sharedPreferenceHelper.clearPreferences();
        Intent azIntent = null;
        if (destinationClass != null) {
            azIntent = new Intent(this, destinationClass);
        } else {
//            azIntent = new Intent(this, LoginActivity.class);
        }
        azIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.finish();
        startActivity(azIntent);
    }

    public String getVisibleFragmentTag() {
        String tag = "";
        try {
            tag = getSupportFragmentManager().getBackStackEntryAt(
                    getSupportFragmentManager()
                            .getBackStackEntryCount() - 1)
                    .getName();
            Log.v("fragment_tag", tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
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
            e.printStackTrace();
        }
    }

    public interface OnFragmentSwitchListener {
        void onFragmentSwitch(Fragment fragment, boolean addToBackStack, String backStackTag, boolean replace, String screenName);
    }
}
