package org.tianjyan.luban.manager;

public class ConnectedClient extends AbsClient {
    ConnectedClient(String key) {
        super(key);
        inParaManager = new DefaultInParaManager(this);
        outParaManager = new DefaultOutParaManager(this);
    }
}
