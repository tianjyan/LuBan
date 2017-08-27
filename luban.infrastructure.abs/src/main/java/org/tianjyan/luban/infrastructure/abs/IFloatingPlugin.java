package org.tianjyan.luban.infrastructure.abs;

import org.tianjyan.luban.aidl.OutPara;

public interface IFloatingPlugin {
    void outParaValueUpdate(OutPara outPara, String value);
    void clientDisconnect(String pkgName);
    void showLogo();
    void hideLogo();
    void showDetail();
    void hideDetail();
}
