package org.tianjyan.luban.infrastructure.client;

class ConnectedClient extends AbsClient {
    public ConnectedClient(String key) {
        super(key);
        inParaManager = new DefaultInParaManager(this);
        outParaManager = new DefaultOutParaManager(this);
    }
}
