package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.InPara;

import java.util.ArrayList;
import java.util.List;

class InParaManagerInternal {
    //region Field
    private List<InPara> temp;
    private InParaManager userInterface;
    //endregion

    //region Constructor
    InParaManagerInternal() {
        temp = new ArrayList<>();
        userInterface = new InParaManager();
    }
    //endregion

    //region Internal Method
    InParaManager getUserInterface() {
        return userInterface;
    }

    void register(String paraName, String alias, String defaultValue) {
        if (paraName == null || alias == null || defaultValue == null) {
            return;
        }

        if (alias.length() > 4) {
            alias = alias.substring(0, 3) + ".";
        }

        InPara inPara = new InPara();
        inPara.setKey(paraName);
        inPara.setAlias(alias);
        inPara.setDisplayProperty(InPara.DISPLAY_NORMAL);
        List<String> values = new ArrayList<>();
        values.add(defaultValue);

        if (!values.contains("<null>")) {
            values.add("<null>");
        }
        inPara.setValues(values);
        inPara.setRegistering(true);
        temp.add(inPara);
    }

    void setInParasInFloatingArea(String... paraNames) {
        if (paraNames != null) {
            int len = paraNames.length;
            for (int i = 0; i < temp.size(); i++) {
                for (int j = 0; j < len; j++) {
                    if (temp.get(i).getKey().equals(paraNames[j])) {
                        temp.get(i).setDisplayProperty(InPara.DISPLAY_FLOATING);
                    }
                }
            }
        }
    }

    void setInParasInDisableArea() {
        for (int i = 0; i < temp.size(); i++) {
            temp.get(i).setDisplayProperty(InPara.DISPLAY_DISABLE);
        }
    }

    void setInParasInDisableArea(String... paraNames) {
        if (paraNames != null) {
            int len = paraNames.length;
            for (int i = 0; i < temp.size(); i++) {
                for (int j = 0; j < len; j++) {
                    if (temp.get(i).getKey().equals(paraNames[j])) {
                        temp.get(i).setDisplayProperty(InPara.DISPLAY_DISABLE);
                    }
                }
            }
        }
    }

    InPara[] getAndClearTempParas() {
        InPara[] result = temp.toArray(new InPara[]{});
        temp.clear();
        return result;
    }
    //endregion
}
