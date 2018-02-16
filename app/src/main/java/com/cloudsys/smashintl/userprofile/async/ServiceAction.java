package com.cloudsys.smashintl.userprofile.async;

/**
 * Created by AzminPurushotham on 11/13/2017 time 12 : 54.
 * Mfluid Mobile Apps Pvt Ltd
 */

public interface ServiceAction {
    void postUpdatePassword(String user_id, String new_password, String old_password, String tocken);
}
