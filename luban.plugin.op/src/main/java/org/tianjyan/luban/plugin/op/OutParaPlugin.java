package org.tianjyan.luban.plugin.op;

import android.app.Application;

import androidx.fragment.app.Fragment;

import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.plugin.IOutParaPlugin;
import org.tianjyan.luban.plugin.common.Utils;
import org.tianjyan.luban.plugin.op.activity.OutParaFragment;
import org.tianjyan.luban.plugin.op.bridge.UIOutParaBridge;

import dagger.Lazy;

public class OutParaPlugin implements IOutParaPlugin {
    Lazy<UIOutParaBridge> outParaBridgeLazy;
    OutParaFragment outParaFragment;
    private boolean isGathering;

    public OutParaPlugin(Lazy<UIOutParaBridge> outParaBridgeLazy) {
        this.outParaBridgeLazy = outParaBridgeLazy;
    }

    @Override
    public String getPluginName() {
        return Utils.getString(R.string.function_out_para);
    }

    @Override
    public Fragment getPluginFragment() {
        if (outParaFragment == null) {
            outParaFragment = new OutParaFragment();
        }
        return outParaFragment;
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
