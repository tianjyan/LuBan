package org.tianjyan.luban.host;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;

import org.tianjyan.luban.host.model.OnSettingChangeListener;
import org.tianjyan.luban.host.model.SettingKey;
import org.tianjyan.luban.host.view.FloatingView;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.common.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;

public class LBApp extends Application implements ILBApp, HasActivityInjector, HasServiceInjector, SharedPreferences.OnSharedPreferenceChangeListener {
    private static boolean isAppRunning = false;
    private static Context mContext;
    private static int sessionDepth = 0;
    private SharedPreferences mSharedPreferences;
    private Map<SettingKey, List<OnSettingChangeListener>> onSettingChangeListenerMap = new HashMap<>();
    private FloatingView floatingView;

    @Inject DispatchingAndroidInjector<Activity> activityInjector;
    @Inject DispatchingAndroidInjector<Service> serviceInjector;

    public static void setAppRunning(boolean isRunning) {
        isAppRunning = isRunning;
    }

    public static boolean isAppRunning() {
        return isAppRunning;
    }

    public static void exit() {
        LBEntrance.close(mContext);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerLBComponent.builder().create(this).inject(this);
        mContext = getApplicationContext();
        loadSettings();
        Common.init(this);
        floatingView = new FloatingView(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (sessionDepth == 0) {
                    floatingView.hideDetail();
                    floatingView.hideLogo();
                }
                sessionDepth++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (sessionDepth > 0) {
                    sessionDepth--;
                }

                if (sessionDepth == 0) {
                    floatingView.showLogo();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        //TODO: Crash组件
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }


    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceInjector;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    private  void loadSettings() {
        mSharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public String getSetting(SettingKey key, String defaultValue) {
        return mSharedPreferences.getString(key.name(), defaultValue);
    }

    public void putSetting(SettingKey key, String value) {
        mSharedPreferences.edit().putString(key.name(), value).apply();
    }

    private void checkUIThread() {
        if (!isRunOnUIThread())
            throw new RuntimeException(getResources().getString(R.string.main_thread_error));
    }

    private boolean isRunOnUIThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public void registerOnSettingChangeListener(SettingKey key, OnSettingChangeListener onSettingChangeListener) {
        checkUIThread();

        List<OnSettingChangeListener> onSettingChangeListeners;
        if (onSettingChangeListenerMap.containsKey(key)) {
            onSettingChangeListeners = onSettingChangeListenerMap.get(key);
        } else {
            onSettingChangeListeners = new ArrayList<>();
            onSettingChangeListenerMap.put(key, onSettingChangeListeners);
        }
        onSettingChangeListeners.add(onSettingChangeListener);
    }

    public void unregisterOnSettingChangeListener(SettingKey key, OnSettingChangeListener onSettingChangeListener) {
        checkUIThread();
        if (onSettingChangeListenerMap.containsKey(key)) {
            List<OnSettingChangeListener> onSettingChangeListeners = onSettingChangeListenerMap.get(key);
            onSettingChangeListeners.remove(onSettingChangeListener);
            if (onSettingChangeListeners.size() == 0) {
                onSettingChangeListenerMap.remove(key);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SettingKey settingKey = SettingKey.valueOf(SettingKey.class, key);
        List<OnSettingChangeListener> onSettingChangeListeners = onSettingChangeListenerMap.get(settingKey);
        if (onSettingChangeListeners != null) {
            for (OnSettingChangeListener onSettingChangeListener : onSettingChangeListeners) {
                onSettingChangeListener.onSettingChange(settingKey);
            }
        }
    }
}
