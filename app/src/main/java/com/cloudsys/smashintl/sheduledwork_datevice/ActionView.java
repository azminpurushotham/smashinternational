package com.cloudsys.smashintl.sheduledwork_datevice;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    RecyclerView getRecyclerView();

    LinearLayoutManager getLinearLayoutManager();

}
