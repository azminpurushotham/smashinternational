package com.cloudsys.smashintl.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.base.BaseFragment;
import com.cloudsys.smashintl.BuildConfig;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;


/**
 * Created by azmin purushotham on 22/8/15.
 */
public class AppBaseFragment extends BaseFragment implements AppBaseActionView {


    private SharedPreferenceHelper sharedPreferenceHelper;
    public AppBaseActivity.OnFragmentSwitchListener onFragmentSwitchListener;
    Dialog mLoading;
    View parent;

    public AppBaseActivity.OnFragmentSwitchListener getFragmentSwitchListener() {
        return onFragmentSwitchListener;
    }

    public void setFragmentSwitchListener(AppBaseActivity.OnFragmentSwitchListener azFragmentSwitchListener) {
        this.onFragmentSwitchListener = azFragmentSwitchListener;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(getActivity(), getString(R.string.loading));
        }
        return null;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment != null && fragment.getClass().toString().contains(BuildConfig.APPLICATION_ID)) {
            onFragmentSwitchListener = (AppBaseActivity.OnFragmentSwitchListener) this;
        }

        hideKeyboard(fragment.getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context != null) {
            if (context instanceof AppBaseActivity) {
                try {
                    onFragmentSwitchListener = (AppBaseActivity.OnFragmentSwitchListener) context;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }


    public SharedPreferenceHelper getSharedPreferenceHelper() {
        if (sharedPreferenceHelper == null) {
            sharedPreferenceHelper = new SharedPreferenceHelper(getActivity());
        }
        return sharedPreferenceHelper;
    }

    public void setSharedPreferenceHelper(SharedPreferenceHelper sharedPreferenceHelper) {
        this.sharedPreferenceHelper = sharedPreferenceHelper;
    }

    public void initLogout(Class<?> destinationClass) {
        if (sharedPreferenceHelper == null) {
            sharedPreferenceHelper = new SharedPreferenceHelper(getActivity());
        }
        sharedPreferenceHelper.clearPreferences();
        Intent azIntent = null;
        if (destinationClass != null) {
            azIntent = new Intent(getActivity(), destinationClass);
        } else {
//            azIntent = new Intent(getActivity(), LoginActivity.class);
        }
        azIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getActivity().finish();
        startActivity(azIntent);
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

    public void initParentView(ViewGroup parent) {
        if (this.parent == null) {
            this.parent = parent;
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
        return getActivity();
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return (AppBaseActivity) getActivity();
    }

    @Override
    public AppBaseFragment getViewFragment() {
        return this;
    }

    @Override
    public AppBaseFragment getBaseFragment() {
        return AppBaseFragment.this;
    }

    @Override
    public AppBaseActivity getBaseActivity() {
        return (AppBaseActivity) getActivity();
    }

    @Override
    public void onFinishActivity() {

    }

    @Override
    public void showInternetAlertLogic(boolean isInternet) {

    }

    @Override
    public void showNodataAlertLogic(boolean isDataPresent) {

    }

    @Override
    public AppBaseActivity.OnFragmentSwitchListener getFragmentSwitch() {
        return onFragmentSwitchListener;
    }

    @Override
    public View getParentView() {
        return parent;
    }

}
