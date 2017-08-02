package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.InPara;

import java.util.ArrayList;
import java.util.List;

class InParaManagerInternal {
    private List<InPara> temp;
    private InParaManager userInterface;

    InParaManagerInternal() {
        temp = new ArrayList<>();
        userInterface = new InParaManager();
    }

    InParaManager getUserInterface() {
        return userInterface;
    }

    void register(String paraName, String defaultValue, String... optionalValues) {
        if (paraName == null || defaultValue == null) {
            return;
        }

        InPara inPara = new InPara();
        inPara.setKey(paraName);
        inPara.setDisplayProperty(InPara.DISPLAY_NORMAL);
        List<String> values = new ArrayList<>();
        values.add(defaultValue);

        for (String i: optionalValues) {
            values.add(i);
        }

        if (!values.contains("<null>")) {
            values.add("<null>");
        }
        inPara.setValues(values);
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
}
