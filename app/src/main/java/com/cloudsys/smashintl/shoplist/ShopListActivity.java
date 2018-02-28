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
        initParentView(parent);
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
    public Context getViewContext() {
        return ShopListActivity.this;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_try:
                showInternetAlertLogic(true);
                buscinessLogic();
                break;
        }
    }

}
