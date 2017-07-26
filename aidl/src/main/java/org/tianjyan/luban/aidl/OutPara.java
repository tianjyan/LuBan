package org.tianjyan.luban.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OutPara extends AidlEntry {
    private String key;
    private String alias;
    private String value;
    private String freezValue;
    private int displayProperty;

    private List<String> cacheHistory;

    private long time = -1;

    private boolean isRegistering;
    private boolean isGlobal;

    private boolean monitor;
    public boolean hasMonitorOnce;
    public boolean alert = false;

    private String client;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public boolean isMonitor() {
        return monitor;
    }

    public void setMonitor(boolean monitor) {
        this.monitor = monitor;
    }

    public OutPara() {
        setFunctionId(Functions.REGISTER_OUT_PARA);
        cacheHistory = new ArrayList<String>();
    }

    public OutPara(Parcel parcel) {
        setFunctionId(parcel.readInt());
        key = parcel.readString();
        alias = parcel.readString();
        value = parcel.readString();
        freezValue = "";
        displayProperty = parcel.readInt();
        cacheHistory = new ArrayList<String>();
        parcel.readStringList(cacheHistory);
        this.time = parcel.readLong();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setValue(String value) {
        if (null == value) {
            this.value = "";
        } else {
            this.value = value;
        }
    }

    public void setValue(long time, String value) {
        if (null == value) {
            this.value = "";
        } else {
            this.value = value;
        }
        this.time = time;
    }

    public void setFreezValue(String freezValue) {
        this.freezValue = freezValue;
    }

    public void setDisplayProperty(int displayProperty) {
        this.displayProperty = displayProperty;
    }

    public void setRegistering(boolean isRegistering) {
        this.isRegistering = isRegistering;
    }

    public void setGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    public void addHistory(String h) {
        cacheHistory.add(h);
    }

    public String getKey() {
        return key;
    }

    public String getAlias() {
        return alias;
    }

    public String getValue() {
        return value;
    }

    public String getFreezValue() {
        return freezValue;
    }

    public int getDisplayProperty() {
        return displayProperty;
    }

    public boolean isRegistering() {
        return isRegistering;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeString(key);
        parcel.writeString(alias);
        parcel.writeString(value);
        parcel.writeInt(displayProperty);
        parcel.writeStringList(cacheHistory);
        parcel.writeLong(time);
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