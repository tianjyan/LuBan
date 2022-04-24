package org.tianjyan.luban.host.infrastructure.abs;

import android.content.Context;

public interface ILBApp {
    Context getContext();
    String getSetting(SettingKey key, String defaultValue);
}
