package org.tianjyan.luban.client;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.client.Connect.IConnState;

class LBInternal {
    //region Field
    static final String LB_PACKAGE_NAME = "org.tianjyan.luban";
    static final String ACTION = "org.tianjyan.luban.service";

    private IService lbService;
    private Context context;
    private SplashHandler splashHandler;
    private LBServiceConnection lbServiceConnection;
    private InParaManagerInternal inParaManager;
    private OutParaManagerInternal outParaManager;
    IConnState CONNECT_STATE_CONNECTING;
    IConnState CONNECT_STATE_CONNECTED;
    IConnState CONNECT_STATE_DISCONNECTING;
    IConnState CONNECT_STATE_DISCONNECTED;
    IConnState currentConnState;
    //endregion

    //region Singleton
    private static LBInternal INSTANCE = new LBInternal();
    static LBInternal getInstance() {
        return INSTANCE;
    }
    //endregion

    //region Internal Method

    //region Connect and Disconnect Binder Service and Aidl Service
    boolean connect(Context hostContext, AbsLBParaLoader loader) {
        if (hostContext == null) return false;
        Context app = hostContext.getApplicationContext();
        if (canConnect()) {
            setConnState(CONNECT_STATE_CONNECTING);
            context = app;

            if (isLBInstalled(context)) {
                setConnState(CONNECT_STATE_DISCONNECTED);
                return false;
            }

            splashHandler = new SplashHandler(context);
            lbServiceConnection = new LBServiceConnection(splashHandler);

            outParaManager = new OutParaManagerInternal();
            inParaManager = new InParaManagerInternal();
            loader.loadOutParas(outParaManager.getUserInterface());
            loader.loadInParas(inParaManager.getUserInterface());
            currentConnState.registerInParas(inParaManager.getAndClearTempParas());
            currentConnState.registerOutParas(outParaManager.getAndClearTempParas());

            Intent gtIntent = new Intent();
            gtIntent.setAction(ACTION);
            gtIntent.setPackage(LB_PACKAGE_NAME);
            Message msg = Message.obtain();
            msg.what = SplashHandler.MSG_START_CONNECT_SERVICE;
            msg.obj = gtIntent;
            splashHandler.sendMessage(msg);
        }
        return true;
    }

    void disconnect(Context hostContext) {
        if (canDisConnect()) {
            setConnState(CONNECT_STATE_DISCONNECTING);
            boolean isSuccess = disconnectLB() == Config.RES_CODE_OK;
            if (isSuccess && splashHandler != null) {
                splashHandler.sendEmptyMessage(SplashHandler.MSG_START_DISCONNECT_SERVICE);
            }
        }
    }

    int connectLB() {
        int result = canConnectLB();
        if (result == Config.RES_CODE_OK) {
            try {
                lbService.connectLB(context.getPackageName(), android.os.Process.myPid());
                setConnState(CONNECT_STATE_CONNECTED);
            } catch (RemoteException e) {
                Log.e("connectLB Exception", e.getMessage());
                result = Config.RES_CODE_REFUSE;
            }
        }
        return result;
    }

    private int disconnectLB() {
        int result = Config.RES_CODE_NONE;
        try {
            if (lbService.disconnectLB(context.getPackageName())) {
                result = Config.RES_CODE_OK;
            } else {
                result = Config.RES_CODE_REFUSE;
            }
        } catch (RemoteException e) {
            Log.e("disconnectLB Exception", e.getMessage());
            result = Config.RES_CODE_REFUSE;
        }
        return result;
    }

    private boolean canConnect() {
        return currentConnState == CONNECT_STATE_DISCONNECTED;
    }

    private boolean canDisConnect() {
        return  currentConnState == CONNECT_STATE_CONNECTED;
    }

    private int canConnectLB() {
        int result = Config.RES_CODE_NONE;
        try {
            result = lbService.canConnectLB(context.getPackageName(), Config.INTERVAL_VID);
        } catch (RemoteException e) {
            Log.e("canConnectLB Exception", e.getMessage());
        }
        return result;
    }

    void setLbService(IService lbService) {
        this.lbService = lbService;
    }

    void setLbServiceConnection(LBServiceConnection lbServiceConnection) {
        this.lbServiceConnection = lbServiceConnection;
    }

    LBServiceConnection getLbServiceConnection() {
        return lbServiceConnection;
    }

    synchronized void setConnState(IConnState state) {
        if (this.currentConnState != null) {
            this.currentConnState.finish();
        }
        Log.w("setConnState", "Pre State:" + this.currentConnState.getClass().getName());

        state.init(this.currentConnState);
        state.init(this.currentConnState, lbService);
        this.currentConnState = state;

        Log.w("setConnState", "Now State:" + this.currentConnState.getClass().getName());
    }
    //endregion

    //region OutParaCache and InPara
    InParaManagerInternal getInParaManager() {
        return inParaManager;
    }

    OutParaManagerInternal getOutParaManager() {
        return outParaManager;
    }
    //endregion

    //region Log
    void logI(String tag, String msg) {
        if (currentConnState != null) {
            currentConnState.logI(tag, msg);
        }
    }

    void logD(String tag, String msg) {
        if (currentConnState != null) {
            currentConnState.logD(tag, msg);
        }
    }

    void logW(String tag, String msg) {
        if (currentConnState != null) {
            currentConnState.logW(tag, msg);
        }
    }

    void logE(String tag, String msg) {
        if (currentConnState != null) {
            currentConnState.logE(tag, msg);
        }
    }
    //endregion

    //region Misc.
    private boolean isLBInstalled(Context hostContext) {
        if (getLBContext(hostContext) == null) {
            return false;
        }
        return true;
    }

    private static Context getLBContext(Context hostContext) {
        Context context = null;
        try {
            context = hostContext.createPackageContext(LBInternal.LB_PACKAGE_NAME,
                    Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(LBInternal.LB_PACKAGE_NAME, "LuBan is not installed");
        }
        return context;
    }
    //endregion

    //endregion
}
