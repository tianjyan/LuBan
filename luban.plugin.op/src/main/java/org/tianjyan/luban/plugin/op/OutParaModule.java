package org.tianjyan.luban.plugin.op;

import org.tianjyan.luban.infrastructure.abs.IPlugin;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class OutParaModule {
    @Provides
    @Named("OutParaPlugin")
    @Singleton
    public static IPlugin provideOutParaPlugin() {
        return new OutParaPlugin();
    }
}
