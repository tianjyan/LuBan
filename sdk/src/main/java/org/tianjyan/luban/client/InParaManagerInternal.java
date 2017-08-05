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
        List<String> values = new ArrayList<>();
        values.add(defaultValue);

        for (String i: optionalValues) {
            values.add(i);
        }

        if (!values.contains("None")) {
            values.add("None");
        }
        inPara.setValues(values);
        temp.add(inPara);
    }

    InPara[] getAndClearTempParas() {
        InPara[] result = temp.toArray(new InPara[]{});
        temp.clear();
        return result;
    }
}
