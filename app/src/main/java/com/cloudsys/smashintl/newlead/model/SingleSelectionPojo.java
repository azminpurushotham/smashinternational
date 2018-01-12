package com.cloudsys.smashintl.newlead.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Azmin Purushotham on 12/1/2017.
 */

public class SingleSelectionPojo implements Parcelable{
    int position;
    ServicesPojo mPojo;

    protected SingleSelectionPojo(Parcel in) {
        position = in.readInt();
    }

    public static final Creator<SingleSelectionPojo> CREATOR = new Creator<SingleSelectionPojo>() {
        @Override
        public SingleSelectionPojo createFromParcel(Parcel in) {
            return new SingleSelectionPojo(in);
        }

        @Override
        public SingleSelectionPojo[] newArray(int size) {
            return new SingleSelectionPojo[size];
        }
    };

    public SingleSelectionPojo() {

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ServicesPojo getmPojo() {
        return mPojo;
    }

    public void setmPojo(ServicesPojo mPojo) {
        this.mPojo = mPojo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
    }
}
