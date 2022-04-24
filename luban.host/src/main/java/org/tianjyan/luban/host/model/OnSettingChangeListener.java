package org.tianjyan.luban.host.model;

import org.tianjyan.luban.host.infrastructure.abs.SettingKey;

public interface OnSettingChangeListener {
    void onSettingChange(SettingKey key);
}
