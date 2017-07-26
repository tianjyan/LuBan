package org.tianjyan.luban.aidl;

import android.os.Parcel;

public abstract class AidlEntry implements AidlTask {

    public static final int DISPLAY_NORMAL = 0;
    public static final int DISPLAY_AC = 1;
    public static final int DISPLAY_DISABLE = 2;
    public static final int DISPLAY_TITLE = 3;

    int functionId;

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(functionId);
    }

    public void readFromParcel(Parcel parcel) {
        functionId = parcel.readInt();
    }
}