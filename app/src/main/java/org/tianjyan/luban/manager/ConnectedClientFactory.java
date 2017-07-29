package org.tianjyan.luban.manager;

public class ConnectedClientFactory extends AbsClientFactory {
    @Override
    public IClient createClient(String key) {
        IClient client = ClientManager.getInstance().getClient(key);
        if (client == null) {
            client = new ConnectedClient(key);
        }
        return client;
    }
}
