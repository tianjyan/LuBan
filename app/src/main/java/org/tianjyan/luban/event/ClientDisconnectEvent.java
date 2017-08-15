package org.tianjyan.luban.event;

public class ClientDisconnectEvent {
    private String pkgName;

    public ClientDisconnectEvent(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getPkgName() {
        return pkgName;
    }
}
