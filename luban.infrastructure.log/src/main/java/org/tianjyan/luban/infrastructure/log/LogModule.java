package org.tianjyan.luban.infrastructure.log;

import org.tianjyan.luban.infrastructure.abs.ILog;
import org.tianjyan.luban.plugin.common.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LogModule {
    @Provides
    @Named(AliasName.LOG)
    @Singleton
    public static ILog provideLog() {
        return new Log();
    }
}
