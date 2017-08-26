package org.tianjyan.luban.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.aidl.IService;

class LBInternal {
    static final String LB_PACKAGE_NAME = "org.tianjyan.luban.host";
    static final String ACTION = "org.tianjyan.luban.service";

    private IService service;
    private Context context;
    private SplashHandler handler;
    private LBServiceConnection connection;
    private InParaManagerInternal inParaManager;
    private OutParaManagerInternal outParaManager;
    private DataCacheController cacheController;
    IConnState CONNECT_STATE_CONNECTING;
    IConnState CONNECT_STATE_CONNECTED;
    IConnState CONNECT_STATE_DISCONNECTING;
    IConnState CONNECT_STATE_DISCONNECTED;
    IConnState currentConnState;

    private static LBInternal INSTANCE = new LBInternal();
    static LBInternal getInstance() {
        return INSTANCE;
    }

    private LBInternal() {
        cacheController = new DataCacheController();
        CONNECT_STATE_CONNECTING = new ConnectingState(cacheController);
        CONNECT_STATE_CONNECTED = new ConnectedState(cacheController);
        CONNECT_STATE_DISCONNECTING = new DisconnectingState(cacheController);
        CONNECT_STATE_DISCONNECTED = new DisconnectedState(cacheController);
        currentConnState = CONNECT_STATE_DISCONNECTED;
    }

    /**
     * 与LuBan主程序连接的入口函数。
     * 主要连接的有两个：自定义的Android Service 和 Aidl Service
     * 1. 尝试连接Android Service，并将状态置为Connecting，如果LuBan主程序不存在，则状态变为Disconnected
     * 2. 当Android Service连接上以后，尝试连接Aidl Service，此时如果失败，会开始Disconnect, 状态变为Disconnected
     * 3. 当Aidl Service连接上以后，状态变为Connected，并开始将在Connecting状态缓存的数据发送。
     * @param hostContext 宿主应用
     * @param loader 需要处理的出入参回调
     * @return 连接结果
     */
    boolean connect(Context hostContext, AbsLBParaLoader loader) {
        if (hostContext == null) return false;
        Context app = hostContext.getApplicationContext();
        if (canConnect()) {
            setConnState(CONNECT_STATE_CONNECTING);
            context = app;

            if (!isLBInstalled(context)) {
                setConnState(CONNECT_STATE_DISCONNECTED);
                return false;
            }

            handler = new SplashHandler(context);
            connection = new LBServiceConnection(handler);

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
            handler.sendMessage(msg);
        }
        return true;
    }

    /**
     * 与LuBan主程序断开连接的入口函数。
     * 断开Aidl Service和Android Service
     */
    void disconnect() {
        if (canDisConnect()) {
            setConnState(CONNECT_STATE_DISCONNECTING);
            disconnectLB();
            if (handler != null) {
                handler.sendEmptyMessage(SplashHandler.MSG_START_DISCONNECT_SERVICE);
            }
        }
    }

    int connectLB() {
        int result = canConnectLB();
        if (result == Config.RES_CODE_OK) {
            try {
                service.connectLB(context.getPackageName());
                setConnState(CONNECT_STATE_CONNECTED);
            } catch (RemoteException e) {
                Log.e("connectLB Exception", e.getMessage());
                // 如果连接Aidl Service失败则断开Android Service
                handler.sendEmptyMessage(SplashHandler.MSG_START_DISCONNECT_SERVICE);
                result = Config.RES_CODE_REFUSE;
            }
        } else {
            // 如果无法连接Aidl Service则断开Android Service
            handler.sendEmptyMessage(SplashHandler.MSG_START_DISCONNECT_SERVICE);
        }

        return result;
    }

    private int disconnectLB() {
        int result = Config.RES_CODE_NONE;
        try {
            if (service.disconnectLB(context.getPackageName())) {
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
            result = service.canConnectLB(context.getPackageName(), Config.INTERVAL_VID);
        } catch (RemoteException e) {
            Log.e("canConnectLB Exception", e.getMessage());
        }
        return result;
    }

    void setService(IService service) {
        this.service = service;
    }

    void setConnection(LBServiceConnection connection) {
        this.connection = connection;
    }

    LBServiceConnection getConnection() {
        return connection;
    }

    synchronized void setConnState(IConnState state) {
        if (this.currentConnState != null) {
            this.currentConnState.finish();
        }
        Log.w("setConnState", "Pre State:" + this.currentConnState.getClass().getName());

        state.init(service);
        this.currentConnState = state;

        Log.w("setConnState", "Now State:" + this.currentConnState.getClass().getName());
    }

    InParaManagerInternal getInParaManager() {
        return inParaManager;
    }

    OutParaManagerInternal getOutParaManager() {
        return outParaManager;
    }

    void setOutPara(String paraName, String value) {
        currentConnState.setOutPara(paraName, value);
    }

    String getInPara(String paraName) {
        return currentConnState.getInPara(paraName);
    }

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
}
