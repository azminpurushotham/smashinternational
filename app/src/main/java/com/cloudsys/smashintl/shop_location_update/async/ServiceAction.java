package com.cloudsys.smashintl.shop_location_update.async;

import com.cloudsys.smashintl.shop_location_update.model.scheduleWorkPojo;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 54.
 */

public interface ServiceAction {
    void getJson(String userId, String token, String id);

    void postUpdateWorkStatus(scheduleWorkPojo data);
}
