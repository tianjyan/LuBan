package org.tianjyan.luban.host;

import org.tianjyan.luban.infrastructure.client.ClientModule;
//import org.tianjyan.luban.infrastructure.log.LogModule;
import org.tianjyan.luban.plugin.floating.FloatingModule;
import org.tianjyan.luban.plugin.ip.InParaModule;
import org.tianjyan.luban.plugin.log.LogPluginModule;
import org.tianjyan.luban.plugin.logcat.LogcatModule;
import org.tianjyan.luban.plugin.op.OutParaModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        LBAppModule.class,
        ClientModule.class,
        //LogModule.class,
        OutParaModule.class,
        InParaModule.class,
        LogPluginModule.class,
        FloatingModule.class,
        LogcatModule.class
})
interface LBComponent extends AndroidInjector<LBApp> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<LBApp> {

    }
}
