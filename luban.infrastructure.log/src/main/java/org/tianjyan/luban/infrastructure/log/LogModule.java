package org.tianjyan.luban.infrastructure.log;

import org.tianjyan.luban.infrastructure.abs.ILog;
import org.tianjyan.luban.plugin.common.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LogModule {
    @Provides
    @Named(AliasName.LOG)
    @Singleton
    public static ILog provideLog() {
        return new Log();
    }
}
