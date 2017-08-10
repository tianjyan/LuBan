package org.tianjyan.luban.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tianjyan.luban.R;
import org.tianjyan.luban.bridge.UILogBridge;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogFragment extends Fragment {
    @BindView(R.id.log_rv)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log, container, false);
        ButterKnife.bind(this, rootView);
        recyclerView.setAdapter(UILogBridge.getInstance().getLogDataAdapter(getActivity()));
        return rootView;
    }
}
