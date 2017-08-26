package org.tianjyan.luban.infrastructure.common.event;

public class ClientDisconnectEvent {
    private String pkgName;

    public ClientDisconnectEvent(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getPkgName() {
        return pkgName;
    }
}
