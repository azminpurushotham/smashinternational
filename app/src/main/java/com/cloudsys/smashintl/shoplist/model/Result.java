
package com.cloudsys.smashintl.shoplist.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("customer_id")
    private String mCustomerId;
    @SerializedName("id")
    private String mId;
    @SerializedName("isLatLong")
    private String mIsLatLong;
    @SerializedName("name")
    private String mName;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(String customerId) {
        mCustomerId = customerId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getIsLatLong() {
        return mIsLatLong;
    }

    public void setIsLatLong(String isLatLong) {
        mIsLatLong = isLatLong;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
