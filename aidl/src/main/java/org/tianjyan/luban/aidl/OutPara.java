package org.tianjyan.luban.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OutPara extends AidlEntry {
    //region Field
    private String key;
    private String alias;
    private String value;
    private String freezeValue;
    private int displayProperty;
    private List<String> cacheHistory = new ArrayList<>();
    private long time = -1;
    private boolean isRegistering;
    private boolean monitor;
    public boolean alert;
    private String client;
    //endregion

    //region Constructor
    public OutPara() {
        setFunctionId(Functions.REGISTER_OUT_PARA);
    }

    public OutPara(Parcel parcel) {
        setFunctionId(parcel.readInt());
        key = parcel.readString();
        alias = parcel.readString();
        value = parcel.readString();
        freezeValue = "";
        displayProperty = parcel.readInt();
        parcel.readStringList(cacheHistory);
        this.time = parcel.readLong();
    }
    //endregion

    //region Setter and Getter
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

    public void addHistory(String h) {
        cacheHistory.add(h);
    }

    public void setTime(long time) {
        this.time = time;
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

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
    //endregion

    //region Override
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
    //endregion
}