package org.tianjyan.luban.host.infrastructure.abs.plugin;

import org.tianjyan.luban.aidl.InPara;

public interface IInParaPlugin extends IPlugin {
    void registerInPara(InPara inPara);
    void clientDisconnect(String pkgName);
}
