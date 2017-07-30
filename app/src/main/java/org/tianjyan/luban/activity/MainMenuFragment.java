package org.tianjyan.luban.activity;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tianjyan.luban.LBApp;
import org.tianjyan.luban.R;

public class MainMenuFragment extends Fragment {
    View exitView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        exitView = rootView.findViewById(R.id.exit_view);
        exitView.setOnClickListener(v -> {
            LBApp.exit();
        });
        return rootView;
    }
}
