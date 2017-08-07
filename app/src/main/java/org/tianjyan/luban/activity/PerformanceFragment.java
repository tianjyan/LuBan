package org.tianjyan.luban.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tianjyan.luban.R;

import butterknife.ButterKnife;

public class PerformanceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_performance, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
