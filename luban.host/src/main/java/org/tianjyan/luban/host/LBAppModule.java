package org.tianjyan.luban.host;

import android.os.IBinder;

import org.tianjyan.luban.infrastructure.abs.IClientManager;
import org.tianjyan.luban.infrastructure.abs.plugin.IFloatingPlugin;
import org.tianjyan.luban.infrastructure.abs.plugin.IInParaPlugin;
import org.tianjyan.luban.infrastructure.abs.plugin.ILogPlugin;
import org.tianjyan.luban.infrastructure.abs.plugin.IOutParaPlugin;
import org.tianjyan.luban.plugin.common.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
abstract class LBAppModule {

    @Provides
    @Singleton
    public static IBinder provideBinder(@Named(AliasName.CLIENT_MANAGER) IClientManager clientManager,
                                        @Named(AliasName.OUT_PARA_PLUGIN) IOutParaPlugin outPlugin,
                                        @Named(AliasName.IN_PARA_PLUGIN) IInParaPlugin inPlugin,
                                        @Named(AliasName.LOG_PLUGIN) ILogPlugin logPlugin,
                                        @Named(AliasName.FLOATING_PLUGIN)IFloatingPlugin floatingPlugin) {
        return new LBBinder(clientManager, outPlugin, inPlugin, logPlugin, floatingPlugin);
    }
}
