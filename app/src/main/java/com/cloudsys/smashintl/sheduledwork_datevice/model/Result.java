
package com.cloudsys.smashintl.sheduledwork_datevice.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("amount")
    private String mAmount;
    @SerializedName("branch_name")
    private String mBranchName;
    @SerializedName("currency")
    private String mCurrency;
    @SerializedName("customer_id")
    private String mCustomerId;
    @SerializedName("date")
    private String mDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("lat")
    private String mLat;
    @SerializedName("lon")
    private String mLon;
    @SerializedName("name")
    private String mName;
    @SerializedName("status")
    private String mStatus;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(String customerId) {
        mCustomerId = customerId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLat() {
        return mLat;
    }

    public void setLat(String lat) {
        mLat = lat;
    }

    public String getLon() {
        return mLon;
    }

    public void setLon(String lon) {
        mLon = lon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
