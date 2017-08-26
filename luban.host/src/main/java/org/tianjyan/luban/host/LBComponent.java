package org.tianjyan.luban.host;

import org.tianjyan.luban.infrastructure.client.ClientModule;
import org.tianjyan.luban.plugin.ip.InParaModule;
import org.tianjyan.luban.plugin.op.OutParaModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        LBAppModule.class,
        ClientModule.class,
        OutParaModule.class,
        InParaModule.class
})
interface LBComponent extends AndroidInjector<LBApp> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<LBApp> {}
}
