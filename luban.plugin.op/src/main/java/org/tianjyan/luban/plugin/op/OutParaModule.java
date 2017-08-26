package org.tianjyan.luban.plugin.op;

import org.tianjyan.luban.infrastructure.abs.IClientManager;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.IOutParaPlugin;
import org.tianjyan.luban.infrastructure.abs.inject.PreActivity;
import org.tianjyan.luban.infrastructure.abs.inject.PreFragment;
import org.tianjyan.luban.infrastructure.common.consts.AliasName;
import org.tianjyan.luban.plugin.op.activity.OutParaDetailActivity;
import org.tianjyan.luban.plugin.op.activity.OutParaFragment;
import org.tianjyan.luban.plugin.op.bridge.UIOutParaBridge;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OutParaModule {
    @Provides
    @Named(AliasName.OUT_PARA_PLUGIN)
    @Singleton
    public static IOutParaPlugin provideOutParaPlugin(ILBApp app,
                                                      @Named(AliasName.OUT_PARA_BRIDGE) Lazy<UIOutParaBridge> outParaBridgeLazy) {
        return new OutParaPlugin(app, outParaBridgeLazy);
    }

    @Provides
    @Named(AliasName.OUT_PARA_BRIDGE)
    @Singleton
    public static UIOutParaBridge provideOutParaBridge(ILBApp app,
                                                       @Named(AliasName.CLIENT_MANAGER) IClientManager clientManager,
                                                       @Named(AliasName.OUT_PARA_PLUGIN) Lazy<IOutParaPlugin> outParaPluginLazy) {
        return new UIOutParaBridge(app, clientManager, outParaPluginLazy);
    }

    @PreActivity
    @ContributesAndroidInjector
    abstract OutParaDetailActivity outParaDetailActivityInjector();

    @PreFragment
    @ContributesAndroidInjector
    abstract OutParaFragment outParaFragmentInjector();
}
