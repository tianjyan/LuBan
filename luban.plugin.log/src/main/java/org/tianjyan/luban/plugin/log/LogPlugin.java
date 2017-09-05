package org.tianjyan.luban.plugin.log;

import android.app.Fragment;

import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.plugin.ILogPlugin;
import org.tianjyan.luban.plugin.common.Utils;
import org.tianjyan.luban.plugin.log.activity.LogFragment;
import org.tianjyan.luban.plugin.log.manager.LogManager;

import dagger.Lazy;

public class LogPlugin implements ILogPlugin {
    private final Lazy<LogManager> logManagerLazy;

    public LogPlugin(Lazy<LogManager> logManagerLazy) {
        this.logManagerLazy = logManagerLazy;
    }

    @Override
    public void log(long tid, int level, String tag, String msg) {
        logManagerLazy.get().log(tid, level, tag, msg);
    }

    @Override
    public String getPluginName() {
        return Utils.getString(R.string.function_log);
    }

    @Override
    public Fragment getPluginFragment() {
        return new LogFragment();
    }
}
