package org.tianjyan.luban.infrastructure.abs.plugin;

import androidx.fragment.app.Fragment;

public interface IPlugin {
    String getPluginName();
    Fragment getPluginFragment();
}
