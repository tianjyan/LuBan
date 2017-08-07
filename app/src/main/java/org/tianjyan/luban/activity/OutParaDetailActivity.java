package org.tianjyan.luban.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.tianjyan.luban.R;
import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.bridge.UIOutParaBridge;
import org.tianjyan.luban.event.OutParaHistoryUpdateEvent;
import org.tianjyan.luban.model.ParaHistory;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutParaDetailActivity extends BaseActivity {
    @BindView(R.id.para_rv) RecyclerView recyclerView;
    OutPara outPara;
    OutParaDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_para_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        String paraName = getIntent().getStringExtra("paraName");
        String pkgName = getIntent().getStringExtra("pkgName");
        outPara = UIOutParaBridge.getInstance().getOutPara(paraName, pkgName);
        Vector<ParaHistory> histories = UIOutParaBridge.getInstance().getHistories(outPara);
        adapter = new OutParaDetailAdapter(this, histories);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setTitle(String.format("%s -- %s", pkgName, paraName));
        actionBar.setTitle(paraName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!UIOutParaBridge.getInstance().isRunning()) {
            if (outPara.getDisplayProperty() == AidlEntry.DISPLAY_FLOATING ||
                    UIOutParaBridge.getInstance().getFloatingItemCount() < Config.MAX_FLOATING_COUNT) {
                getMenuInflater().inflate(R.menu.para_detail, menu);
                int resId = outPara.getDisplayProperty() == AidlEntry.DISPLAY_FLOATING
                        ? R.string.menu_move_out_floating
                        : R.string.menu_move_to_floating;
                menu.findItem(R.id.move).setTitle(getString(resId));
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.move) {
            if (outPara.getDisplayProperty() == AidlEntry.DISPLAY_FLOATING) {
                UIOutParaBridge.getInstance().moveParaFromFloatingToNormal(outPara);
            } else {
                UIOutParaBridge.getInstance().moveParaFromNormalToFloating(outPara);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOutParaHistoryUpdated(OutParaHistoryUpdateEvent event) {
        if (event.getOutPara() == outPara) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
