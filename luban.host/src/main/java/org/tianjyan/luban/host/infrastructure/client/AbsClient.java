package org.tianjyan.luban.host.infrastructure.client;

import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.host.infrastructure.abs.IClient;

import java.util.List;

abstract class AbsClient implements IClient {
    protected String packageName;
    protected IInParaManager inParaManager;
    protected IOutParaManager outParaManager;

    AbsClient(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void registerInPara(InPara inPara) {
        inParaManager.register(inPara);
    }

    public void unregisterInPara(String paraName) {
        inParaManager.unregister(paraName);
    }

    public void isInParaEmpty() {
        inParaManager.isEmpty();
    }

    public List<InPara> getInPara() {
        return inParaManager.getAll();
    }

    public InPara getInPara(String paraName) {
        return inParaManager.getInPara(paraName);
    }

    public String getInPara(String paraName, String origVal) {
        return inParaManager.getInPara(paraName, origVal);
    }

    public void registerOutPara(OutPara outPara) {
        outParaManager.register(outPara);
    }

    public void unregisterOutPara(String paraName) {
        outParaManager.unregister(paraName);
    }

    public void isOutParaEmpty() {
        outParaManager.isEmpty();
    }

    public OutPara getOutPara(String paraName) {
        return outParaManager.getOutPara(paraName);
    }

    public List<OutPara> getOutPara() {
        return outParaManager.getAll();
    }

    public void setOutPara(String paraName, String value) {
        outParaManager.setOutPara(paraName, value);
    }

    public void clear() {
        inParaManager.clear();
        outParaManager.clear();
    }
}
