package com.cloudsys.smashintl.collectionsviewpager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cloudsys.smashintl.base.AppBaseActionView;
import com.cloudsys.smashintl.base.AppBaseFragment;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    /////////////DEFAULTS///////////////////////

    Context getViewContext();

    AppBaseFragment getAppBaseFragment();

    void onFinishActivity();

    String getStringRes(int string_id);

    /////////////DEFAULTS///////////////////////
    ViewPager getViewPager();

    TabLayout getTabLayOut();
}
