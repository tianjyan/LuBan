package org.tianjyan.luban.client.Connect;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

public class ConnectedState extends AbsDataCachedConnState  {
    LogTaskConsumer logTaskConsumer;
    ParaTaskConsumer paraTaskConsumer;

    public ConnectedState(DataCacheController dataCacheController) {
        super(dataCacheController);
    }

    @Override
    public void init(IConnState lastState) {

    }

    @Override
    public void init(IConnState lastState, IService lbService) {
        dataCacheController.transParasToConsole();
        logTaskConsumer = new LogTaskConsumer(lbService, dataCacheController);
        logTaskConsumer.start();
        paraTaskConsumer = new ParaTaskConsumer(lbService, dataCacheController);
        paraTaskConsumer.start();
    }

    @Override
    public void finish() {
        logTaskConsumer.stop(dataCacheController);
        paraTaskConsumer.stop(dataCacheController);
        try {
            // TODO: 信号量处理
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dataCacheController.dispose();
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
        dataCacheController.setOutPara(paraName, value);
    }

    @Override
    public void setInPara(String paraName, String value) {
        dataCacheController.setInPara(paraName, value);
    }

    @Override
    public String getInPara(String paraName) {
        return dataCacheController.getInParaFromCache(paraName);
    }
}
