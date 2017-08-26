package org.tianjyan.luban.infrastructure.client;

import android.net.wifi.WifiManager;

import org.greenrobot.eventbus.EventBus;
import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.common.event.SetOutParaEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static android.content.Context.WIFI_SERVICE;

class DefaultClient extends AbsClient {

    private WifiManager wifiManager;

    public DefaultClient(ILBApp app) {
        super(app.getContext().getString(R.string.client_default));
        wifiManager = (WifiManager) app.getContext()
                .getApplicationContext()
                .getSystemService(WIFI_SERVICE);

        inParaManager = new DefaultInParaManager(this);
        outParaManager = new DefaultOutParaManager(this);

        OutPara wifiPara = new OutPara();
        wifiPara.setKey("WIFI");
        wifiPara.setValue("");
        wifiPara.setDisplayProperty(AidlEntry.DISPLAY_FLOATING);
        outParaManager.register(wifiPara);

        Observable.interval(5, TimeUnit.SECONDS).subscribe(aLong -> {
            String wifiInfo  = getWifiInfo();
            wifiPara.setValue(wifiInfo);
            EventBus.getDefault().post(new SetOutParaEvent(wifiPara, wifiInfo));
        });
    }

    private String getWifiInfo() {
        return String.format("SSID: %s, RSSI: %s",
                wifiManager.getConnectionInfo().getSSID(),
                wifiManager.getConnectionInfo().getRssi());
    }
}
