
package com.cloudsys.smashintl.completd_work.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Item {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("amount")
    private String mAmount;
    @SerializedName("bill_id")
    private String mBillId;
    @SerializedName("currency")
    private String mCurrency;
    @SerializedName("customer_id")
    private String mCustomerId;
    @SerializedName("date")
    private String mDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("lat")
    private Object mLat;
    @SerializedName("lon")
    private Object mLon;
    @SerializedName("name")
    private String mName;
    @SerializedName("pending_amount")
    private Object mPendingAmount;
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

    public String getBillId() {
        return mBillId;
    }

    public void setBillId(String billId) {
        mBillId = billId;
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

    public Object getLat() {
        return mLat;
    }

    public void setLat(Object lat) {
        mLat = lat;
    }

    public Object getLon() {
        return mLon;
    }

    public void setLon(Object lon) {
        mLon = lon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Object getPendingAmount() {
        return mPendingAmount;
    }

    public void setPendingAmount(Object pendingAmount) {
        mPendingAmount = pendingAmount;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
