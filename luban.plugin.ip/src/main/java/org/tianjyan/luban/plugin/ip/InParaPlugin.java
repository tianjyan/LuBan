package org.tianjyan.luban.plugin.ip;

import android.app.Application;
import android.app.Fragment;

import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.IPlugin;

public class InParaPlugin implements IPlugin {
    private final ILBApp app;

    public InParaPlugin(ILBApp app) {
        this.app = app;
    }

    @Override
    public String getPluginName() {
        return ((Application)app).getString(R.string.function_in_para);
    }

    @Override
    public Fragment getPluginFragment() {
        return new Fragment();
    }

    @Override
    public ILBApp getApp() {
        return app;
    }
}
