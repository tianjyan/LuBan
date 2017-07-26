package org.tianjyan.luban.client.internal.connect;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

public interface IConnState {
    void init(IConnState lastState);
    void init(IConnState lastState, IService lbService);
    void finish();
    void logI(String tag, String msg);
    void logD(String tag, String msg);
    void logW(String tag, String msg);
    void logE(String tag, String msg);

    void registerInParas(InPara[] inParas);
    void registerOutParas(OutPara[] outParas);
}
