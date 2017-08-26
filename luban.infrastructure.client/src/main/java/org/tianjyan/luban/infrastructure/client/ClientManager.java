package org.tianjyan.luban.infrastructure.client;

import org.tianjyan.luban.infrastructure.abs.IClient;
import org.tianjyan.luban.infrastructure.abs.IClientManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class ClientManager implements IClientManager {
    IClient defaultClient;
    public ClientManager(IClient defaultClient) {
        this.defaultClient = defaultClient;
        clientMap.put(0, defaultClient);
    }

    private Map<Integer, IClient> clientMap = new HashMap<>();

    @Override
    public IClient getClient(int pid) {
        return clientMap.get(pid);
    }

    @Override
    public synchronized void addClient(int pid, IClient client) {
        if (!clientMap.containsKey(pid)) {
            clientMap.put(pid, client);
        }
    }

    @Override
    public synchronized void addClient(int pid, String pkgName) {
        if (!clientMap.containsKey(pid)) {
            clientMap.put(pid, new ConnectedClient(pkgName));
        }
    }


    @Override
    public synchronized  void removeClient(int pid) {
        if (clientMap.containsKey(pid)) {
            IClient client = clientMap.remove(pid);
            client.clear();
        }
    }

    @Override
    public synchronized Collection<IClient> getAllClient() {
        return clientMap.values();
    }
}
