package org.tianjyan.luban.infrastructure.abs.plugin;

public interface ILogPlugin extends IPlugin {
    void log(long tid, int level, String tag, String msg);
}
