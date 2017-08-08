package org.tianjyan.luban.manager;

import org.tianjyan.luban.model.LogEntry;

import java.util.concurrent.LinkedBlockingDeque;

public class LogManager {
    private Thread thread;
    private boolean flag;
    private LinkedBlockingDeque<LogEntry> queue = new LinkedBlockingDeque<>(1000);
}
