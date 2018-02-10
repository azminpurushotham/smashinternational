package com.cloudsys.smashintl.pending_work;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {

    RelativeLayout getParentView();

    RecyclerView getRecyclerView();

    LinearLayoutManager getLinearLayoutManager();

}
