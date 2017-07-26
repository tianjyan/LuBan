package org.tianjyan.luban.aidl;

interface IService {
    String helloWorld();

    	int checkIsCanConnect(String cur_pkgName, int versionId);
    	void initConnect(String pkgName, int pid);
    	boolean disconnect(String cur_pkgName);
}
