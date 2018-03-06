
package com.cloudsys.smashintl.completd_work.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("completed_amount")
    private String mCompletedAmount;
    @SerializedName("currency")
    private String mCurrency;
    @SerializedName("list")
    private java.util.List<Item> mList;
    @SerializedName("pending_amount")
    private String mPendingAmount;
    @SerializedName("total_amount")
    private String mTotalAmount;

    public String getCompletedAmount() {
        return mCompletedAmount;
    }

    public void setCompletedAmount(String completedAmount) {
        mCompletedAmount = completedAmount;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public java.util.List<Item> getList() {
        return mList;
    }

    public void setList(java.util.List<Item> list) {
        mList = list;
    }

    public String getPendingAmount() {
        return mPendingAmount;
    }

    public void setPendingAmount(String pendingAmount) {
        mPendingAmount = pendingAmount;
    }

    public String getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        mTotalAmount = totalAmount;
    }

}
