package org.tianjyan.luban.plugin.logcat;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import org.tianjyan.luban.infrastructure.abs.plugin.ILogcatPlugin;
import org.tianjyan.luban.plugin.common.Utils;

public class LogcatPlugin implements ILogcatPlugin {
    private Context context;

    public LogcatPlugin(Context context) {
        Intent intent = new Intent(context, FCService.class);
        this.context = context;
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
