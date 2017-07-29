package org.tianjyan.luban.activity;

import android.os.Bundle;

import org.tianjyan.luban.R;

public class MainActivity extends BaseActivity {
    private static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        active = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    public static boolean isActive() {
        return active;
    }
}
