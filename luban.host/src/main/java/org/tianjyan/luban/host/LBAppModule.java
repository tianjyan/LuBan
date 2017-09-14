package org.tianjyan.luban.host;

import android.os.IBinder;

import org.tianjyan.luban.host.activity.MainActivity;
import org.tianjyan.luban.host.activity.MainMenuFragment;
import org.tianjyan.luban.infrastructure.abs.IClientManager;
import org.tianjyan.luban.infrastructure.abs.inject.PreFragment;
import org.tianjyan.luban.infrastructure.abs.plugin.IFloatingPlugin;
import org.tianjyan.luban.infrastructure.abs.plugin.IInParaPlugin;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.plugin.ILogPlugin;
import org.tianjyan.luban.infrastructure.abs.plugin.IOutParaPlugin;
import org.tianjyan.luban.infrastructure.abs.inject.PreActivity;
import org.tianjyan.luban.plugin.common.AliasName;

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

    @PreFragment
    @ContributesAndroidInjector
    abstract MainMenuFragment mainMenuFragmentInjector();

    @ContributesAndroidInjector
    abstract LBService serviceInjector();

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
