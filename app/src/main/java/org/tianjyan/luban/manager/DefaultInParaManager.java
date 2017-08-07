package org.tianjyan.luban.manager;

import org.tianjyan.luban.aidl.InPara;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefaultInParaManager implements IInParaManager {
    protected IClient client;
    protected Map<String, InPara> inParaMap = Collections.synchronizedMap(new LinkedHashMap<>());

    public DefaultInParaManager(IClient client) {
        this.client = client;
    }

    @Override
    public void register(InPara inPara) {
        synchronized (inParaMap) {
            if (inPara != null
                    && inPara.getKey() != null) {
                inPara.setClient(client.getPackageName());
                inParaMap.put(inPara.getKey(), inPara);
            }
        }
    }

    @Override
    public void unregister(String paraName) {
        synchronized (inParaMap) {
            inParaMap.remove(paraName);
        }
    }

    @Override
    public void clear() {
        List<InPara> ipList = getAll();
        for (InPara ip : ipList) {
            unregister(ip.getKey());
        }
    }

    @Override
    public boolean isEmpty() {
        return inParaMap.isEmpty();
    }

    @Override
    public List<InPara> getAll() {
        List<InPara> result = new ArrayList<>();
        result.addAll(inParaMap.values());
        return result;
    }

    @Override
    public InPara getInPara(String paraName) {
        return inParaMap.get(paraName);
    }

    @Override
    public String getInPara(String paraName, String origVal) {
        InPara inPara = getInPara(paraName);
        if (inPara == null
                || inPara.getValues() == null
                || inPara.getValues().size() == 0
                || inPara.getSelectedValue() == null
                || inPara.getSelectedValue().isEmpty()) {
            return origVal;
        } else if (inPara.getSelectedValue() == "None") {
            return origVal;
        } else {
            return inPara.getSelectedValue();
        }
    }
}
