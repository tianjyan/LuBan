package org.tianjyan.luban;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import org.tianjyan.luban.model.OnSettingChangeListener;
import org.tianjyan.luban.model.SettingKey;
import org.tianjyan.luban.utils.CrashUtils;
import org.tianjyan.luban.view.FloatingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LBApp extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static boolean isAppRunning = false;
    private static boolean isGathering = false;
    private static Context mContext;
    private static int sessionDepth = 0;
    private SharedPreferences mSharedPreferences;
    private Map<SettingKey, List<OnSettingChangeListener>> onSettingChangeListenerMap = new HashMap<>();
    private FloatingView floatingView;

    public static void setAppRunning(boolean isRunning) {
        isAppRunning = isRunning;
    }

    public static boolean isAppRunning() {
        return isAppRunning;
    }

    public static boolean isGathering() {
        return isGathering;
    }

    public static void setIsGathering(boolean isGathering) {
        LBApp.isGathering = isGathering;
    }

    public static void exit() {
        LBEntrance.close(mContext);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        if (BuildConfig.ENABLE_LEAK_CANARY) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
        mContext = getApplicationContext();
        loadSettings();
        final String key = getSetting(SettingKey.KEY, "");
        floatingView = new FloatingView(LBApp.getContext());
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
        CrashUtils.init();
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

    public static Context getContext() {
        return mContext;
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
