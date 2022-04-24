package org.tianjyan.luban.host.infrastructure.log;

import org.tianjyan.luban.host.infrastructure.abs.ILBApp;
import org.tianjyan.luban.host.infrastructure.abs.ILog;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;

public class Log implements ILog{
    @Override
    public void v(String msg) {
        Logger.v(msg);
    }

    @Override
    public void d(String msg) {
        Logger.d(msg);
    }

    @Override
    public void i(String msg) {
        Logger.i(msg);
    }

    @Override
    public void w(String msg) {
        Logger.w(msg);
    }

    @Override
    public void e(String msg) {
        Logger.e(msg);
    }

    @Override
    public void json(String msg) {
        Logger.json(msg);
    }

    @Override
    public void xml(String msg) {
        Logger.xml(msg);
    }

    @Override
    public void init(ILBApp app) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.addLogAdapter(new DiskLogAdapter(TxtFormatStrategy.newBuilder().build()));
    }
}
