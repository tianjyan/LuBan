package org.tianjyan.luban.host;

import org.tianjyan.luban.host.activity.MainActivity;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.inject.PreActivity;
import org.tianjyan.luban.plugin.op.OutParaModule;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
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
}
