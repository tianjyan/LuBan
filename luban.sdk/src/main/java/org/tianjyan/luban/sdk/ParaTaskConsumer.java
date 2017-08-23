package org.tianjyan.luban.sdk;

import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import org.tianjyan.luban.aidl.AidlTask;
import org.tianjyan.luban.aidl.IService;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;

class ParaTaskConsumer {
    private Thread thread;
    private boolean flag;

    ParaTaskConsumer(final IService service, final DataCacheController dataCacheController) {
        thread = new Thread(() -> {
            try {
                while (flag) {
                    AidlTask task = dataCacheController.takeParaTask();
                    if (task == null) continue;
                    if (task instanceof InPara) {
                        InPara inPara = (InPara) task;
                        service.registerInPara(inPara);
                    } else if (task instanceof OutPara) {
                        OutPara outPara = (OutPara) task;
                        if (outPara.isRegistering()) {
                            service.registerOutPara(outPara);
                        } else if (outPara.getValue() != null) {
                            service.setOutPara(outPara.getKey(), outPara.getValue());
                        }
                    }
                }
            } catch (InterruptedException e) {
                Log.e("ParaTask Interrupted", e.getMessage());
            } catch (RemoteException e) {
                Log.e("ParaTask Remote", e.getMessage());
            } catch (NullPointerException e) {
                Log.e("Disconnected", e.getMessage());
                flag = false;
            }
        }, ParaTaskConsumer.this.getClass().getSimpleName());
    }

    void start() {
        flag = true;
        thread.setPriority(Thread.MIN_PRIORITY + 2);
        thread.start();
    }

    void stop(final DataCacheController dataCacheController) {
        flag = false;
        dataCacheController.putParaTask(new AidlTask() {
            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {

            }
        });
    }
}
