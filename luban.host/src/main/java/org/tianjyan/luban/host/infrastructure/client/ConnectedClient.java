package org.tianjyan.luban.host.infrastructure.client;

class ConnectedClient extends AbsClient {
    public ConnectedClient(String key) {
        super(key);
        inParaManager = new DefaultInParaManager(this);
        outParaManager = new DefaultOutParaManager(this);
    }
}
