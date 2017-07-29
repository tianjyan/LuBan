package org.tianjyan.luban.client;

public class InParaManager {
    public void register(String paraName, String alias, String defaultValue) {
        LBInternal.getInstance().getInParaManager().register(
                paraName, alias, defaultValue);
    }

    public void setInParasInFloatingArea(String... paraNames) {
        LBInternal.getInstance().getInParaManager().setInParasInFloatingArea(paraNames);
    }

    public void setInParasInDisableArea() {
        LBInternal.getInstance().getInParaManager().setInParasInDisableArea();
    }

    public void setInParasInDisableArea(String... paraNames) {
        LBInternal.getInstance().getInParaManager().setInParasInDisableArea(paraNames);
    }
}
