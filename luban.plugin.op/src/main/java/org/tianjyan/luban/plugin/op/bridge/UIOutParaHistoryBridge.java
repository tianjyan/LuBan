package org.tianjyan.luban.plugin.op.bridge;

import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.plugin.op.Utils.FileUtils;
import org.tianjyan.luban.plugin.op.model.ParaHistory;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

class UIOutParaHistoryBridge {
    private int totalHistoriesCount = 0;
    private ConcurrentHashMap<OutPara, Vector<ParaHistory>> histories = new ConcurrentHashMap<>();

    private static UIOutParaHistoryBridge INSTANCE = new UIOutParaHistoryBridge();
    static UIOutParaHistoryBridge getInstance() {
        return INSTANCE;
    }

    void addHistory(OutPara para, String value) {
        if (totalHistoriesCount >= Config.MAX_HISTORIES_SUPPORT) {
            return;
        }

        Vector<ParaHistory> paraHistories = histories.get(para);

        if (paraHistories == null) {
            paraHistories = new Vector<>();
            histories.put(para, paraHistories);
        }

        paraHistories.add(0, new ParaHistory(System.currentTimeMillis(), value));
    }

    void addOutPara(OutPara outPara) {
        if (!histories.contains(outPara)) {
            Vector<ParaHistory> paraHistories = paraHistories = new Vector<>();
            histories.put(outPara, paraHistories);
        }
    }

    Vector<ParaHistory> getHistories(OutPara para) {
        return histories.get(para);
    }

    void clearHistories() {
        synchronized (histories) {
            for (Vector<ParaHistory> h : histories.values()) {
                h.clear();
            }
            totalHistoriesCount = 0;
        }
    }

    void saveHistories() {
        synchronized (histories) {
            Iterator iterator = histories.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<OutPara, Vector<ParaHistory>> entry = (Map.Entry) iterator.next();
                FileUtils.saveOutParaHistory(entry.getKey(), entry.getValue());
            }
        }
    }

    void removeHistories(OutPara outPara) {
        histories.remove(outPara);
    }
}
