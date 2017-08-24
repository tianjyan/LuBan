package org.tianjyan.luban.plugin.ip;

import org.tianjyan.luban.infrastructure.abs.IPlugin;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InParaModule {
    @Provides
    @Named("InParaPlugin")
    @Singleton
    public static IPlugin provideInParaPlugin() {
        return new InParaPlugin();
    }
}
