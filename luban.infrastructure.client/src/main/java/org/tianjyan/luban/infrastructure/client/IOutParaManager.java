package org.tianjyan.luban.infrastructure.client;

import org.tianjyan.luban.aidl.OutPara;

import java.util.List;

interface IOutParaManager {
    void register(OutPara outPara);
    void unregister(String paraName);
    void clear();
    boolean isEmpty();
    List<OutPara> getAll();
    OutPara getOutPara(String paraName);
    void setOutPara(String paraName, String value);
}
