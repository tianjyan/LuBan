package org.tianjyan.luban.host.plugin.floating;

import android.content.Context;

import org.tianjyan.luban.host.plugin.common.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

import org.tianjyan.luban.host.infrastructure.abs.plugin.IFloatingPlugin;

@Module
@InstallIn(SingletonComponent.class)
public class FloatingModule {
    @Provides
    @Named(AliasName.FLOATING_PLUGIN)
    @Singleton
    public static IFloatingPlugin provideFloatingPlugin(@ApplicationContext Context context) {
        return new FloatingPlugin(context);
    }
}
