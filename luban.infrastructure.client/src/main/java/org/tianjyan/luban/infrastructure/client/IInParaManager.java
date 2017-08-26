package org.tianjyan.luban.infrastructure.client;

import org.tianjyan.luban.aidl.InPara;

import java.util.List;

interface IInParaManager {
    void register(InPara inPara);
    void unregister(String paraName);
    void clear();
    boolean isEmpty();
    List<InPara> getAll();
    InPara getInPara(String paraName);
    String getInPara(String paraName, String origVal);
}
