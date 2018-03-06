package com.cloudsys.smashintl.completd_work;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.cloudsys.smashintl.base.AppBaseActionView;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 55.
 */

public interface ActionView extends AppBaseActionView {
    RecyclerView getRecyclerView();

    LinearLayoutManager getLinearLayoutManager();

    void setPendingAmount(String value);

    void setCompletedAmount(String value);

    void setTotalAmount(String value);

    void setCurrency(String currency);

    String getCurrency();
}
