package com.cloudsys.smashintl.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.base.BaseFragment;
import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;


/**
 * Created by appzoc on 22/8/15.
 */
public class AppBaseFragment extends BaseFragment implements AppBaseActionView {


    private SharedPreferenceHelper sharedPreferenceHelper;
    public AppBaseActivity.OnFragmentSwitchListener onFragmentSwitchListener;
    AppBasePresenter mPresenter;

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
        mPresenter = new AppBasePresenter(this, getActivity());
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment != null) {
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
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.snack_bar_text_color));
        snackbar.setActionTextColor(ContextCompat.getColor(getActivity(), R.color.snack_bar_text_color));
        return snackbar;
    }
}
