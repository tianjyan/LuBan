package org.tianjyan.luban.plugin.ip;

import android.app.Fragment;

import org.tianjyan.luban.infrastructure.abs.IPlugin;

public class InParaPlugin implements IPlugin {
    @Override
    public String getPluginName() {
        return "入参设置";
    }

    @Override
    public Fragment getPluginFragment() {
        return null;
    }
}
