package org.tianjyan.luban.sdk;

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

    public static void disconnect() {
        if (!enable) {
            return;
        }
        LBInternal.getInstance().disconnect();
    }

    public static void setOutPara(String paraName, String value) {
        if (!enable) {
            return;
        }
        LBInternal.getInstance().setOutPara(paraName, value);
    }

    public static void getInPara(String paraName) {
        if (!enable) {
            return;
        }
        LBInternal.getInstance().getInPara(paraName);
    }

    public static void logI(String tag, String msg) {
        if (!enable) {
            return;
        }
        LBInternal.getInstance().logI(tag, msg);
    }

    public static void logD(String tag, String msg) {
        if (!enable) {
            return;
        }
        LBInternal.getInstance().logD(tag, msg);
    }

    public static void logW(String tag, String msg) {
        if (!enable) {
            return;
        }
        LBInternal.getInstance().logW(tag, msg);
    }

   public static void logE(String tag, String msg) {
        if (!enable) {
            return;
        }
        LBInternal.getInstance().logE(tag, msg);
    }
}
