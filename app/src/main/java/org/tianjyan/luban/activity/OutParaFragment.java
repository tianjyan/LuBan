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

public class OutParaFragment extends Fragment {
    @BindView(R.id.para_rv) RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_out_para, container, false);
        ButterKnife.bind(this, rootView);
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(1);
        dividerLine.setColor(R.color.para_line_color);
        OutParaDataAdapter adapter = UIOutParaBridge.getInstance().getOutParaDataAdapter(getActivity());
        recyclerView.addItemDecoration(dividerLine);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }
}
