package org.tianjyan.luban.host.plugin.ip;

import androidx.fragment.app.Fragment;

import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.host.R;
import org.tianjyan.luban.host.plugin.common.Utils;

import org.tianjyan.luban.host.infrastructure.abs.plugin.IInParaPlugin;
import org.tianjyan.luban.host.plugin.ip.activity.InParaFragment;
import org.tianjyan.luban.host.plugin.ip.bridge.UIInParaBridge;

public class InParaPlugin implements IInParaPlugin {
    private final UIInParaBridge inParaBridge;

    public InParaPlugin(UIInParaBridge inParaBridge) {
        this.inParaBridge = inParaBridge;
    }

    @Override
    public String getPluginName() {
        return Utils.getString(R.string.function_in_para);
    }

    @Override
    public Fragment getPluginFragment() {
        return new InParaFragment();
    }

    @Override
    public void registerInPara(InPara inPara) {
        inParaBridge.registerInPara(inPara);
    }

    @Override
    public void clientDisconnect(String pkgName) {
        inParaBridge.clientDisConnect(pkgName);
    }
}
