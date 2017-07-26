package org.tianjyan.luban.client;

import org.tianjyan.luban.client.internal.LBInternal;

public class InParaManager {
    public void register(String ParaName, String alias, String defaultValue, String... optionalValues){
        LBInternal.getInstance().getInParaManager().register(
                ParaName, alias, defaultValue, optionalValues);
    }

    public void defaultInParasInAC(String... ParaNames){
        LBInternal.getInstance().getInParaManager().defaultInParasInAC(ParaNames);
    }

    public void defaultInParasInDisableArea(){
        LBInternal.getInstance().getInParaManager().setInParasDisable();
    }

    public void defaultInParasInDisableArea(String... ParaNames){
        LBInternal.getInstance().getInParaManager().setInParasDisable(ParaNames);
    }
}
