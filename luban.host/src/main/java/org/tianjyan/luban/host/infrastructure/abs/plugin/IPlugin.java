package org.tianjyan.luban.host.infrastructure.abs.plugin;

import androidx.fragment.app.Fragment;

public interface IPlugin {
    String getPluginName();
    Fragment getPluginFragment();
}
