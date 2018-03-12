package com.cloudsys.smashintl.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.BaseFragment;
import com.base.log.LogUtils;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by appzoc on 22/8/15.
 */
public class AppBaseActivity extends BaseActivity implements AppBaseActionView {

    private static String TAG = "AppBaseActivity";
    private SharedPreferenceHelper sharedPreferenceHelper;
    private OnFragmentSwitchListener mFragmenntSwitchListner;
    Dialog mLoading;
    View parent;


    public interface OnFragmentSwitchListener {
        void onFragmentSwitch(Fragment fragment, boolean addToBackStack, String backStackTag, boolean replace, String screenName);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(this, getString(R.string.loading));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public AppBaseActivity getBaseInstence() {
        return AppBaseActivity.this;
    }

    public void initParentView(ViewGroup parent) {
        if (this.parent == null) {
            this.parent = parent;
        }
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
             LogUtils.v(TAG, e.getMessage());
        }
        return class_name;
    }

    public OnFragmentSwitchListener getFragmenntSwitchListner() {
        return mFragmenntSwitchListner;
    }

    public void setFragmenntSwitchListner(OnFragmentSwitchListener mFragmenntSwitchListner) {
        this.mFragmenntSwitchListner = mFragmenntSwitchListner;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment != null && fragment instanceof BaseFragment) {
            mFragmenntSwitchListner = (OnFragmentSwitchListener) fragment.getActivity();
        }
        hideKeyboard(fragment.getActivity());
    }

    public String getVisibleFragmentTag() {
        String tag = "";
        try {
            tag = getSupportFragmentManager().getBackStackEntryAt(
                    getSupportFragmentManager()
                            .getBackStackEntryCount() - 1)
                    .getName();
            LogUtils.v("fragment_tag", tag);
        } catch (Exception e) {
             LogUtils.v(TAG, e.getMessage());
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
            LogUtils.v(TAG, e.getMessage());
        }
    }

    @Override
    public void showWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.show();
    }

    @Override
    public void showWait(int message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(message));
        mLoading.show();
    }

    @Override
    public void removeWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.show();
    }

    @Override
    public void removeWait(int message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(message));
        mLoading.show();
    }


    @Override
    public void removeWait() {
        mLoading.dismiss();
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        snackbar.show();
    }

    @Override
    public void showSnackBar(int message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        snackbar.show();
    }

    @Override
    public String getStringRes(int string_id) {
        return getString(string_id);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return AppBaseActivity.this;
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
        return AppBaseActivity.this;
    }

    @Override
    public void onFinishActivity() {
        finish();
    }

    @Override
    public void showInternetAlertLogic(boolean isInternet) {

    }

    @Override
    public void showNodataAlertLogic(boolean isDataPresent) {

    }

    @Override
    public OnFragmentSwitchListener getFragmentSwitch() {
        return mFragmenntSwitchListner;
    }

    @Override
    public View getParentView() {
        return parent;
    }

}
