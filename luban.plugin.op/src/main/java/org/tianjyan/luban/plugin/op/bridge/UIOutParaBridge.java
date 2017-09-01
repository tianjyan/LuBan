package org.tianjyan.luban.plugin.op.bridge;

import android.content.Context;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.infrastructure.abs.IClient;
import org.tianjyan.luban.infrastructure.abs.IClientManager;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.abs.plugin.IOutParaPlugin;
import org.tianjyan.luban.infrastructure.common.event.AddFloatingOutParaEvent;
import org.tianjyan.luban.infrastructure.common.event.RemoveFloatingOutParaEvent;
import org.tianjyan.luban.infrastructure.common.event.SetOutParaEvent;
import org.tianjyan.luban.plugin.op.R;
import org.tianjyan.luban.plugin.op.adapter.OutParaDataAdapter;
import org.tianjyan.luban.plugin.op.event.OutParaHistoryUpdateEvent;
import org.tianjyan.luban.plugin.op.model.ParaHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UIOutParaBridge {
    private final String Floating_Area_Title;
    private final String Normal_Area_Title;
    IClientManager clientManager;
    Lazy<IOutParaPlugin> outParaPluginLazy;

    private int floatingItemCount = 0;

    private OutParaDataAdapter outParaDataAdapter;
    private List<OutPara> outParas = Collections.synchronizedList(new ArrayList<>());
    private UIOutParaHistoryBridge historyBridge;

    public UIOutParaBridge(ILBApp app, IClientManager clientManager, Lazy<IOutParaPlugin> outParaPluginLazy) {
        EventBus.getDefault().register(this);
        this.clientManager = clientManager;
        this.outParaPluginLazy = outParaPluginLazy;
        this.historyBridge = UIOutParaHistoryBridge.getInstance();
        Floating_Area_Title = app.getContext().getString(R.string.para_floating_title);
        Normal_Area_Title = app.getContext().getString(R.string.para_normal_title);
        initParamList();
    }

    public void registerOutPara(OutPara outPara) {
        Observable.just(outPara)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> {
                    switch (outPara.getDisplayProperty()) {
                        case AidlEntry.DISPLAY_FLOATING:
                            addParaToFloatingArea(val);
                            break;
                        case AidlEntry.DISPLAY_NORMAL:
                            addParaToNormalArea(val);
                            historyBridge.addOutPara(val);
                            break;
                        default:
                            break;
                    }
                    notifyChanged();
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onSetOutPara(SetOutParaEvent event) {
        setOutPara(event.getOutPara(), event.getValue());
    }

    public void setOutPara(OutPara outPara, String value) {
        if (!outParaPluginLazy.get().isGathering()) return;
        Observable.just(outPara)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> {
                    int position = outParas.indexOf(val);
                    if (position > -1) {
                        historyBridge.addHistory(val, value);
                        notifyItemChanged(position);
                        EventBus.getDefault().post(
                                new OutParaHistoryUpdateEvent(val, value));
                    }
                });
    }

    public void clientDisconnect(String pkgName) {
        Observable.just(pkgName)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> {
                    outParas.removeIf(outPara -> {
                        UIOutParaHistoryBridge.getInstance().removeHistories(outPara);
                        if (outPara.getDisplayProperty() == AidlEntry.DISPLAY_FLOATING) {
                            floatingItemCount--;
                        }
                        return outPara.getClient() != null
                                && outPara.getClient().equals(pkgName);
                    });
                    notifyChanged();
                });
    }

    public OutParaDataAdapter getOutParaDataAdapter(Context context) {
        if (outParaDataAdapter == null) {
            outParaDataAdapter = new OutParaDataAdapter(context, outParas);
        }
        return outParaDataAdapter;
    }

    public OutPara getOutPara(String paraName, String pkgName) {
        synchronized (outParas) {
            for (OutPara p : outParas) {
                if (p.getClient() != null
                        && p.getClient().equals(pkgName)
                        && p.getKey().equals(paraName))
                    return p;
            }
        }
        throw new IllegalArgumentException("Can't find the para.");
    }

    public Vector<ParaHistory> getHistories(OutPara outPara) {
        return historyBridge.getHistories(outPara);
    }

    public void clearHistories() {
        historyBridge.clearHistories();
    }

    public void saveHistories() {
        historyBridge.saveHistories();
    }

    public void moveParaFromFloatingToNormal(OutPara outPara) {
        int position = getNormalDividePosition();
        outPara.setDisplayProperty(AidlEntry.DISPLAY_NORMAL);
        outParas.remove(outPara);
        outParas.add(position, outPara);
        floatingItemCount--;
        notifyChanged();
        EventBus.getDefault().post(new RemoveFloatingOutParaEvent(outPara));
    }

    public void moveParaFromNormalToFloating(OutPara outPara) {
        int position = getNormalDividePosition();
        outPara.setDisplayProperty(AidlEntry.DISPLAY_FLOATING);
        outParas.remove(outPara);
        outParas.add(position, outPara);
        floatingItemCount++;
        notifyChanged();
        EventBus.getDefault().post(new AddFloatingOutParaEvent(outPara));
    }

    public int getFloatingItemCount() {
        return floatingItemCount;
    }

    private void initParamList() {
        List<OutPara> temps = getAll();

        outParas.clear();

        OutPara floatingPara = new OutPara();
        floatingPara.setKey(Floating_Area_Title);
        outParas.add(floatingPara);
        addParas(AidlEntry.DISPLAY_FLOATING, temps);

        OutPara normalPara = new OutPara();
        normalPara.setKey(Normal_Area_Title);
        outParas.add(normalPara);
        addParas(AidlEntry.DISPLAY_NORMAL, temps);
    }

    private void addParas(int type, List<OutPara> sources) {
        for (OutPara para: sources) {
            if (para.getDisplayProperty() == type) {
                outParas.add(para);
                if (type == AidlEntry.DISPLAY_FLOATING) {
                    EventBus.getDefault().post(new AddFloatingOutParaEvent(para));
                    floatingItemCount++;
                }
                historyBridge.addOutPara(para);
            }
        }
    }

    private void addParaToFloatingArea(OutPara outPara) {
        if (outParas.contains(outPara) ||
                outPara.getDisplayProperty() != AidlEntry.DISPLAY_FLOATING) {
            return;
        }

        int normalAreaPosition = getNormalDividePosition();

        if (normalAreaPosition <= Config.MAX_FLOATING_COUNT) {
            floatingItemCount++;
            outParas.add(normalAreaPosition, outPara);
            EventBus.getDefault().post(new AddFloatingOutParaEvent(outPara));
        } else {
            outPara.setDisplayProperty(AidlEntry.DISPLAY_NORMAL);
            addParaToNormalArea(outPara);
        }
    }

    private void addParaToNormalArea(OutPara outPara) {
        if (outParas.contains(outPara) ||
                outPara.getDisplayProperty() != AidlEntry.DISPLAY_NORMAL) {
            return;
        }

        outParas.add(outPara);
    }

    private int getNormalDividePosition() {
        return getDividePosition(Normal_Area_Title);
    }

    private List<OutPara> getAll() {
        List<OutPara> tempOutParas = new ArrayList<>();
        for (IClient client : clientManager.getAllClient()) {
            tempOutParas.addAll(client.getOutPara());
        }
        return tempOutParas;
    }

    private int getDividePosition(String title)  {
        int pos = 0;
        for (int i = 0; i < outParas.size(); i++) {
            if (outParas.get(i).getKey() == title) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    private void notifyChanged() {
        if (outParaDataAdapter != null) {
            outParaDataAdapter.notifyDataSetChanged();
        }
    }

    private void notifyItemChanged(int position) {
        if (outParaDataAdapter != null) {
            outParaDataAdapter.notifyItemChanged(position);
        }
    }
}
