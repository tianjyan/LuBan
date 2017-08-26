package org.tianjyan.luban.plugin.ip.bridge;

import android.content.Context;

import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.infrastructure.abs.IClient;
import org.tianjyan.luban.infrastructure.abs.IClientManager;
import org.tianjyan.luban.plugin.ip.adapter.InParaDataAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UIInParaBridge {
    private List<InPara> inParas = Collections.synchronizedList(new ArrayList<>());
    private InParaDataAdapter inParaDataAdapter;
    private IClientManager clientManager;

    public UIInParaBridge(IClientManager clientManager) {
        this.clientManager = clientManager;
        initParamList();
    }

    public void registerInPara(InPara inPara) {
        Observable.just(inPara)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(val-> {
                    if (!inParas.contains(val)) {
                        inParas.add(val);
                        notifyChanged();
                    }
                });
    }

    public void setInPara(InPara inPara) {
        Observable.just(inPara)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(val-> {
                    if (inParas.contains(val)) {
                        int position = inParas.indexOf(val);
                        notifyItemChanged(position);
                    }
                });
    }

    public void clientDisConnect(String pkgName) {
        Observable.just(pkgName)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(val-> {
                    inParas.removeIf(inPara -> inPara.getClient().equals(val));
                    notifyChanged();
                });
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
        for (IClient client : clientManager.getAllClient()) {
            tempInParas.addAll(client.getInPara());
        }
        return tempInParas;
    }

    private void notifyChanged() {
        if (inParaDataAdapter != null) {
            inParaDataAdapter.notifyDataSetChanged();
        }
    }

    private void notifyItemChanged(int position) {
        if (inParaDataAdapter != null) {
            inParaDataAdapter.notifyItemChanged(position);
        }
    }
}
