package org.tianjyan.luban.manager;

import org.tianjyan.luban.aidl.OutPara;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefaultOutParaManager implements IOutParaManager {
    protected IClient client;
    protected Map<String, OutPara> outParaMap = Collections.synchronizedMap(new LinkedHashMap<>());

    public DefaultOutParaManager(IClient client) {
        this.client = client;
    }

    @Override
    public void register(OutPara outPara) {
        synchronized (outParaMap) {
            if (outPara != null
                    && outPara.getKey() != null
                    && getOutPara(outPara.getKey()) == null) {
                outPara.setClient(client.getPackageName());
                outParaMap.put(outPara.getKey(), outPara);
            }
        }
    }

    @Override
    public void unregister(String paraName) {
        synchronized (outParaMap) {
            outParaMap.remove(paraName);
        }
    }

    @Override
    public void clear() {
        List<OutPara> opList = getAll();
        for (OutPara op : opList) {
            unregister(op.getKey());
        }
    }

    @Override
    public boolean isEmpty() {
        return outParaMap.isEmpty();
    }

    @Override
    public List<OutPara> getAll() {
        List<OutPara> result = new ArrayList<>();
        result.addAll(outParaMap.values());
        return result;
    }

    @Override
    public OutPara getOutPara(String paraName) {
        return outParaMap.get(paraName);
    }

    @Override
    public void setOutPara(String paraName, String value) {
        OutPara outPara = getOutPara(paraName);
        if (outPara != null) {
            outPara.setValue(value);
        }
    }

    @Override
    public void setOutParaMonitor(String paraName, boolean flag) {
        OutPara outPara = getOutPara(paraName);
        if (outPara != null) {
            outPara.setMonitor(flag);
        }
    }
}
