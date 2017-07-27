package org.tianjyan.luban.client.Connect;

import org.tianjyan.luban.aidl.AidlTask;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class DataCacheController {
    private ParaTaskCache paraTaskCache;
    private LogTaskCache logTaskCache;
    private OutParaCache outParaCache;
    private InParaCache inParaCache;

    void init() {
        paraTaskCache = new ParaTaskCache();
        logTaskCache = new LogTaskCache();
        outParaCache = new OutParaCache();
        inParaCache = new InParaCache();
    }

    void dispose() {
        if (paraTaskCache != null) paraTaskCache.clear();
        if (logTaskCache != null) logTaskCache.clear();
        if (outParaCache != null) outParaCache.clear();
        if (inParaCache != null) inParaCache.clear();
    }

    boolean putLog(String[] content) {
        return logTaskCache.offer(content);
    }

    String[] takeLog() throws InterruptedException {
        return logTaskCache.take();
    }

    void putParaTask(AidlTask task) {
        if (task != null) {
            paraTaskCache.add(task);
        }
    }

    AidlTask takeParaTask() throws InterruptedException {
        return paraTaskCache.take();
    }

    void setOutPara(String paraName, String value) {
        if (paraName != null) {
            OutPara outPara = new OutPara();
            outPara.setKey(paraName);
            outPara.setValue(value != null ? value : "");
            paraTaskCache.add(outPara);
        }

    }

    void registerOutPareToCache(OutPara outPara) {
        if (outPara != null && outPara.getKey() != null) {
            if (outPara.getValue() == null) {
                outPara.setValue("");
            }
            outParaCache.register(outPara);
        }
    }

    void setOutParaToCache(String paraName, String value) {
        if (value != null) {
            outParaCache.put(paraName, value);
        }
        outParaCache.put(paraName, "");
    }

    String getOutParaFromCache(String paraName) {
        String result = "";
        if (paraName != null && outParaCache.get(paraName) != null) {
            result = outParaCache.get(paraName).getValue();
        }
        return result;
    }

    void setInPara(String paraName, String value) {
        if (paraName != null) {
            InPara inPara = new InPara();
            inPara.setKey(paraName);
            value = value != null ? value : "";
            List<String> values = new ArrayList<>();
            values.add(value);
            inPara.setValues(values);
        }
    }

    void registerInParaToCache(InPara inPara) {
        if (inPara != null && inPara.getKey() != null) {
            if (inPara.getValues() == null) {
                inPara.setValues(new ArrayList<>());
            }
            inParaCache.register(inPara);
        }
    }

    void setInParaToCache(String paraName, String value) {
        if (value != null) {
            inParaCache.put(paraName, value);
        }
    }

    String getInParaFromCache(String paraName) {
        String result = "";
        if (paraName != null && inParaCache.get(paraName) != null) {
            List<String> values = inParaCache.get(paraName).getValues();
            if (values != null && values.size() > 0) {
                result = values.get(0);
            }
        }
        return result;
    }

    void transParasToConsole() {
        Collection<InPara> inParas = inParaCache.getAll();
        if (inParas != null) {
            for (InPara para : inParas) {
                paraTaskCache.add(para);
            }
        }
        inParaCache.clear();

        Collection<OutPara> outParas = outParaCache.getAll();
        if (outParas != null) {
            for (OutPara para : outParas) {
                paraTaskCache.add(para);
            }
        }
        outParaCache.clear();
    }
}
