package org.tianjyan.luban.host.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import org.tianjyan.luban.host.R;
import org.tianjyan.luban.infrastructure.abs.IPlugin;

import javax.inject.Inject;
import javax.inject.Named;

public class SplashActivity extends AppCompatActivity {
    @Inject
    @Named("OutParaPlugin")
    IPlugin outParaPlugin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    }
}
