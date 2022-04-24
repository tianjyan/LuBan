package org.tianjyan.luban.plugin.log.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.infrastructure.abs.ui.AbsFragment;
import org.tianjyan.luban.plugin.common.AliasName;
import org.tianjyan.luban.plugin.log.R;
import org.tianjyan.luban.plugin.log.R2;
import org.tianjyan.luban.plugin.log.adapter.LogFilterAdapter;
import org.tianjyan.luban.plugin.log.bridge.UILogBridge;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LogFragment extends AbsFragment implements TextWatcher, View.OnClickListener {
    @Inject @Named(AliasName.LOG_BRIDGE) UILogBridge uiLogBridge;
    @BindView(R2.id.log_rv)
    RecyclerView recyclerView;
    @BindView(R2.id.filter_rv) RecyclerView filterRecyclerView;
    @BindView(R2.id.filter_msg_et) EditText filterMsgET;
    @BindView(R2.id.filter_level_tv) TextView filterLevelTV;
    @BindView(R2.id.filter_tag_tv) TextView filterTagTV;

    LogFilterAdapter levelAdapter;
    LogFilterAdapter tagAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log, container, false);
        ButterKnife.bind(this, rootView);
        filterMsgET.addTextChangedListener(this);
        recyclerView.setAdapter(uiLogBridge.getLogDataAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(1);
        dividerLine.setColor(R.color.white);
        filterRecyclerView.addItemDecoration(dividerLine);
        filterLevelTV.setText(Config.VERBOSE);
        filterTagTV.setText(Config.TAG);
        levelAdapter = new LogFilterAdapter(getActivity(), uiLogBridge.getLevels(), this);
        return rootView;
    }

    @OnClick(R2.id.action_level)
    public void onLevelClick() {
        filterTagTV.setSelected(false);
        if (filterLevelTV.isSelected()) {
            filterLevelTV.setSelected(false);
            filterRecyclerView.setVisibility(View.GONE);
        } else {
            filterLevelTV.setSelected(true);
            filterRecyclerView.setVisibility(View.VISIBLE);
            filterRecyclerView.setAdapter(levelAdapter);
        }
    }

    @OnClick(R2.id.action_tag)
    public void onTagClick() {
        filterLevelTV.setSelected(false);
        if (filterTagTV.isSelected()) {
            filterTagTV.setSelected(false);
            filterRecyclerView.setVisibility(View.GONE);
        } else {
            filterTagTV.setSelected(true);
            filterRecyclerView.setVisibility(View.VISIBLE);
            tagAdapter = new LogFilterAdapter(getActivity(), uiLogBridge.getTags(), this);
            filterRecyclerView.setAdapter(tagAdapter);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        uiLogBridge.setMsg(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        if (filterTagTV.isSelected()) {
            TextView tv = (TextView) v;
            filterTagTV.setText(tv.getText());
            filterRecyclerView.setVisibility(View.GONE);
            filterTagTV.setSelected(false);
            uiLogBridge.setTag(tv.getText().toString());
        } else if (filterLevelTV.isSelected()) {
            TextView tv = (TextView) v;
            filterLevelTV.setText(tv.getText());
            filterRecyclerView.setVisibility(View.GONE);
            filterLevelTV.setSelected(false);
            uiLogBridge.setLevel(tv.getText().toString());
        }
    }
}
