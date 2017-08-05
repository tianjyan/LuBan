package org.tianjyan.luban.demo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.tianjyan.luban.client.AbsLBParaLoader;
import org.tianjyan.luban.client.InParaManager;
import org.tianjyan.luban.client.LB;
import org.tianjyan.luban.client.OutParaManager;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Handler refreshHandler = new Handler();
    private Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            LB.setOutPara("Test", String.valueOf(System.currentTimeMillis()));
            refreshHandler.postDelayed(refreshRunnable, 1000);
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
