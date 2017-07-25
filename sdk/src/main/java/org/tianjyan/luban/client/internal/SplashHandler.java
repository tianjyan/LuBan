package org.tianjyan.luban.client.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class SplashHandler extends Handler {
    WeakReference<Context> mContext;

    public static final int MSG_START_CONNECT_LB = 0x0;
    public static final int MSG_START_DISCONNECT_LB = 0x1;
    public static final int MSG_LB_SERVICE_CONNECTED = 0x2;
    public static final int MSG_LB_SERVICE_DISCONNECTED = 0x3;

    public SplashHandler(Context context) {
        super(Looper.getMainLooper());
        mContext = new WeakReference<>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        if (mContext == null) {
            return;
        }
        Context context = mContext.get();
        if (mContext == null) {
            return;
        }

        switch (msg.what) {
            case MSG_START_CONNECT_LB:
                // TODO: 连接LuBan
                break;
            case MSG_START_DISCONNECT_LB:
                // TODO: 断开LuBan
                break;
            case MSG_LB_SERVICE_CONNECTED:
                break;
            case MSG_LB_SERVICE_DISCONNECTED:
                break;
        }
    }
}
