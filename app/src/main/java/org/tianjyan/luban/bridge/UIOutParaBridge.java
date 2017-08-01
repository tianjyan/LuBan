package org.tianjyan.luban.bridge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.event.RegisterInParaEvent;
import org.tianjyan.luban.event.RegisterOutParaEvent;
import org.tianjyan.luban.event.SetOutParaEvent;
import org.tianjyan.luban.manager.ClientManager;
import org.tianjyan.luban.manager.IClient;
import org.tianjyan.luban.model.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UIOutParaBridge {
    private static UIOutParaBridge INSTANCE = new UIOutParaBridge();
    public static UIOutParaBridge getInstance() {
        return INSTANCE;
    }

    public UIOutParaBridge() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onRegisterInPara(RegisterInParaEvent event){

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onRegisterOutPara(RegisterOutParaEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onSetOutPara(SetOutParaEvent event) {

    }

    public static List<OutPara> outParas = Collections.synchronizedList(new ArrayList<>());

    public static void addParaToFloatingArea(OutPara outPara) {
        if (outParas.contains(outPara) ||
                outPara.getDisplayProperty() != AidlEntry.DISPLAY_FLOATING) {
            return;
        }

        int normalAreaPosition = getNormalDividePosition();

        if (normalAreaPosition < 4) {
            outParas.add(normalAreaPosition, outPara);
        } else {
            outPara.setDisplayProperty(AidlEntry.DISPLAY_NORMAL);
        }
    }

    public static int getNormalDividePosition() {
        return getDividePosition(Const.Normal_Area_Title);
    }

    public static int getDisableDividePosition() {
        return getDividePosition(Const.Disable_Area_Title);
    }

    private static List<OutPara> getAll() {
        List<OutPara> tempOutParas = new ArrayList<>();
        for (IClient client : ClientManager.getInstance().getAllClient()) {
            tempOutParas.addAll(client.getOutPara());
        }
        return tempOutParas;
    }

    private static int getDividePosition(String title) {
        int pos = 0;
        for (int i = 0; i < outParas.size(); i++) {
            if (outParas.get(i).getKey() == title) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}
