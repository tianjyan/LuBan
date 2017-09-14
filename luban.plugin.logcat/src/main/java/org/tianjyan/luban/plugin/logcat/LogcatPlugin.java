package org.tianjyan.luban.plugin.logcat;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.plugin.ILogcatPlugin;
import org.tianjyan.luban.plugin.common.Utils;

public class LogcatPlugin implements ILogcatPlugin {
    private Context context;

    public LogcatPlugin(ILBApp app) {
        Intent intent = new Intent(app.getContext(), FCService.class);
        context = app.getContext();
        context.startService(intent);
    }

    @Override
    public String getPluginName() {
        return Utils.getString(R.string.function_crash);
    }

    @Override
    public Fragment getPluginFragment() {
        return new Fragment();
    }

    @Override
    public void stopService() {
        context.stopService(new Intent(context, FCService.class));
    }
}
