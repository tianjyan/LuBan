package org.tianjyan.luban.sampleclient;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.tianjyan.luban.sdk.AbsLBParaLoader;
import org.tianjyan.luban.sdk.InParaManager;
import org.tianjyan.luban.sdk.LB;
import org.tianjyan.luban.sdk.OutParaManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Handler refreshHandler = new Handler();
    private Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            LB.setOutPara("Test", String.valueOf(System.currentTimeMillis()));
            LB.logD("Test", String.valueOf(System.currentTimeMillis()));
            LB.logI("Test", String.valueOf(System.currentTimeMillis()));
            LB.logW("Test", String.valueOf(System.currentTimeMillis()));
            LB.logE("Test", String.valueOf(System.currentTimeMillis()));
            LB.logD("NewOne", String.valueOf(System.currentTimeMillis()));
            LB.logI("NewOne", String.valueOf(System.currentTimeMillis()));
            LB.logW("NewOne", String.valueOf(System.currentTimeMillis()));
            LB.logE("NewOne", String.valueOf(System.currentTimeMillis()));
            refreshHandler.postDelayed(refreshRunnable, 3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.connectBtn).setOnClickListener(this);
        findViewById(R.id.disconnectBtn).setOnClickListener(this);
        findViewById(R.id.transportBtn).setOnClickListener(this);
        refreshHandler.post(refreshRunnable);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.connectBtn:
                LB.connect(getApplicationContext(), new AbsLBParaLoader() {
                    @Override
                    public void loadInParas(InParaManager im) {
                        im.register("Value1", "Default", "abc");
                        im.register("Value2", "Default2", "bcd");
                    }

                    @Override
                    public void loadOutParas(OutParaManager om) {
                        om.register("Test");
                        om.register("Test2");
                        om.register("Test3");
                    }
                });
                break;
            case R.id.disconnectBtn:
                LB.disconnect();
                break;
            case R.id.transportBtn:
                LB.setOutPara("Test", "123");
                break;
            default:
                break;
        }
    }
}