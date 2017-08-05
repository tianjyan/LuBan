package org.tianjyan.luban.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tianjyan.luban.R;
import org.tianjyan.luban.bridge.UIOutParaBridge;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OutParaFragment extends Fragment {
    @BindView(R.id.para_rv) RecyclerView recyclerView;
    @BindView(R.id.action_delete) View delete;
    @BindView(R.id.action_save) View save;
    @BindView(R.id.action_start) View start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_out_para, container, false);
        ButterKnife.bind(this, rootView);
        OutParaDataAdapter adapter = UIOutParaBridge.getInstance().getOutParaDataAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        delete.setEnabled(false);
        save.setEnabled(false);
        return rootView;
    }

    @OnClick(R.id.action_delete)
    public void deleteHistories() {

    }

    @OnClick(R.id.action_save)
    public void saveHistories() {

    }

    @OnClick(R.id.action_start)
    public void start() {
        if (UIOutParaBridge.getInstance().isRunning()) {
            delete.setEnabled(true);
            save.setEnabled(true);
            UIOutParaBridge.getInstance().setRunning(false);
        } else {
            delete.setEnabled(false);
            save.setEnabled(false);
            UIOutParaBridge.getInstance().setRunning(true);
        }
    }
}
