package org.tianjyan.luban.plugin.ip;

import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.IPlugin;
import org.tianjyan.luban.infrastructure.common.consts.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InParaModule {
    @Provides
    @Named(AliasName.IN_PARA_PLUGIN)
    @Singleton
    public static IPlugin provideInParaPlugin(ILBApp app) {
        return new InParaPlugin(app);
    }
}
