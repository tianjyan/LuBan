package org.tianjyan.luban.host;

import org.tianjyan.luban.host.activity.MainActivity;
import org.tianjyan.luban.infrastructure.abs.inject.ActivityScope;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = AndroidInjectionModule.class)
abstract class LBAppModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract MainActivity mainActivityInjector();
}
