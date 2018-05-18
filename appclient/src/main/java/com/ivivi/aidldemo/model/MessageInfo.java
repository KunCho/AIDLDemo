package com.ivivi.aidldemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhaokun on 2018/5/18.
 */

public class MessageInfo implements Parcelable {
    private String message;
    private int num;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
    }

    public MessageInfo() {
    }

    protected MessageInfo(Parcel in) {
        this.message = in.readString();
        this.num = in.readInt();
    }

    public static final Parcelable.Creator<MessageInfo> CREATOR = new Parcelable.Creator<MessageInfo>() {
        @Override
        public MessageInfo createFromParcel(Parcel source) {
            return new MessageInfo(source);
        }

        @Override
        public MessageInfo[] newArray(int size) {
            return new MessageInfo[size];
        }
    };

    public void readFromParcel(Parcel in) {
        message = in.readString();
        num = in.readInt();
    }
}
