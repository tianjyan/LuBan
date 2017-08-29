package org.tianjyan.luban.plugin.ip.activity;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tianjyan.luban.infrastructure.common.consts.AliasName;
import org.tianjyan.luban.plugin.ip.R2;
import org.tianjyan.luban.plugin.ip.R;
import org.tianjyan.luban.plugin.ip.adapter.InParaDataAdapter;
import org.tianjyan.luban.plugin.ip.bridge.UIInParaBridge;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class InParaFragment extends Fragment {
    @Inject @Named(AliasName.IN_PARA_BRIDGE) UIInParaBridge inParaBridge;
    @BindView(R2.id.para_rv) RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_in_para, container, false);
        ButterKnife.bind(this, rootView);
        InParaDataAdapter adapter = inParaBridge.getInParaDataAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        AndroidInjection.inject(this);
        super.onAttach(activity);
    }
}
