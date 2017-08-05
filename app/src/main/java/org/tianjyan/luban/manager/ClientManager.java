package org.tianjyan.luban.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClientManager {
    private static ClientManager INSTANCE = new ClientManager();
    public static ClientManager getInstance() {
        return INSTANCE;
    }

    private ClientManager() {
        clientMap.put(0, DefaultClient.getInstance());
    }

    private Map<Integer, IClient> clientMap = new HashMap<>();

    public IClient getClient(int pid) {
        return clientMap.get(pid);
    }

    public synchronized void addClient(int pid, IClient client) {
        if (!clientMap.containsKey(pid)) {
            clientMap.put(pid, client);
        }
    }

    public synchronized  void removeClient(int pid) {
        if (clientMap.containsKey(pid)) {
            IClient client = clientMap.remove(pid);
            client.clear();
        }
    }

    public synchronized Collection<IClient> getAllClient() {
        return clientMap.values();
    }
}
