package org.tianjyan.luban.host.plugin.logcat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import org.tianjyan.luban.host.R;

import org.tianjyan.luban.host.infrastructure.abs.ui.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CrashDetailActivity extends AbsActivity{
    @BindView(R.id.crash_detail)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_detail);
        ButterKnife.bind(this);
        onNewIntent(getIntent());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        String packageName = bundle.getString("packageName");
        String content = bundle.getString("content");
        textView.setText(content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
