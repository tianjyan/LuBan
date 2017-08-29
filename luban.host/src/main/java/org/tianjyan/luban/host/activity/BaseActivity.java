package org.tianjyan.luban.host.activity;

import org.tianjyan.luban.host.LBApp;
import org.tianjyan.luban.infrastructure.abs.AbsActivity;
import org.tianjyan.luban.infrastructure.abs.SettingKey;

public class BaseActivity extends AbsActivity {
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
