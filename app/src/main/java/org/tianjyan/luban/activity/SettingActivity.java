package org.tianjyan.luban.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import org.tianjyan.luban.R;

import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.setting));
    }
}
