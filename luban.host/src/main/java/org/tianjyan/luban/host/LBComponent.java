package org.tianjyan.luban.host;

import org.tianjyan.luban.infrastructure.client.ClientModule;
import org.tianjyan.luban.plugin.common.CommonModule;
import org.tianjyan.luban.plugin.floating.FloatingModule;
import org.tianjyan.luban.plugin.ip.InParaModule;
import org.tianjyan.luban.plugin.log.LogModule;
import org.tianjyan.luban.plugin.op.OutParaModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        LBAppModule.class,
        ClientModule.class,
        OutParaModule.class,
        InParaModule.class,
        LogModule.class,
        FloatingModule.class,
        CommonModule.class
})
interface LBComponent extends AndroidInjector<LBApp> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<LBApp> {

    }
}
