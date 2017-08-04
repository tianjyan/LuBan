package org.tianjyan.luban.bridge;

import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.model.ParaHistory;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class UIOutParaHistoryBridge {
    private ConcurrentHashMap<OutPara, Vector<ParaHistory>> histories = new ConcurrentHashMap<>();

    private static UIOutParaHistoryBridge INSTANCE = new UIOutParaHistoryBridge();
    public static UIOutParaHistoryBridge getInstance() {
        return INSTANCE;
    }

    public void addHistory(OutPara para, String value) {
        Vector<ParaHistory> paraHistories = histories.get(para);

        if (paraHistories == null) {
            paraHistories = new Vector<>();
            histories.put(para, paraHistories);
        }

        paraHistories.add(new ParaHistory(System.currentTimeMillis(), value));
    }

    public Vector<ParaHistory> getHistories(OutPara para) {
        return histories.get(para);
    }
}
