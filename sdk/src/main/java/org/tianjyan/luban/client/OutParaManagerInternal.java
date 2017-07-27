package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.OutPara;

import java.util.ArrayList;
import java.util.List;

public class OutParaManagerInternal {
    //region Field
    private List<OutPara> temp;
    private OutParaManager userInterface;
    //endregion

    //region Constructor
    public OutParaManagerInternal() {
        temp = new ArrayList<>();
        userInterface = new OutParaManager();
    }
    //endregion

    //region Internal Method
    public OutParaManager getUserInterface() {
        return userInterface;
    }

    public void register(String paraName, String alias) {
        if (paraName == null || alias == null) {
            return;
        }
        if (alias.length() > 4) {
            alias = alias.substring(0, 3) + ".";
        }

        OutPara outPara = new OutPara();
        outPara.setKey(paraName);
        outPara.setAlias(alias);
        outPara.setRegistering(true);
        outPara.setDisplayProperty(OutPara.DISPLAY_NORMAL);
        temp.add(outPara);
    }

    public void setOutParasInFloatingArea(String... paraNames) {
        if (paraNames != null) {
            int len = paraNames.length;
            for (int i = 0; i < temp.size(); i++) {
                for (int j = 0; j < len; j++) {
                    if (temp.get(i).getKey().equals(paraNames[j])) {
                        temp.get(i).setDisplayProperty(OutPara.DISPLAY_FLOATING);
                    }
                }
            }
        }
    }

    public void setOutParasInDisableArea() {
        for (int i = 0; i < temp.size(); i++) {
            temp.get(i).setDisplayProperty(OutPara.DISPLAY_DISABLE);
        }
    }

    public void setOutParasInDisableArea(String... paraNames) {
        if (paraNames != null) {
            int len = paraNames.length;

            for (int i = 0; i < temp.size(); i++) {
                for (int j = 0; j < len; j++) {
                    if (temp.get(i).getKey().equals(paraNames[j])) {
                        temp.get(i).setDisplayProperty(OutPara.DISPLAY_DISABLE);
                    }
                }
            }
        }
    }

    public OutPara[] getAndClearTempParas() {
        OutPara[] result = temp.toArray(new OutPara[]{});
        temp.clear();
        return result;
    }
    //endregion
}
