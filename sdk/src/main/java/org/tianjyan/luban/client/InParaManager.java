package org.tianjyan.luban.client;

public class InParaManager {
    public void register(String paraName, String alias, String defaultValue, String... optionalValues) {
        LBInternal.getInstance().getInParaManager().register(
                paraName, alias, defaultValue, optionalValues);
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
