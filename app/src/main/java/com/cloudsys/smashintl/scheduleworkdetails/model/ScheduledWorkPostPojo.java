
package com.cloudsys.smashintl.scheduleworkdetails.model;

import com.cloudsys.smashintl.completd_work.model.Result;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ScheduledWorkPostPojo {

    @SerializedName("error_code")
    private String mErrorCode;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private List<com.cloudsys.smashintl.completd_work.model.Result> mResult;
    @SerializedName("status")
    private Boolean mStatus;

    public String getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(String errorCode) {
        mErrorCode = errorCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<com.cloudsys.smashintl.completd_work.model.Result> getResult() {
        return mResult;
    }

    public void setResult(List<Result> result) {
        mResult = result;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
