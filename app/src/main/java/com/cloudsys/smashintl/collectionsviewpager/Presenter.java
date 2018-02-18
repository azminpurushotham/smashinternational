package com.cloudsys.smashintl.collectionsviewpager;

import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public class Presenter extends AppBasePresenter implements UserActions {
    ActionView mView;
    private ViewPagerAdapter adapter;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
    }

    public Presenter(ActionView mView, AppBaseFragment baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
    }


    @Override
    public void setData() {
        adapter = new ViewPagerAdapter(mView.getBaseFragment().getFragmentManager(), mView.getViewContext());
        mView.getViewPager().setAdapter(adapter);
        mView.getTabLayOut().setupWithViewPager(mView.getViewPager());
    }

    @Override
    public void permissionGranded(String permission) {

    }

    @Override
    public void permissionDenaid(String permission) {

    }

    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {

    }
}
