package org.tianjyan.luban.infrastructure.abs;

import java.util.Collection;

public interface IClientManager {
    IClient getClient(int pid);
    void addClient(int pid, IClient client);
    void removeClient(int pid);
    Collection<IClient> getAllClient();
}
