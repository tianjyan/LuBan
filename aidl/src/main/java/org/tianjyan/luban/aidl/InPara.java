package org.tianjyan.luban.aidl;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class InPara extends AidlEntry {
    private String key;
    private List<String> values = new ArrayList<>();
    private String client;

    public InPara() {

    }

    public InPara(Parcel parcel){
        key = parcel.readString();
        parcel.readStringList(values);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof InPara) {
            InPara para = (InPara) obj;
            if (para.getKey() != null && para.getKey().equals(key)
                    && para.getClient() != null && para.getClient().equals(client))
                result = true;
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (key != null) {
            result = 31 * result + key.hashCode();
        }
        if (client != null) {
            result = 31 * result + client.hashCode();
        }
        return result;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(key);
        parcel.writeStringList(values);
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
