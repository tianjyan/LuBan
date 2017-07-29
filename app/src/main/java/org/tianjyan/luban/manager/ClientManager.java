package org.tianjyan.luban.manager;

import java.util.HashMap;
import java.util.Map;

public class ClientManager {
    private static ClientManager INSTANCE = new ClientManager();
    public static ClientManager getInstance() {
        return INSTANCE;
    }

    private Map<String, IClient> clientMapS = new HashMap<>();
    private Map<Integer, IClient> clientMapI = new HashMap<>();
    private Map<String, Integer> keyMap = new HashMap<>();

    public IClient getClient(String key) {
        return clientMapS.get(key);
    }

    public IClient getClient(int intKey) {
        return clientMapI.get(intKey);
    }

    public synchronized void addClient(String key, int intKey, IClient client) {
        if (!clientMapS.containsKey(key)) {
            clientMapS.put(key, client);
            clientMapI.put(intKey, client);
            keyMap.put(key, intKey);
        }
    }

    public synchronized  void removeClient(String key) {
        clientMapS.remove(key);
        if (keyMap.get(key) == null) {
            return;
        }
        IClient client = clientMapI.remove(keyMap.get(key));
        client.clear();
    }
}
