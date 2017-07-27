package org.tianjyan.luban.client.Connect;

public class DisconnectingState extends AbsConnState{
    private DataCacheController dataCacheController;

    public DisconnectingState(DataCacheController dataCacheController) {
        this.dataCacheController = dataCacheController;
    }

    @Override
    public void finish() {
        dataCacheController.dispose();
    }
}
