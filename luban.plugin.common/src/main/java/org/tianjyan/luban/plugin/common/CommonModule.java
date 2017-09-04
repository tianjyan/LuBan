package org.tianjyan.luban.plugin.common;

import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.plugin.common.consts.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class CommonModule {
    @Provides
    @Named(AliasName.COMMON)
    @Singleton
    public static Common provideCommon(ILBApp app) {
        return new Common(app);
    }
}
