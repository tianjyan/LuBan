package org.tianjyan.luban.client;

import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import org.tianjyan.luban.aidl.IService;

class LogTaskConsumer {
    Thread thread;
    private boolean flag;

    LogTaskConsumer(final IService service, final DataCacheController dataCacheController) {
        thread = new Thread(() -> {
            try {
                while (flag) {
                    String[] task = dataCacheController.takeLog();
                    if (task != null && task.length == 4) {
                        service.log(Long.parseLong(task[0]),
                                Integer.parseInt(task[1]),
                                task[2],
                                task[3]);
                    }
                }
            } catch (InterruptedException e) {
                Log.e("LogTask Interrupted", e.getMessage());
            } catch (RemoteException e) {
                Log.e("LogTask Remote", e.getMessage());
            } catch (NullPointerException e) {
                Log.e("Disconnected", e.getMessage());
                flag = false;
            }
        }, LogTaskConsumer.this.getClass().getSimpleName());
    }

    void start() {
        flag = true;
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    void stop(final DataCacheController dataCacheController) {
        flag = false;
        long tid = Process.myTid();
        String[] content = new String[1];
        content[0] = String.valueOf(tid);
        dataCacheController.putLog(content);
        thread = null;
    }
}
