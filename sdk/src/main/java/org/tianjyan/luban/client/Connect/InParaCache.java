package org.tianjyan.luban.client.Connect;

import org.tianjyan.luban.aidl.InPara;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class InParaCache {
    private Map<String, InPara> map;

    InParaCache() {
        map = new LinkedHashMap<>();
    }

    void register(InPara inPara) {
        if (inPara != null && inPara.getKey() != null) {
            map.put(inPara.getKey(), inPara);
        }
    }

    void put(String key, String newValue) {
        InPara inPara = map.get(key);
        if (inPara != null) {
            List<String> values = inPara.getValues();
            if (values != null) {
                values.add(0, newValue);
            }
        }
    }

    Collection<InPara> getAll() {
        return map.values();
    }

    InPara get(String key) {
        return map.get(key);
    }

    void clear() {
        map.clear();
    }
}
