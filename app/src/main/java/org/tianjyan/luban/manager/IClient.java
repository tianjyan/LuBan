package org.tianjyan.luban.manager;

import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

import java.util.List;

public interface IClient {
    String getPackageName();
    void clear();
    void registerInPara(InPara inPara);
    void unregisterInPara(String paraName);
    void registerOutPara(OutPara outPara);
    void unregisterOutPara(String paraName);
    void isInParaEmpty();
    List<InPara> getInPara();
    InPara getInPara(String paraName);
    String getInPara(String paraName, String origVal);
    void isOutParaEmpty();
    OutPara getOutPara(String paraName);
    List<OutPara> getOutPara();
    void setOutPara(String paraName, String value);
    void setOutParaMonitor(String paraName, boolean flag);
}
