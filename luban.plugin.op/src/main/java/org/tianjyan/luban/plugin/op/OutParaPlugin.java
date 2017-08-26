package org.tianjyan.luban.plugin.op;

import android.app.Application;
import android.app.Fragment;

import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.IOutParaPlugin;
import org.tianjyan.luban.plugin.op.activity.OutParaFragment;
import org.tianjyan.luban.plugin.op.bridge.UIOutParaBridge;

import dagger.Lazy;

public class OutParaPlugin implements IOutParaPlugin {
    Lazy<UIOutParaBridge> outParaBridgeLazy;
    OutParaFragment outParaFragment;

    private final ILBApp app;
    private boolean isGathering;

    public OutParaPlugin(ILBApp app, Lazy<UIOutParaBridge> outParaBridgeLazy) {
        this.app = app;
        this.outParaBridgeLazy = outParaBridgeLazy;
    }

    @Override
    public String getPluginName() {
        return ((Application) app).getString(R.string.function_out_para);
    }

    @Override
    public Fragment getPluginFragment() {
        if (outParaFragment == null) {
            outParaFragment = new OutParaFragment();
        }
        return outParaFragment;
    }

    public ILBApp getApp() {
        return app;
    }

    @Override
    public boolean isGathering() {
        return isGathering;
    }

    @Override
    public void setIsGathering(boolean isGathering) {
        this.isGathering = isGathering;
    }

    @Override
    public void registerOutPara(OutPara outPara) {
        outParaBridgeLazy.get().registerOutPara(outPara);
    }

    @Override
    public void setOutPara(OutPara outPara, String value) {
        outParaBridgeLazy.get().setOutPara(outPara, value);
    }

    @Override
    public void clientDisconnect(String pkgName) {
        outParaBridgeLazy.get().clientDisconnect(pkgName);
    }
}
