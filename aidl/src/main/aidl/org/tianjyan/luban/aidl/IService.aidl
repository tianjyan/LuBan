package org.tianjyan.luban.aidl;

interface IService {
    int canConnectLB(String pkgName, int versionId);
    void connectLB(String pkgName, int pid);
    boolean disconnectLB(String pkgName);
}
