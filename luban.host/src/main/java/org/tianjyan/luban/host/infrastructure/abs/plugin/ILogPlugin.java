package org.tianjyan.luban.host.infrastructure.abs.plugin;

public interface ILogPlugin extends IPlugin {
    void log(long tid, int level, String tag, String msg);
}
