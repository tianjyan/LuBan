package org.tianjyan.luban.client.communicate;

public class ComAdaptor implements ICom {

    @Override
    public void initConnect(String pkgName, int pid) {
    }

    @Override
    public int checkIsCanConnect(String pkgName, int versionId) {
        return -1;
    }

    @Override
    public boolean disconnect(String pkgName) {
        return false;
    }
}
