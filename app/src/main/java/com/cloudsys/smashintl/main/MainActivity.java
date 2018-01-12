package com.cloudsys.smashintl.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.login.LoginActivity;
import com.cloudsys.smashintl.scheduledwork.ScheduledWorkFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActionView, View.OnClickListener,
        AppBaseActivity.OnFragmentSwitchListener, DrawerLayout.DrawerListener {

    private static final String TAG = "MainActivity";

    //// DEFAULT///////
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.parent)
    LinearLayout parent;
    @Nullable
    @BindView(R.id.LAYnointernet)
    ConstraintLayout LAYnointernet;
    @BindView(R.id.BTN_try)
    Button BTN_try;
    Presenter mPresenter;
    //// DEFAULT///////

    private Dialog dialougeLogout;
    boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.TVtitle)
    TextView TVtitle;
    private FragmentManager mFragmentManager;
    private ActionBarDrawerToggle mDrawerToggle;
    private OnFilterMenuClick mFilterMenuClick;


    public interface OnFilterMenuClick {
        void onFilterMenuClick();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        buscinessLogic();
    }

    private void buscinessLogic() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawer_layout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        drawer_layout.addDrawerListener(mDrawerToggle);
        drawer_layout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mPresenter = new Presenter(this, getBaseInstence());

//        onFragmentSwitch(new CompletdWorkFragment(), true, getString(R.string.tag_home), false, getString(R.string.title_home));

//        mPresenter.checkRunTimePermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    private void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        nav_view.setNavigationItemSelectedListener(this);
        nav_view.bringToFront();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem action_search = menu.findItem(R.id.action_search);
        MenuItem action_notification = menu.findItem(R.id.action_notification);

        String tag = getVisibleFragmentTag();
        Log.v("tag", " onPrepareOptionsMenu " + tag);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case R.id.action_notification:
                mFilterMenuClick.onFilterMenuClick();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLogoutDialouge() {
        showLogOutDialouge();
    }

    @Override
    public void onstartLogin() {
        startActivity(new Intent(LoginActivity.getStartIntent(MainActivity.this)));
        finish();
    }


    private void showLogOutDialouge() {
        if (dialougeLogout == null) {
            dialougeLogout = getLogoutDialouge();
        }
        if (dialougeLogout.isShowing() == false) {
            dialougeLogout.show();
        }
    }

    private Dialog getLogoutDialouge() {
        Dialog mDialog = null;
        Button BTNclose, BTNok;
        TextView TVtitle, TVmessage;

        mDialog = new Dialog(MainActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialouge_message_with_positive_and_negative_button);

        TVtitle = (TextView) mDialog.findViewById(R.id.TVtitle);
        TVmessage = (TextView) mDialog.findViewById(R.id.TVmessage);
        BTNclose = (Button) mDialog.findViewById(R.id.BTNclose);
        BTNok = (Button) mDialog.findViewById(R.id.BTNok);

        BTNclose.setText(getString(R.string.cancel));
        BTNok.setText(getString(R.string.ok));
        TVtitle.setText(getString(R.string.logout));
        TVmessage.setText(getString(R.string.do_you_want_logout));

        BTNclose.setOnClickListener(this);
        BTNok.setOnClickListener(this);

        mDialog.setCancelable(true);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return mDialog;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.v(TAG, item.getItemId() + "");
        switch (item.getItemId()) {
            case R.id.ic_logout:
                mPresenter.showLogoutDialouge();
                break;
            case R.id.ic_collection:
                mPresenter.showLogoutDialouge();
                break;
            case R.id.ic_about_smash:
                mPresenter.showLogoutDialouge();
                break;
            case R.id.ic_new_lead:
                mPresenter.showLogoutDialouge();
                break;
            case R.id.ic_sheduled_work:
                onFragmentSwitch(new ScheduledWorkFragment(),
                        true,
                        getString(R.string.tag_sheduled_work),
                        true,
                        getString(R.string.title_sheduled_work));
                break;
            case R.id.ic_update_customer_location:
                onFragmentSwitch(new ScheduledWorkFragment(),
                        true,
                        getString(R.string.tag_sheduled_work),
                        true,
                        getString(R.string.title_sheduled_work));
                break;
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public void setImage(String image) {

    }

    @Override
    public String getOtp() {
        return null;
    }

    @Override
    public String getUserId() {
        return getSharedPreferenceHelper().getString(getString(R.string.user_id), null);
    }

    @Override
    public void dismissLogOut() {
        if (dialougeLogout.isShowing()) {
            dialougeLogout.dismiss();
        }
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    /////////////DEFAULTS///////////////////////
    @Override
    public void onBackPressed() {
        onBackPressedLogic();
    }

    private void onBackPressedLogic() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            if (getVisibleFragmentTag().equalsIgnoreCase(getString(R.string.tag_home))) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    finish();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                mPresenter.showSnackBar(parent, "Please click BACK again to exit !.");

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                getSupportFragmentManager().popBackStack();
                invalidateMenuWithDelay();
            }
        }
    }

    private void invalidateMenuWithDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        }, 500);
    }

    @Override
    public Context getViewContext() {
        return MainActivity.this;
    }

    @Override
    public LinearLayout getParentView() {
        return parent;
    }

    @OnClick(R.id.BTN_try)
    void onConnectionRetry(View v) {
        mPresenter.showNoInternetConnectionLayout(true);
        buscinessLogic();
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return MainActivity.this;
    }

    @Override
    public void showWait(Dialog mLoading) {
        mLoading.show();
    }

    @Override
    public void removeWait(Dialog mLoading) {
        mLoading.dismiss();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        getSnackBar(parent, appErrorMessage).show();
    }

    @Override
    public void showInternetAlertLogic(boolean isInternet) {
        if (isInternet == false) {
            LAYnointernet.setVisibility(View.VISIBLE);
        } else {
            parent.setVisibility(View.VISIBLE);
            LAYnointernet.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSnackBar(Snackbar snackBar) {
        snackBar.show();
    }

    @Override
    public void onFinishActivity() {
        finish();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.BTNclose:
                mPresenter.dismissLogOut();
                break;

            case R.id.BTNok:
                mPresenter.logOut();
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onFragmentSwitch(Fragment mFragment, boolean isAddToBackStack, String backStackTag, boolean isReplace, String screenName) {
        String class_name = "";
        mFragmentManager = getSupportFragmentManager();
        class_name = getFragmentClassName();
        Log.v("tag", " class_name " + class_name);
        Log.v("tag", " mFragment.getClass().getSimpleName() " + mFragment.getClass().getSimpleName());
        if (!mFragment.getClass().getSimpleName().equalsIgnoreCase(class_name)) {
            if (mFragmentManager == null) {
                mFragmentManager = getSupportFragmentManager();
            } else {
                if (isReplace) {
                    if (isAddToBackStack) {
                        mFragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit)
                                .replace(R.id.fragment, mFragment, screenName).addToBackStack(backStackTag).commit();
                    } else {
                        mFragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit)
                                .replace(R.id.fragment, mFragment, screenName).commit();
                    }
                } else {
                    if (isAddToBackStack) {
                        mFragmentManager.beginTransaction().setCustomAnimations(R.anim.pulse, R.anim.pop_exit)
                                .add(R.id.fragment, mFragment, screenName).addToBackStack(backStackTag).commit();
                    } else {
                        mFragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit)
                                .add(R.id.fragment, mFragment, screenName).commit();
                    }
                }

                Log.v("tag", mFragment.getClass().getSimpleName());
                TVtitle.setText(screenName);
                Log.v("tag", screenName);
                invalidateMenuWithDelay();
            }
        }
    }


    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
/////////////DEFAULTS///////////////////////
}
