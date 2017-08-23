package org.tianjyan.luban.sdk;

import org.tianjyan.luban.aidl.OutPara;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

class OutParaCache {
    private Map<String, OutPara> map;

    OutParaCache() {
        map = new LinkedHashMap<>();
    }

    void register(OutPara outPara) {
        if (outPara != null && outPara.getKey() != null) {
            map.put(outPara.getKey(), outPara);
        }
    }

    void put(String key, String newValue) {
        OutPara outPara = map.get(key);
        if (outPara != null) {
            outPara.setValue(newValue);
        }
    }

    Collection<OutPara> getAll() {
        return map.values();
    }

    OutPara get(String key) {
        return map.get(key);
    }

    void clear() {
        map.clear();
    }
}
