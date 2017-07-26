package org.tianjyan.luban.client;

import org.tianjyan.luban.client.internal.LBInternal;

public class OutParaManager {
    public void register(String ParaName, String alias, Object...extras){
        LBInternal.getInstance().getOutParaManager().register(ParaName, alias, extras);
    }

    public void defaultOutParasInAC(String... ParaNames){
        LBInternal.getInstance().getOutParaManager().defaultOutParasInAC(ParaNames);
    }

    public void defaultOutParasInDisableArea(){
        LBInternal.getInstance().getOutParaManager().setOutParasDisable();
    }

    public void defaultOutParasInDisableArea(String... ParaNames){
        LBInternal.getInstance().getOutParaManager().setOutParasDisable(ParaNames);
    }
}
