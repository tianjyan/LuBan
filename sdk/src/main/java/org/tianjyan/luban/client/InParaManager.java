package org.tianjyan.luban.client;

public class InParaManager {
    public void register(String paraName, String defaultValue, String... optionalValues) {
        LBInternal.getInstance().getInParaManager().register(
                paraName, defaultValue, optionalValues);
    }
}
