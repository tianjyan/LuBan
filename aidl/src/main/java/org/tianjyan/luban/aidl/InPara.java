package org.tianjyan.luban.aidl;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class InPara extends AidlEntry {
    private String key;
    private String alias;
    private List<String> values = new ArrayList<>();
    private int displayProperty;
    private String client;

    public InPara() {

    }

    public InPara(Parcel parcel){
        key = parcel.readString();
        alias = parcel.readString();
        parcel.readStringList(values);
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

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public int getDisplayProperty() {
        return displayProperty;
    }

    public void setDisplayProperty(int displayProperty) {
        this.displayProperty = displayProperty;
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
        parcel.writeStringList(values);
        parcel.writeInt(displayProperty);
    }

    public static final Parcelable.Creator<InPara> CREATOR = new Creator<InPara>(){
        public InPara createFromParcel(Parcel parcel){
            return new InPara(parcel);
        }
        public InPara[] newArray(int size){
            return new InPara[size];
        }
    };
}
