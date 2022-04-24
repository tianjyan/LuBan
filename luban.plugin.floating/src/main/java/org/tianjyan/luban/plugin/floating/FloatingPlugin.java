package org.tianjyan.luban.plugin.floating;

import android.content.Context;

import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.infrastructure.abs.plugin.IFloatingPlugin;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.plugin.floating.view.FloatingView;

public class FloatingPlugin implements IFloatingPlugin {
    private final FloatingView floatingView;

    public FloatingPlugin(Context context) {
        floatingView = new FloatingView(context);
    }

    @Override
    public void outParaValueUpdate(OutPara outPara, String value) {
        floatingView.outParaValueUpdate(outPara, value);
    }

    @Override
    public void clientDisconnect(String pkgName) {
        floatingView.clientDisconnect(pkgName);
    }

    @Override
    public void showLogo() {
        floatingView.showLogo();
    }

    @Override
    public void hideLogo() {
        floatingView.hideLogo();
    }

    @Override
    public void showDetail() {
        floatingView.showDetail();
    }

    @Override
    public void hideDetail() {
        floatingView.hideDetail();
    }
}
