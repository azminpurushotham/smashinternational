package com.cloudsys.smashintl.collectionsviewpager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    ViewPager getViewPager();

    TabLayout getTabLayOut();
}
