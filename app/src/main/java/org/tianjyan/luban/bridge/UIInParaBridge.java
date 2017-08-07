package org.tianjyan.luban.bridge;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.tianjyan.luban.activity.InParaDataAdapter;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.event.RegisterInParaEvent;
import org.tianjyan.luban.event.SetInParaEvent;
import org.tianjyan.luban.manager.ClientManager;
import org.tianjyan.luban.manager.IClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UIInParaBridge {
    private static UIInParaBridge INSTANCE = new UIInParaBridge();
    public static UIInParaBridge getInstance() {
        return INSTANCE;
    }

    private List<InPara> inParas = Collections.synchronizedList(new ArrayList<>());
    private InParaDataAdapter inParaDataAdapter;

    public UIInParaBridge() {
        EventBus.getDefault().register(this);
        initParamList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterInPara(RegisterInParaEvent event) {
        if (!inParas.contains(event.getInPara())) {
            inParas.add(event.getInPara());
            inParaDataAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSetInPara(SetInParaEvent event) {
        if (inParas.contains(event.getInPara())) {
            int position = inParas.indexOf(event.getInPara());
            inParaDataAdapter.notifyItemChanged(position);
        }
    }

    public InParaDataAdapter getInParaDataAdapter(Context context) {
        if (inParaDataAdapter == null) {
            inParaDataAdapter = new InParaDataAdapter(context, inParas);
        }
        return inParaDataAdapter;
    }

    public InPara getInPara(String paraName, String pkgName) {
        synchronized (inParas) {
            for (InPara p : inParas) {
                if (p.getClient() != null
                        && p.getClient().equals(pkgName)
                        && p.getKey().equals(paraName))
                    return p;
            }
        }
        throw new IllegalArgumentException("Can't find the para.");
    }

    private void initParamList() {
        List<InPara> temps = getAll();
        inParas.addAll(temps);

    }

    private List<InPara> getAll() {
        List<InPara> tempInParas = new ArrayList<>();
        for (IClient client : ClientManager.getInstance().getAllClient()) {
            tempInParas.addAll(client.getInPara());
        }
        return tempInParas;
    }
}
