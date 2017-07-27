package org.tianjyan.luban.client.Connect;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

public abstract class AbsConnState implements IConnState {
    @Override
    public void init(IConnState lastState) {

    }

    @Override
    public void init(IConnState lastState, IService gtService) {

    }

    @Override
    public void finish() {

    }

    @Override
    public void logI(String tag, String msg) {

    }

    @Override
    public void logD(String tag, String msg) {

    }

    @Override
    public void logW(String tag, String msg) {

    }

    @Override
    public void logE(String tag, String msg) {

    }

    @Override
    public void registerOutParas(OutPara[] outParas) {

    }

    @Override
    public void registerInParas(InPara[] inParas) {

    }

    @Override
    public void setOutPara(String paraName, String value) {

    }

    @Override
    public void setInPara(String paraName, String value) {

    }

    @Override
    public String getInPara(String paraName) {
        return "";
    }
}
