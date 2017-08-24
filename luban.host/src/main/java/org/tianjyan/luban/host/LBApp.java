package org.tianjyan.luban.host;

import android.app.Application;

import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.plugin.ip.InParaModule;
import org.tianjyan.luban.plugin.op.OutParaModule;

public class LBApp extends Application implements ILBApp {
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerLBComponent.builder().inParaModule(new InParaModule()).outParaModule(new OutParaModule()).build();
    }

    @Override
    public void inject(Object object) {
    }

    @Override
    public void configure() {
    }
}
