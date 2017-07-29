package org.tianjyan.luban.manager;

import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

import java.util.List;

public abstract class AbsClient implements IClient {
    protected String key;
    protected IInParaManager inParaManager;
    protected IOutParaManager outParaManager;

    AbsClient(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void clear() {
        inParaManager.clear();
        outParaManager.clear();
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
}
