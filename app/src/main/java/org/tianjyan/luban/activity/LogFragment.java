package org.tianjyan.luban.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.tianjyan.luban.R;
import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.bridge.UILogBridge;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogFragment extends Fragment implements TextWatcher, View.OnClickListener {
    @BindView(R.id.log_rv) RecyclerView recyclerView;
    @BindView(R.id.filter_rv) RecyclerView filterRecyclerView;
    @BindView(R.id.filter_msg_et) EditText filterMsgET;
    @BindView(R.id.filter_level_btn) Button filterLevelBtn;
    @BindView(R.id.filter_tag_btn) Button filterTagBtn;

    LogFilterAdapter levelAdapter;
    LogFilterAdapter tagAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log, container, false);
        ButterKnife.bind(this, rootView);
        filterMsgET.addTextChangedListener(this);
        recyclerView.setAdapter(UILogBridge.getInstance().getLogDataAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        filterLevelBtn.setText(Config.VERBOSE);
        filterTagBtn.setText(Config.TAG);
        levelAdapter = new LogFilterAdapter(getActivity(), UILogBridge.getInstance().getLevels(), this);
        return rootView;
    }

    @OnClick(R.id.filter_level_btn)
    public void onLevelClick() {
        filterTagBtn.setSelected(false);
        if (filterLevelBtn.isSelected()) {
            filterLevelBtn.setSelected(false);
            filterRecyclerView.setVisibility(View.GONE);
        } else {
            filterLevelBtn.setSelected(true);
            filterRecyclerView.setVisibility(View.VISIBLE);
            filterRecyclerView.setAdapter(levelAdapter);
        }
    }

    @OnClick(R.id.filter_tag_btn)
    public void onTagClick() {
        filterLevelBtn.setSelected(false);
        if (filterTagBtn.isSelected()) {
            filterTagBtn.setSelected(false);
            filterRecyclerView.setVisibility(View.GONE);
        } else {
            filterTagBtn.setSelected(true);
            filterRecyclerView.setVisibility(View.VISIBLE);
            tagAdapter = new LogFilterAdapter(getActivity(), UILogBridge.getInstance().getTags(), this);
            filterRecyclerView.setAdapter(tagAdapter);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        UILogBridge.getInstance().setMsg(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        if (filterTagBtn.isSelected()) {
            TextView tv = (TextView) v;
            filterTagBtn.setText(tv.getText());
            filterRecyclerView.setVisibility(View.GONE);
            UILogBridge.getInstance().setTag(tv.getText().toString());
        } else if (filterLevelBtn.isSelected()) {
            TextView tv = (TextView) v;
            filterLevelBtn.setText(tv.getText());
            UILogBridge.getInstance().setLevel(tv.getText().toString());
        }
    }
}
