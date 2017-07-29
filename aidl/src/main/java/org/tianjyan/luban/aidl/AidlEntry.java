package org.tianjyan.luban.aidl;

public abstract class AidlEntry implements AidlTask {
    public static final int DISPLAY_NORMAL = 0;
    public static final int DISPLAY_FLOATING = 1;
    public static final int DISPLAY_DISABLE = 2;

    @Override
    public int describeContents() {
        return 0;
    }
}