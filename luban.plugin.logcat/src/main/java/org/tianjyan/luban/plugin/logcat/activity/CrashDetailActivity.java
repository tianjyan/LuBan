package org.tianjyan.luban.plugin.logcat.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import org.tianjyan.luban.infrastructure.abs.ui.AbsActivity;
import org.tianjyan.luban.plugin.logcat.R;

import dagger.android.AndroidInjection;

public class CrashDetailActivity extends AbsActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_detail);
        // ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
