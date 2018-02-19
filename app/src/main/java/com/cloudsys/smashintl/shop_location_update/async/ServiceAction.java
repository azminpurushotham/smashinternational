package com.cloudsys.smashintl.shop_location_update.async;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 54.
 */

public interface ServiceAction {
    void getJson(String userId, String token, String id);

    void updateShopLocation(String user_id,
                            String token,
                            String customer_id,
                            String address_1,
                            String address_2,
                            String latitude,
                            String longitude);
}
