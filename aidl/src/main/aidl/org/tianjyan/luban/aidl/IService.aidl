package org.tianjyan.luban.aidl;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

interface IService {
    int canConnectLB(String pkgName, int versionId);
    void connectLB(String pkgName, int pid);
    boolean disconnectLB(String pkgName);

    void log(long tid, int level, String tag, String msg);
    oneway void registerInPara(in InPara inPara);
    oneway void registerOutPara(in OutPara outPara);
    void setInPara(String key, String value);
    void setOutPara(String key, String value);
}
