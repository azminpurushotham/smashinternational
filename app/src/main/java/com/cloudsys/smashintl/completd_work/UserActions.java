package com.cloudsys.smashintl.completd_work;

import com.cloudsys.smashintl.base.AppBaseAction;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public interface UserActions  extends AppBaseAction{

    void getScheduledWork();

    void initRecyclerView();

    void setData();

    void searchItems(String query);
}
