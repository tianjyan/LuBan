package org.tianjyan.luban.infrastructure.common;

import org.tianjyan.luban.infrastructure.abs.ILBApp;

public class Common {
    public static ILBApp app;
    public static void init(ILBApp ilbApp) {
        app = ilbApp;
    }
}
