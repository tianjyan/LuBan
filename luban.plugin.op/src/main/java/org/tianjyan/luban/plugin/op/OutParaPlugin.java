package org.tianjyan.luban.plugin.op;

import android.app.Fragment;

import org.tianjyan.luban.infrastructure.abs.IPlugin;

public class OutParaPlugin implements IPlugin {
    @Override
    public String getPluginName() {
        return "出参监视";
    }

    @Override
    public Fragment getPluginFragment() {
        return null;
    }
}
