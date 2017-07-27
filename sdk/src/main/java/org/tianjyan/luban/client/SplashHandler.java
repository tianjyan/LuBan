package org.tianjyan.luban.client;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.tianjyan.luban.aidl.Config;

import java.lang.ref.WeakReference;

class SplashHandler extends Handler {
    WeakReference<Context> mContext;

    static final int MSG_START_CONNECT_SERVICE = 0x0;
    static final int MSG_START_DISCONNECT_SERVICE = 0x1;
    static final int MSG_SERVICE_CONNECTED = 0x2;
    static final int MSG_SERVICE_DISCONNECTED = 0x3;

    SplashHandler(Context context) {
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
            case MSG_START_CONNECT_SERVICE:
                Intent bindIntent = (Intent) msg.obj;
                context.bindService(bindIntent,
                        LBInternal.getInstance().getLbServiceConnection(),
                        Context.BIND_AUTO_CREATE);
                break;
            case MSG_START_DISCONNECT_SERVICE:
                context.unbindService(LBInternal.getInstance().getLbServiceConnection());
                LBInternal.getInstance().setConnState(LBInternal.getInstance().CONNECT_STATE_DISCONNECTED);
                LBInternal.getInstance().setLbService(null);
                LBInternal.getInstance().setLbServiceConnection(null);
                break;
            case MSG_SERVICE_CONNECTED:
                int result = LBInternal.getInstance().connectLB();
                handleConnectedResult(result);
                break;
            case MSG_SERVICE_DISCONNECTED:
                LBInternal.getInstance().setConnState(LBInternal.getInstance().CONNECT_STATE_DISCONNECTED);
                context.unbindService(LBInternal.getInstance().getLbServiceConnection());
                LBInternal.getInstance().setLbService(null);
                LBInternal.getInstance().setLbServiceConnection(null);
                break;
            default:
                break;
        }
    }

    private void handleConnectedResult(int result) {
        switch (result) {
            case Config.RES_CODE_REFUSE:
                // TODO: 错误
                break;
            case Config.RES_CODE_VERSION_INVALID:
                // TODO: SDK和APP版本不匹配
                break;
            default:
                break;
        }
    }
}
