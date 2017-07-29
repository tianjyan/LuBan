package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

/**
 * 正在连接上Aidl Service的状态类。
 * 此时会初始化DataCacheController，并将传输的数据缓存起来，以便在连接上以后发送数据。
 */
class ConnectingState extends AbsDataCachedConnState {

    ConnectingState(DataCacheController dataCacheController) {
        super(dataCacheController);
    }

    @Override
    public void init(IService service) {
        // cacheController的初始化时机放在这里，也是唯一一处，
        // 此时可以开始接受数据，放入缓存，等待正式建立连接后将缓存数据再发出
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
    public String getInPara(String paraName) {
        return cacheController.getInParaFromCache(paraName);
    }
}
