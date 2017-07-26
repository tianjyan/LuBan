package org.tianjyan.luban.aidl;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class InPara extends AidlEntry {
    private String key;
    private String alias;
    private List<String> values = new ArrayList<String>();
    private int displayProperty;
    private boolean isRegistering;
    private boolean isGlobal;

    private String client;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public InPara() {
        setFunctionId(Functions.REGISTER_IN_PARA);
    }

    public InPara(Parcel parcel){
        setFunctionId(parcel.readInt());
        key = parcel.readString();
        alias = parcel.readString();
        parcel.readStringList(values);
        displayProperty = parcel.readInt();
    }

    public void setKey(String key){
        this.key = key;
    }

    public void setAlias(String alias){
        this.alias = alias;
    }

    public void setValues(List<String> values){
        this.values = values;
    }

    public void setDisplayProperty(int displayProperty){
        this.displayProperty = displayProperty;
    }

    public void setRegistering(boolean isRegistering) {
        this.isRegistering = isRegistering;
    }

    public void setGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    public String getKey(){
        return key;
    }

    public String getAlias(){
        return alias;
    }

    public List<String> getValues(){
        return values;
    }

    public int getDisplayProperty(){
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
