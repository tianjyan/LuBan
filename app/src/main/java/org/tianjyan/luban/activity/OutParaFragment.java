package org.tianjyan.luban.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobeta.android.dslv.DragSortListView;

import org.tianjyan.luban.R;
import org.tianjyan.luban.bridge.UIOutParaBridge;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutParaFragment extends Fragment implements DragSortListView.DropListener {
    @BindView(R.id.para_rv) RecyclerView recyclerView;
    @BindView(R.id.para_dsl) DragSortListView dragSortListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_out_para, container, false);
        ButterKnife.bind(this, rootView);
//        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
//        dividerLine.setSize(1);
//        dividerLine.setColor(R.color.para_line_color);
//        recyclerView.addItemDecoration(dividerLine);
//        recyclerView.setAdapter(UIOutParaBridge.getInstance().getOutParaDataAdapter(getActivity()));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dragSortListView.setAdapter(UIOutParaBridge.getInstance().getOutParaEditDataAdapter(getActivity()));
        return rootView;
    }

    @Override
    public void drop(int from, int to) {

    }
}
