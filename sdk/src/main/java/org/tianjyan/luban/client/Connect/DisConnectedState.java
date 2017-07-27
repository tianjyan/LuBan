package org.tianjyan.luban.client.Connect;

public class DisConnectedState extends AbsConnState{
    private DataCacheController dataCacheController;

    public DisConnectedState(DataCacheController dataCacheController) {
        this.dataCacheController = dataCacheController;
    }

    @Override
    public void init(IConnState lastState) {
        super.init(lastState);
        dataCacheController.dispose();
    }
}
