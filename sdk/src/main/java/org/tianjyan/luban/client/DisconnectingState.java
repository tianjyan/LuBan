package org.tianjyan.luban.client;

class DisconnectingState extends AbsEmptyConnState {
    private DataCacheController dataCacheController;

    public DisconnectingState(DataCacheController dataCacheController) {
        this.dataCacheController = dataCacheController;
    }

    @Override
    public void finish() {
        // 只是一个防守性质的操作
        // 正常情况下，Disconnecting的下一个操作必然是Disconnected，上一个操作必然是Connected，
        // 在Connected结束的时候，已经清理过dataCacheController的内容了。
        dataCacheController.dispose();
    }
}
