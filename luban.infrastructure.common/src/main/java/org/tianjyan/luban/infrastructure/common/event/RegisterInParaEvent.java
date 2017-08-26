package org.tianjyan.luban.infrastructure.common.event;

import org.tianjyan.luban.aidl.InPara;

public class RegisterInParaEvent {
    private InPara inPara;

    public RegisterInParaEvent(InPara inPara) {
        this.inPara = inPara;
    }

    public InPara getInPara() {
        return inPara;
    }
}
