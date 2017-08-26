package org.tianjyan.luban.host;

import android.os.IBinder;

import org.tianjyan.luban.host.activity.MainActivity;
import org.tianjyan.luban.infrastructure.abs.IClientManager;
import org.tianjyan.luban.infrastructure.abs.IInParaPlugin;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.IOutParaPlugin;
import org.tianjyan.luban.infrastructure.abs.IPlugin;
import org.tianjyan.luban.infrastructure.abs.inject.PreActivity;
import org.tianjyan.luban.infrastructure.common.consts.AliasName;
import org.tianjyan.luban.plugin.op.OutParaModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = AndroidInjectionModule.class)
abstract class LBAppModule {
    @Binds
    @Singleton
    abstract ILBApp application(LBApp app);

    @PreActivity
    @ContributesAndroidInjector
    abstract MainActivity mainActivityInjector();

    @ContributesAndroidInjector
    abstract LBService serviceInjector();

    @Provides
    @Singleton
    public static IBinder provideBinder(ILBApp app, @Named(AliasName.CLIENT_MANAGER) IClientManager clientManager,
                                 @Named(AliasName.OUT_PARA_PLUGIN) IOutParaPlugin outPlugin,
                                 @Named(AliasName.IN_PARA_PLUGIN) IInParaPlugin inPlugin) {
        return new LBBinder(app, clientManager, outPlugin, inPlugin);
    }
}
