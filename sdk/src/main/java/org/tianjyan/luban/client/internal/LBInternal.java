package org.tianjyan.luban.client.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.util.Log;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.client.AbsLBParaLoader;
import org.tianjyan.luban.client.LBConnectListener;
import org.tianjyan.luban.client.communicate.ComImpl;
import org.tianjyan.luban.client.communicate.ICom;
import org.tianjyan.luban.client.internal.connect.IConnState;

public class LBInternal {
    public static final String LB_PACKAGE_NAME = "org.tianjyan.luban";
    public static final String ACTION = "org.tianjyan.luban.service";

    private static LBInternal INSTANCE = new LBInternal();
    public static LBInternal getInstance() {
        return INSTANCE;
    }

    private Context context;
    private IService lbService;
    private ICom com;

    private InParaManagerInternal inParaManager;
    private OutParaManagerInternal outParaManager;
    IConnState CONNECT_STATE_NOT_CONNECTED;
    IConnState CONNECT_STATE_CONNECTING;
    IConnState CONNECT_STATE_NOT_INSTALLED;
    IConnState CONNECT_STATE_DISCONNECTING;
    IConnState CONNECT_STATE_CONNECTED;
    IConnState currentConnState;

    private SplashHandler splashHandler;
    private LBServiceConnection lbServiceConnection;
    private LBConnectListener lbConnectListener;

    public LBInternal() {

    }

    public void initComImpl() {
        com = new ComImpl(lbService);
    }

    //region connect and disconnect
    public boolean connect(Context hostContext, AbsLBParaLoader loader) {
        if (hostContext == null) return false;
        Context app = hostContext.getApplicationContext();
        if (canConnect()) {
            setHostContext(app);
            setConnState(CONNECT_STATE_CONNECTING);

            if (isLBInstalled(app)) {
                setConnState(CONNECT_STATE_NOT_INSTALLED);
                return false;
            }

            splashHandler = new SplashHandler(app);
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
            msg.what = SplashHandler.MSG_START_CONNECT_LB;
            msg.obj = gtIntent;

            splashHandler.sendMessage(msg);
        }
        return true;
    }

    public void disconnect(Context hostContext) {
        if (canDisConnect()) {
            setConnState(CONNECT_STATE_DISCONNECTING);
            boolean result = com != null ? com.disconnect(hostContext.getPackageName()) : false;
            if(result){
                if (null != splashHandler)
                {
                    splashHandler.sendEmptyMessage(SplashHandler.MSG_START_DISCONNECT_LB);
                }
            }
        }
    }

    public boolean canConnect() {
        return currentConnState == CONNECT_STATE_NOT_CONNECTED ||
                currentConnState == CONNECT_STATE_NOT_INSTALLED;
    }

    public boolean canDisConnect() {
        return  currentConnState == CONNECT_STATE_CONNECTED;
    }
    //endregion

    //region Setter and Getter
    public IService getLbService() {
        return lbService;
    }

    public void setLbService(IService lbService) {
        this.lbService = lbService;
    }

    public Context getHostContext() {
        return context;
    }

    public void setHostContext(Context context) {
        this.context = context;
    }

    public LBConnectListener getLbConnectListener() {
        return lbConnectListener;
    }

    public void setLbConnectListener(LBConnectListener lbConnectListener) {
        this.lbConnectListener = lbConnectListener;
    }

    public void setLbServiceConnection(LBServiceConnection lbServiceConnection) {
        this.lbServiceConnection = lbServiceConnection;
    }

    public LBServiceConnection getLbServiceConnection() {
        return lbServiceConnection;
    }

    public void setConnState(IConnState state) {
        if (this.currentConnState != null) {
            this.currentConnState.finish();
        }
        Log.w("setConnState", "Pre State:" + this.currentConnState.getClass().getName());

        state.init(this.currentConnState);
        state.init(this.currentConnState, lbService);
        this.currentConnState = state;

        Log.w("setConnState", "Now State:" + this.currentConnState.getClass().getName());
    }

    public InParaManagerInternal getInParaManager()
    {
        return inParaManager;
    }

    public OutParaManagerInternal getOutParaManager()
    {
        return outParaManager;
    }
    //endregion

    //region Log
    public void logI(String tag, String msg) {
        if (currentConnState != null) {
            currentConnState.logI(tag, msg);
        }
    }

    public void logD(String tag, String msg) {
        if (currentConnState != null) {
            currentConnState.logD(tag, msg);
        }
    }

    public void logW(String tag, String msg) {
        if (currentConnState != null) {
            currentConnState.logW(tag, msg);
        }
    }

    public void logE(String tag, String msg) {
        if (currentConnState != null) {
            currentConnState.logE(tag, msg);
        }
    }
    //endregion

    private boolean isLBInstalled(Context hostContext) {
        return getLBContext(hostContext) == null ? false : true;
    }

    private static Context getLBContext(Context hostContext) {
        Context context = null;
        try {
            context = hostContext.createPackageContext(LBInternal.LB_PACKAGE_NAME, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(LBInternal.LB_PACKAGE_NAME, "LuBan is not installed");
        }
        return context;
    }
}
