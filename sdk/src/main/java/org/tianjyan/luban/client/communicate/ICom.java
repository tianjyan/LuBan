package org.tianjyan.luban.client.communicate;

public interface ICom {
    int checkIsCanConnect(String pkgName, int versionId);
    void initConnect(String pkgName, int pid);
    boolean disconnect(String pkgName);
}