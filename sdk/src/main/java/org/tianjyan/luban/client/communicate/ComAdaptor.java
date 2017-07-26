package org.tianjyan.luban.client.communicate;

public class ComAdaptor implements ICom {

    @Override
    public void initConnect(String pkgName, int pid) {
    }

    @Override
    public int checkIsCanConnect(String cur_pkgName, int versionId) {
        return -1;
    }

    @Override
    public boolean disconnect(String cur_pkgName) {
        return false;
    }
}
