package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

/**
 * 管理Aidl Service 连接状态的接口
 */
interface IConnState {
    void init(IService service);
    void finish();
    void logI(String tag, String msg);
    void logD(String tag, String msg);
    void logW(String tag, String msg);
    void logE(String tag, String msg);

    void registerInParas(InPara[] inParas);
    void registerOutParas(OutPara[] outParas);
    void setOutPara(String paraName, String value);
    String getInPara(String paraName);
}
