package com.cloudsys.smashintl.scheduledwork_datevice;

import com.cloudsys.smashintl.base.AppBaseAction;


/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 */

public interface UserActions  extends AppBaseAction{

    void getScheduledWorksDateVice(String date);

    void initRecyclerView();

    void setData();

    void searchItems(String query);
}
