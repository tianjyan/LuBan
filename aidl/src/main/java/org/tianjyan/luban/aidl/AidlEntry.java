package org.tianjyan.luban.aidl;

import android.os.Parcel;

public abstract class AidlEntry implements AidlTask {
    //region Field
    public static final int DISPLAY_NORMAL = 0;
    public static final int DISPLAY_FLOATING = 1;
    public static final int DISPLAY_DISABLE = 2;
    int functionId;
    //endregion

    //region Setter and Getter
    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }
    //endregion

    //region Override
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
    //endregion
}