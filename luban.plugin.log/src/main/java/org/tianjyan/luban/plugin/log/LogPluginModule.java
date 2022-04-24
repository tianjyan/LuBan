package org.tianjyan.luban.plugin.log;

import android.content.Context;

import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.plugin.ILogPlugin;
import org.tianjyan.luban.plugin.common.AliasName;
import org.tianjyan.luban.plugin.log.bridge.UILogBridge;
import org.tianjyan.luban.plugin.log.manager.LogManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LogPluginModule {
    @Provides
    @Named(AliasName.LOG_PLUGIN)
    @Singleton
    public static ILogPlugin provideILogPlugin(@Named(AliasName.LOG_MANAGER) Lazy<LogManager> logManagerLazy) {
        return new LogPlugin(logManagerLazy);
    }

    @Provides
    @Named(AliasName.LOG_MANAGER)
    @Singleton
    public static LogManager provideLogManager(@ApplicationContext Context context,
                                               @Named(AliasName.LOG_BRIDGE)UILogBridge logBridge) {
        return new LogManager(context, logBridge);
    }


    @Provides
    @Named(AliasName.LOG_BRIDGE)
    @Singleton
    public static UILogBridge provideLogBridge() {
        return new UILogBridge();
    }
}
