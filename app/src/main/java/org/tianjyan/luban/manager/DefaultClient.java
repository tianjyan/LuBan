package org.tianjyan.luban.manager;

import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.aidl.OutPara;

public class DefaultClient extends AbsClient {
    private static DefaultClient INSTANCE = new DefaultClient("Default");
    public static DefaultClient getInstance() {
        return INSTANCE;
    }

    private DefaultClient(String packageName) {
        super(packageName);
        inParaManager = new DefaultInParaManager(this);
        outParaManager = new DefaultOutParaManager(this);

        OutPara cpuPara = new OutPara();
        cpuPara.setKey("CPU");
        cpuPara.setValue("0%");
        cpuPara.setDisplayProperty(AidlEntry.DISPLAY_FLOATING);
        outParaManager.register(cpuPara);
    }
}
