package org.tianjyan.luban.activity;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.tianjyan.luban.LBApp;
import org.tianjyan.luban.R;
import org.tianjyan.luban.model.OnFunctionSelected;
import org.tianjyan.luban.model.SettingKey;

import java.util.ArrayList;
import java.util.List;

public class MainMenuFragment extends Fragment implements AdapterView.OnItemClickListener {
    View exitView;
    ListView listView;
    List<String> functions;
    MainMenuFragmentAdapter adapter;
    OnFunctionSelected onFunctionSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        functions = new ArrayList<>();
        functions.add(getString(R.string.function_out_para));
        functions.add(getString(R.string.function_in_para));
        adapter = new MainMenuFragmentAdapter(getActivity());
        adapter.setData(functions);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        exitView = rootView.findViewById(R.id.exit_view);
        listView = (ListView) rootView.findViewById(R.id.function_lv);
        exitView.setOnClickListener(v -> {
            LBApp.exit();
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String function = adapter.getItem(position);
        ((BaseActivity) getActivity()).putSetting(SettingKey.LAST_SHOW_ITEM, function);
        adapter.setCurrentFunction(function);
        if (onFunctionSelected != null) {
            onFunctionSelected.onFunctionSelected(function);
        }
    }

    public void  setOnFunctionSelected(OnFunctionSelected onFunctionSelected) {
        this.onFunctionSelected = onFunctionSelected;
    }
}
