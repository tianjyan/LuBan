package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

class DisconnectedState implements IConnState {
    private DataCacheController dataCacheController;

    public DisconnectedState(DataCacheController dataCacheController) {
        this.dataCacheController = dataCacheController;
    }

    @Override
    public void init(IService service) {
        dataCacheController.dispose();
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
    public void registerInParas(InPara[] inParas) {

    }

    @Override
    public void registerOutParas(OutPara[] outParas) {

    }

    @Override
    public void setOutPara(String paraName, String value) {

    }

    @Override
    public void setInPara(String paraName, String value) {

    }

    @Override
    public String getInPara(String paraName) {
        return null;
    }
}
