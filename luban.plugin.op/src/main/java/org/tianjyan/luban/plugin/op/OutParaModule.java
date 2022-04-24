package org.tianjyan.luban.plugin.op;

import org.tianjyan.luban.infrastructure.abs.IClientManager;
import org.tianjyan.luban.infrastructure.abs.plugin.IOutParaPlugin;
import org.tianjyan.luban.plugin.common.AliasName;
import org.tianjyan.luban.plugin.op.bridge.UIOutParaBridge;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class OutParaModule {
    @Provides
    @Named(AliasName.OUT_PARA_PLUGIN)
    @Singleton
    public static IOutParaPlugin provideOutParaPlugin(@Named(AliasName.OUT_PARA_BRIDGE) Lazy<UIOutParaBridge> outParaBridgeLazy) {
        return new OutParaPlugin(outParaBridgeLazy);
    }

    @Provides
    @Named(AliasName.OUT_PARA_BRIDGE)
    @Singleton
    public static UIOutParaBridge provideOutParaBridge(@Named(AliasName.CLIENT_MANAGER) IClientManager clientManager,
                                                       @Named(AliasName.OUT_PARA_PLUGIN) Lazy<IOutParaPlugin> outParaPluginLazy) {
        return new UIOutParaBridge(clientManager, outParaPluginLazy);
    }
}
