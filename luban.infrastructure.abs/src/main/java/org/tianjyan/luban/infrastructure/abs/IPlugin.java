package org.tianjyan.luban.infrastructure.abs;

import android.app.Fragment;

public interface IPlugin {
    String getPluginName();
    Fragment getPluginFragment();
}
