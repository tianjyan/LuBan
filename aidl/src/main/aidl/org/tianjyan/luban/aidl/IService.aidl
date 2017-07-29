package org.tianjyan.luban.aidl;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

interface IService {
    int canConnectLB(String pkgName, int versionId);
    void connectLB(String pkgName);
    boolean disconnectLB(String pkgName);

    void log(long tid, int level, String tag, String msg);
    oneway void registerInPara(in InPara inPara);
    oneway void registerOutPara(in OutPara outPara);
    String getInPara(String key, String origValue);
    void setOutPara(String key, String value);
}
