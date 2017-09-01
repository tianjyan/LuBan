package org.tianjyan.luban.infrastructure.abs.plugin;

import android.app.Fragment;

import org.tianjyan.luban.infrastructure.abs.ILBApp;

public interface IPlugin {
    String getPluginName();
    Fragment getPluginFragment();
    ILBApp getApp();
}
