package org.tianjyan.luban.client;

import java.util.concurrent.LinkedBlockingQueue;

class LogTaskCache {
    private LinkedBlockingQueue<String[]> queue = new LinkedBlockingQueue<>(1000);

    LogTaskCache() {
        queue = new LinkedBlockingQueue<>(1000);
    }

    boolean offer(String[] content) {
        return queue.offer(content);
    }

    String[] take() throws InterruptedException {
        return queue.take();
    }

    void clear() {
        queue.clear();
    }
}
