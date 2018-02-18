package com.cloudsys.smashintl.shoplist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.main.MainActivity;
import com.cloudsys.smashintl.utiliti.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by User on 11/30/2017.
 */

public class ShopListActivity extends AppBaseActivity implements ActionView, View.OnClickListener, MainActivity.SearchQueryScheduledWork {

    private static final String TAG = "ShopListActivity";
    //// DEFAULT///////
    @BindView(R.id.parent)
    RelativeLayout parent;
    @Nullable
    @BindView(R.id.LAYnointernet)
    ConstraintLayout LAYnointernet;
    @BindView(R.id.BTN_try)
    Button BTN_try;
    Presenter mPresenter;
    Dialog mLoading;
    @BindView(R.id.LAYnodata)
    LinearLayout LAYnodatal;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.TVtitle)
    TextView TVtitle;
    //// DEFAULT///////

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        buscinessLogic();
    }

    private void init() {
        setContentView(R.layout.activity_shoplist);
        ButterKnife.bind(this);
    }


    private void buscinessLogic() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.menu_arrow_back_24dp);
        TVtitle.setText(R.string.shops);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPresenter = new Presenter(this, getBaseInstence());
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(ShopListActivity.this, ShopListActivity.this.getString(R.string.loading));
        }
        mPresenter.initRecyclerView();
        mPresenter.getShopList();
        BTN_try.setOnClickListener(this);
    }


    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(ShopListActivity.this);
    }

    @Override
    public void searchQueryScheduledWork(String query) {
        mPresenter.searchItems(query);
    }


    public AppBaseActivity getBaseInstence() {
        return ShopListActivity.this;
    }

    @Override
    public void showWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.show();
    }

    @Override
    public void showWait(int string_id) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(string_id));
        mLoading.show();
    }

    @Override
    public void removeWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.dismiss();
    }

    @Override
    public void removeWait(int message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(message));
        mLoading.dismiss();
    }

    @Override
    public void removeWait() {
        mLoading.dismiss();
    }

    @Override
    public Context getViewContext() {
        return ShopListActivity.this;
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return (AppBaseActivity) ShopListActivity.this;
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
        return (AppBaseActivity) ShopListActivity.this;
    }

    @Override
    public RelativeLayout getParentView() {
        return parent;
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
    public void showNodataAlertLogic(boolean isDataPresent) {

    }

    @Override
    public AppBaseActivity.OnFragmentSwitchListener getFragmentSwitch() {
        return null;
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
    public void onFinishActivity() {
        ShopListActivity.this.finish();
    }


    @Override
    public String getStringRes(int string_id) {
        return getString(string_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_try:
                showInternetAlertLogic(true);
                buscinessLogic();
                break;
        }
    }

}
