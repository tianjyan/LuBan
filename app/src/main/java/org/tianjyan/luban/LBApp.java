package org.tianjyan.luban;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import org.tianjyan.luban.model.OnSettingChangeListener;
import org.tianjyan.luban.model.SettingKey;
import org.tianjyan.luban.view.FloatingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LBApp extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static boolean isAppRunning = false;
    private static Context mContext;
    private SharedPreferences mSharedPreferences;
    private Map<SettingKey, List<OnSettingChangeListener>> onSettingChangeListenerMap = new HashMap<>();
    private FloatingView floatingView;

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
        floatingView.showLogo();
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
