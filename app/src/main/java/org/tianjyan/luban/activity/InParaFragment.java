package org.tianjyan.luban.activity;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tianjyan.luban.R;
import org.tianjyan.luban.bridge.UIInParaBridge;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InParaFragment extends Fragment {
    @BindView(R.id.para_rv)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_in_para, container, false);
        ButterKnife.bind(this, rootView);
        InParaDataAdapter adapter = UIInParaBridge.getInstance().getInParaDataAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }
}
