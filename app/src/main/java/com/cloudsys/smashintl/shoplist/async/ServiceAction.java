package com.cloudsys.smashintl.shoplist.async;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 54.
 */

public interface ServiceAction {
    void getJson(String user_id, String tocken);

    void getSearchScheduledWorks(String user_id, String tocken, String work_type, String query);
}
