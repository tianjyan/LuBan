package org.tianjyan.luban.client;

import android.content.Context;

public class LB {
    private static boolean enable = true;

    public static void setEnable(boolean flag) {
        enable = flag;
    }

    public static boolean isEnable() {
        return enable;
    }

    public static boolean connect(Context hostContext, AbsLBParaLoader loader) {
        if (!enable) {
            return false;
        }
        return LBInternal.getInstance().connect(hostContext, loader);
    }

    public static boolean connect(Context hostContext) {
        if (!enable) {
            return false;
        }
        return LBInternal.getInstance().connect(hostContext, new AbsLBParaLoader() {

            @Override
            public void loadInParas(InParaManager im) {

            }

            @Override
            public void loadOutParas(OutParaManager om) {

            }
        });
    }

    public static void disconnect(Context hostContext) {
        if (!enable) {
            return;
        }
        LBInternal.getInstance().disconnect(hostContext);
    }

    public static void setOutPara(String paraName, String value) {
        LBInternal.getInstance().setOutPara(paraName, value);
    }

    public static void setInPara(String paraName, String value) {
        LBInternal.getInstance().setInPara(paraName, value);
    }

    public static void getInPara(String paraName) {
        LBInternal.getInstance().getInPara(paraName);
    }
}
