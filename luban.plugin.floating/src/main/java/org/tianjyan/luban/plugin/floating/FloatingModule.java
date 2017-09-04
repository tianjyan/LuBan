package org.tianjyan.luban.plugin.floating;

import org.tianjyan.luban.infrastructure.abs.plugin.IFloatingPlugin;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.plugin.common.consts.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class FloatingModule {
    @Provides
    @Named(AliasName.FLOATING_PLUGIN)
    @Singleton
    public static IFloatingPlugin provideFloatingPlugin(ILBApp app) {
        return new FloatingPlugin(app);
    }
}
