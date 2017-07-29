package org.tianjyan.luban.client;

public class OutParaManager {
    public void register(String paraName, String alias) {
        LBInternal.getInstance().getOutParaManager().register(paraName, alias);
    }

    public void setOutParasInFloatingArea(String... paraNames) {
        LBInternal.getInstance().getOutParaManager().setOutParasInFloatingArea(paraNames);
    }

    public void setOutParasInDisableArea() {
        LBInternal.getInstance().getOutParaManager().setOutParasInDisableArea();
    }

    public void setOutParasInDisableArea(String... paraNames) {
        LBInternal.getInstance().getOutParaManager().setOutParasInDisableArea(paraNames);
    }
}
