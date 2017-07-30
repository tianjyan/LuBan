package org.tianjyan.luban.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OutPara extends AidlEntry {
    private String key;
    private String alias;
    private String value;
    private String freezeValue;
    private int displayProperty;
    private boolean isRegistering;
    private boolean monitor;
    private String client;

    public OutPara() {

    }

    public OutPara(Parcel parcel) {
        key = parcel.readString();
        alias = parcel.readString();
        value = parcel.readString();
        freezeValue = "";
        displayProperty = parcel.readInt();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (null == value) {
            this.value = "";
        } else {
            this.value = value;
        }
    }

    public String getFreezeValue() {
        return freezeValue;
    }

    public void setFreezeValue(String freezeValue) {
        this.freezeValue = freezeValue;
    }

    public int getDisplayProperty() {
        return displayProperty;
    }

    public void setDisplayProperty(int displayProperty) {
        this.displayProperty = displayProperty;
    }

    public boolean isRegistering() {
        return isRegistering;
    }

    public void setRegistering(boolean isRegistering) {
        this.isRegistering = isRegistering;
    }

    public boolean isMonitor() {
        return monitor;
    }

    public void setMonitor(boolean monitor) {
        this.monitor = monitor;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(key);
        parcel.writeString(alias);
        parcel.writeString(value);
        parcel.writeInt(displayProperty);
    }

    public static final Parcelable.Creator<OutPara> CREATOR = new Creator<OutPara>() {
        public OutPara createFromParcel(Parcel source) {
            return new OutPara(source);
        }

        public OutPara[] newArray(int size) {
            return new OutPara[size];
        }
    };
}