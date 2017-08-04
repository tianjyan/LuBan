package org.tianjyan.luban.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.tianjyan.luban.R;
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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        String paraName = getIntent().getStringExtra("paraName");
        String pkgName = getIntent().getStringExtra("pkgName");
        outPara = UIOutParaBridge.getInstance().getOutPara(paraName, pkgName);
        Vector<ParaHistory> histories = UIOutParaBridge.getInstance().getHistories(outPara);
        adapter = new OutParaDetailAdapter(this, histories);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
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
