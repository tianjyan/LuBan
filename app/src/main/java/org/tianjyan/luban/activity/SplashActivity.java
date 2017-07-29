package org.tianjyan.luban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.tianjyan.luban.LBApp;
import org.tianjyan.luban.LBEntrance;
import org.tianjyan.luban.R;

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LBApp.isAppRunning()) {
            if (!MainActivity.isActive()) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
            finish();
        } else {
            setContentView(R.layout.splash);
            LBEntrance.open(getApplicationContext());
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }, 2000);
        }
    }
}
