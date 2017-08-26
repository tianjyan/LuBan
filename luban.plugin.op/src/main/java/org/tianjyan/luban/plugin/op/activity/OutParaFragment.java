package org.tianjyan.luban.plugin.op.activity;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tianjyan.luban.infrastructure.abs.IOutParaPlugin;
import org.tianjyan.luban.infrastructure.common.consts.AliasName;
import org.tianjyan.luban.plugin.op.R;
import org.tianjyan.luban.plugin.op.R2;
import org.tianjyan.luban.plugin.op.adapter.OutParaDataAdapter;
import org.tianjyan.luban.plugin.op.bridge.UIOutParaBridge;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class OutParaFragment extends Fragment {
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    @BindView(R2.id.para_rv) RecyclerView recyclerView;
    @BindView(R2.id.action_delete) View delete;
    @BindView(R2.id.action_save) View save;
    @BindView(R2.id.action_start) View start;
    @BindView(R2.id.action_delete_tv) TextView deleteTV;
    @BindView(R2.id.action_save_tv) TextView saveTV;
    @BindView(R2.id.action_start_tv) TextView startTV;
    @Inject
    @Named(AliasName.OUT_PARA_BRIDGE)
    UIOutParaBridge outParaBridge;
    @Inject @Named(AliasName.OUT_PARA_PLUGIN)
    IOutParaPlugin outParaPlugin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_out_para, container, false);
        ButterKnife.bind(this, rootView);
        OutParaDataAdapter adapter = outParaBridge.getOutParaDataAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setToolState(outParaPlugin.isGathering());
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    @OnClick(R2.id.action_delete)
    public void deleteHistories() {
        outParaBridge.clearHistories();
    }

    @OnClick(R2.id.action_save)
    public void saveHistories() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            outParaBridge.saveHistories();
        }
    }

    @OnClick(R2.id.action_start)
    public void start() {
        outParaPlugin.setIsGathering(!outParaPlugin.isGathering());
        setToolState(outParaPlugin.isGathering());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                outParaBridge.saveHistories();
            }
        }
    }

    private void setToolState(boolean isRunning) {
        if (isRunning) {
            delete.setEnabled(false);
            save.setEnabled(false);
            deleteTV.setAlpha(0.5f);
            saveTV.setAlpha(0.5f);
            startTV.setTextColor(getResources().getColor(R.color.action_item_dark_red));
            startTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_pause), null, null, null);
            startTV.setText(getResources().getText(R.string.stop));
        } else {
            delete.setEnabled(true);
            save.setEnabled(true);
            deleteTV.setAlpha(1f);
            saveTV.setAlpha(1f);
            startTV.setTextColor(getResources().getColor(R.color.action_item_green));
            startTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_play), null, null, null);
            startTV.setText(getResources().getText(R.string.start));
        }
    }
}
