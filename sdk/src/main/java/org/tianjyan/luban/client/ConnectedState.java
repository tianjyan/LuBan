package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

class ConnectedState extends AbsDataCachedConnState  {
    LogTaskConsumer logTaskConsumer;
    ParaTaskConsumer paraTaskConsumer;

    ConnectedState(DataCacheController dataCacheController) {
        super(dataCacheController);
    }

    @Override
    public void init(IService service) {
        cacheController.transParasToConsole();
        logTaskConsumer = new LogTaskConsumer(service, cacheController);
        logTaskConsumer.start();
        paraTaskConsumer = new ParaTaskConsumer(service, cacheController);
        paraTaskConsumer.start();
    }

    @Override
    public void finish() {
        logTaskConsumer.stop(cacheController);
        paraTaskConsumer.stop(cacheController);
        try {
            // TODO: 信号量处理
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cacheController.dispose();
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
        cacheController.setOutPara(paraName, value);
    }

    @Override
    public void setInPara(String paraName, String value) {
        cacheController.setInPara(paraName, value);
    }

    @Override
    public String getInPara(String paraName) {
        return cacheController.getInParaFromCache(paraName);
    }
}
