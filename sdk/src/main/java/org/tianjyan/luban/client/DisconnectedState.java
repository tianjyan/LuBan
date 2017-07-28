package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.IService;

class DisconnectedState extends AbsEmptyConnState {
    private DataCacheController dataCacheController;

    public DisconnectedState(DataCacheController dataCacheController) {
        this.dataCacheController = dataCacheController;
    }

    @Override
    public void init(IService service) {
        // 此次代码真正生效的时机有两个：
        // 1. 当连接Aidl Service失败的时候，会从Connecting 状态直接变成Disconnected状态
        // 2. 当发现根本没有安装LuBan主程序的时候，也会从Connecting 状态直接变成Disconnected状态
        dataCacheController.dispose();
    }
}
