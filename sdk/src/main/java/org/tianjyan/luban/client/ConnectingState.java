package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

class ConnectingState extends AbsDataCachedConnState {

    ConnectingState(DataCacheController dataCacheController) {
        super(dataCacheController);
    }

    @Override
    public void init(IService service) {
        cacheController.init();
    }

    @Override
    public void finish() {

    }

    @Override
    public void registerInParas(InPara[] inParas) {
        for (InPara para: inParas) {
            cacheController.registerInParaToCache(para);
        }
    }

    @Override
    public void registerOutParas(OutPara[] outParas) {
        for (OutPara para: outParas) {
            cacheController.registerOutPareToCache(para);
        }
    }

    @Override
    public void setOutPara(String paraName, String value) {
        cacheController.setOutParaToCache(paraName, value);
    }

    @Override
    public void setInPara(String paraName, String value) {
        cacheController.setInParaToCache(paraName, value);
    }

    @Override
    public String getInPara(String paraName) {
        return cacheController.getInParaFromCache(paraName);
    }
}
