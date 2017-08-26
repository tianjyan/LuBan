package org.tianjyan.luban.infrastructure.abs;

import android.content.Context;

public interface ILBApp {
    void inject(Object object);
    Context getContext();
}
