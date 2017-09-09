package org.tianjyan.luban.plugin.logcat;

import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.plugin.IPlugin;
import org.tianjyan.luban.plugin.common.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class LogcatModule {
    @Provides
    @Named(AliasName.LOGCAT_PLUGIN)
    @Singleton
    public static IPlugin provideIPlugin(ILBApp app) {
        return new LogcatPlugin(app);
    }
}
