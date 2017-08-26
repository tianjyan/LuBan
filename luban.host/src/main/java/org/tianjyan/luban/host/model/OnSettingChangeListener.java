package org.tianjyan.luban.host.model;

import org.tianjyan.luban.infrastructure.abs.SettingKey;

public interface OnSettingChangeListener {
    void onSettingChange(SettingKey key);
}
