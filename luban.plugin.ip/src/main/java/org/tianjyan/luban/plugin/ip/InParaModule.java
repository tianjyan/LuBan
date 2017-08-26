package org.tianjyan.luban.plugin.ip;

import org.tianjyan.luban.infrastructure.abs.IClientManager;
import org.tianjyan.luban.infrastructure.abs.IInParaPlugin;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.inject.PreActivity;
import org.tianjyan.luban.infrastructure.abs.inject.PreFragment;
import org.tianjyan.luban.infrastructure.common.consts.AliasName;
import org.tianjyan.luban.plugin.ip.activity.InParaDetailActivity;
import org.tianjyan.luban.plugin.ip.activity.InParaFragment;
import org.tianjyan.luban.plugin.ip.bridge.UIInParaBridge;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class InParaModule {
    @Provides
    @Named(AliasName.IN_PARA_PLUGIN)
    @Singleton
    public static IInParaPlugin provideInParaPlugin(ILBApp app,
                                                    @Named(AliasName.IN_PARA_BRIDGE) UIInParaBridge inParaBridge) {
        return new InParaPlugin(app, inParaBridge);
    }

    @Provides
    @Named(AliasName.IN_PARA_BRIDGE)
    @Singleton
    public static UIInParaBridge provideInParaBridge(
            @Named(AliasName.CLIENT_MANAGER) IClientManager clientManager) {
        return new UIInParaBridge(clientManager);
    }

    @PreActivity
    @ContributesAndroidInjector
    abstract InParaDetailActivity inParaDetailActivityInjector();

    @PreFragment
    @ContributesAndroidInjector
    abstract InParaFragment inParaFragmentInjector();
}
