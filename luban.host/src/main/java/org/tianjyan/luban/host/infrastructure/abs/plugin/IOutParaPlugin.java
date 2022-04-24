package org.tianjyan.luban.host.infrastructure.abs.plugin;

import org.tianjyan.luban.aidl.OutPara;

public interface IOutParaPlugin extends IPlugin {
    boolean isGathering();
    void setIsGathering(boolean isGathering);
    void registerOutPara(OutPara outPara);
    void setOutPara(OutPara outPara, String value);
    void clientDisconnect(String pkgName);
}
