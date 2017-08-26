package org.tianjyan.luban.host.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.tianjyan.luban.host.LBApp;
import org.tianjyan.luban.host.R;
import org.tianjyan.luban.host.model.OnFunctionSelected;
import org.tianjyan.luban.infrastructure.abs.SettingKey;

import java.util.List;

public class MainMenuFragment extends Fragment implements AdapterView.OnItemClickListener {
    View exitView;
    ListView listView;
    MainMenuFragmentAdapter adapter;
    OnFunctionSelected onFunctionSelected;
    List<String> functions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MainMenuFragmentAdapter(getActivity());
        adapter.setData(functions);
        adapter.setCurrentFunction(functions.get(0));
        if (onFunctionSelected != null) {
            onFunctionSelected.onFunctionSelected(functions.get(0));
        }
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

    public void setDataSource(List<String> source) {
        this.functions = source;
    }

    public void  setOnFunctionSelected(OnFunctionSelected onFunctionSelected) {
        this.onFunctionSelected = onFunctionSelected;
    }
}
