package org.tianjyan.luban.client;

public class InParaManager {
    public void register(String paraName, String defaultValue, String... optionalValues) {
        LBInternal.getInstance().getInParaManager().register(
                paraName, defaultValue, optionalValues);
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
