package com.cloudsys.smashintl.scheduledwork;

import com.cloudsys.smashintl.base.AppBaseAction;
import com.cloudsys.smashintl.base.AppBaseActivity;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public interface UserActions  extends AppBaseAction{

    void getScheduledWork();

    void initRecyclerView();

    void setData();

    void searchItems(String query);
}
