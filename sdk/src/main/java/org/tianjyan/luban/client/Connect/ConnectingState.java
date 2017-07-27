package org.tianjyan.luban.client.Connect;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

public class ConnectingState extends AbsDataCachedConnState {

    public ConnectingState(DataCacheController dataCacheController) {
        super(dataCacheController);
    }

    @Override
    public void init(IConnState lastState) {
        dataCacheController.init();
    }

    @Override
    public void init(IConnState lastState, IService lbService) {

    }

    @Override
    public void finish() {

    }

    @Override
    public void registerInParas(InPara[] inParas) {
        for (InPara para: inParas) {
            dataCacheController.registerInParaToCache(para);
        }
    }

    @Override
    public void registerOutParas(OutPara[] outParas) {
        for (OutPara para: outParas) {
            dataCacheController.registerOutPareToCache(para);
        }
    }

    @Override
    public void setOutPara(String paraName, String value) {
        dataCacheController.setOutParaToCache(paraName, value);
    }

    @Override
    public void setInPara(String paraName, String value) {
        dataCacheController.setInParaToCache(paraName, value);
    }

    @Override
    public String getInPara(String paraName) {
        return dataCacheController.getInParaFromCache(paraName);
    }
}
