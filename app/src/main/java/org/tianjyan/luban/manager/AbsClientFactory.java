package org.tianjyan.luban.manager;

public abstract class AbsClientFactory {

    abstract IClient createClient(String key);

    public IClient orderClient(String key, int intKey) {
        IClient client = createClient(key);
        ClientManager.getInstance().addClient(key, intKey, client);
        return client;
    }
}
