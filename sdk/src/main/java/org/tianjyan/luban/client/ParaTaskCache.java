package org.tianjyan.luban.client;

import org.tianjyan.luban.aidl.AidlTask;

import java.util.concurrent.LinkedBlockingQueue;

class ParaTaskCache {
    private LinkedBlockingQueue<AidlTask> queue;

    ParaTaskCache() {
        queue = new LinkedBlockingQueue<>();
    }

    void clear() {
        queue.clear();
    }

    void add(AidlTask task) {
        queue.offer(task);
    }

    AidlTask take() throws InterruptedException {
        return queue.take();
    }
}
