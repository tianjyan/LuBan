package org.tianjyan.luban.host.activity;

import android.support.v7.app.AppCompatActivity;

import org.tianjyan.luban.host.LBApp;
import org.tianjyan.luban.host.model.SettingKey;

public class BaseActivity extends AppCompatActivity {
    private LBApp getApp() {
        return (LBApp) getApplication();
    }

    public String getSetting(SettingKey key, String defaultValue) {
        return getApp().getSetting(key, defaultValue);
    }

    public void putSetting(SettingKey key, String value) {
        getApp().putSetting(key, value);
    }
}
