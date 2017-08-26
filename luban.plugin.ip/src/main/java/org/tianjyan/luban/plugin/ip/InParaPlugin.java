package org.tianjyan.luban.plugin.ip;

import android.app.Application;
import android.app.Fragment;

import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.infrastructure.abs.IInParaPlugin;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.plugin.ip.activity.InParaFragment;
import org.tianjyan.luban.plugin.ip.bridge.UIInParaBridge;

public class InParaPlugin implements IInParaPlugin {
    private final ILBApp app;
    private final UIInParaBridge inParaBridge;

    public InParaPlugin(ILBApp app, UIInParaBridge inParaBridge) {
        this.app = app;
        this.inParaBridge = inParaBridge;
    }

    @Override
    public String getPluginName() {
        return ((Application)app).getString(R.string.function_in_para);
    }

    @Override
    public Fragment getPluginFragment() {
        return new InParaFragment();
    }

    @Override
    public ILBApp getApp() {
        return app;
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
