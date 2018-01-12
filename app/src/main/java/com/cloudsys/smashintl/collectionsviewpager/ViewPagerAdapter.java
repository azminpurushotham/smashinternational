package com.cloudsys.smashintl.collectionsviewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.completd_work.CompletdWorkFragment;
import com.cloudsys.smashintl.pending_work.PendingWorkFragment;

/**
 * Created by azmin on 13/1/18.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    FragmentManager fragmentManager;
    Context mContext;


    public ViewPagerAdapter(FragmentManager fragmentManager, Context mContext) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CompletdWorkFragment();

            case 1:
                return new PendingWorkFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return mContext.getString(R.string.title_pendingwoks);
        } else {
            return mContext.getString(R.string.title_completed_works);
        }
    }


    @Override
    public int getCount() {
        return 2;
    }

}

