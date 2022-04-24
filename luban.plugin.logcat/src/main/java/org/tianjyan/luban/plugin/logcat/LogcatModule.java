package org.tianjyan.luban.plugin.logcat;

import android.content.Context;

import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.plugin.ILogcatPlugin;
import org.tianjyan.luban.plugin.common.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LogcatModule {
    @Provides
    @Named(AliasName.LOGCAT_PLUGIN)
    @Singleton
    public static ILogcatPlugin provideIPlugin(@ApplicationContext  Context context) {
        return new LogcatPlugin(context);
    }
}
