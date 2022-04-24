package org.tianjyan.luban.host.plugin.op.activity;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.tianjyan.luban.host.R;
import org.tianjyan.luban.host.infrastructure.abs.ui.AbsFragment;
import org.tianjyan.luban.host.infrastructure.abs.plugin.IOutParaPlugin;
import org.tianjyan.luban.host.plugin.common.AliasName;
import org.tianjyan.luban.host.plugin.op.adapter.OutParaDataAdapter;
import org.tianjyan.luban.host.plugin.op.bridge.UIOutParaBridge;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OutParaFragment extends AbsFragment {
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    @BindView(R.id.para_rv)
    RecyclerView recyclerView;
    @BindView(R.id.action_delete) View delete;
    @BindView(R.id.action_save) View save;
    @BindView(R.id.action_start) View start;
    @BindView(R.id.action_delete_tv) TextView deleteTV;
    @BindView(R.id.action_save_tv) TextView saveTV;
    @BindView(R.id.action_start_tv) TextView startTV;
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

    @OnClick(R.id.action_delete)
    public void deleteHistories() {
        outParaBridge.clearHistories();
    }

    @OnClick(R.id.action_save)
    public void saveHistories() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            outParaBridge.saveHistories();
        }
    }

    @OnClick(R.id.action_start)
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
