package org.tianjyan.luban.host.plugin.log;

import androidx.fragment.app.Fragment;

import org.tianjyan.luban.host.R;
import org.tianjyan.luban.host.infrastructure.abs.plugin.ILogPlugin;
import org.tianjyan.luban.host.plugin.common.Utils;
import org.tianjyan.luban.host.plugin.log.activity.LogFragment;
import org.tianjyan.luban.host.plugin.log.manager.LogManager;

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
