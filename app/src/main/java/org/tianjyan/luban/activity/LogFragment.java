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
import android.widget.EditText;
import android.widget.TextView;

import org.tianjyan.luban.R;
import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.bridge.UILogBridge;
import org.tianjyan.luban.view.DividerLine;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogFragment extends Fragment implements TextWatcher, View.OnClickListener {
    @BindView(R.id.log_rv) RecyclerView recyclerView;
    @BindView(R.id.filter_rv) RecyclerView filterRecyclerView;
    @BindView(R.id.filter_msg_et) EditText filterMsgET;
    @BindView(R.id.filter_level_tv) TextView filterLevelTV;
    @BindView(R.id.filter_tag_tv) TextView filterTagTV;

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
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(1);
        dividerLine.setColor(R.color.white);
        filterRecyclerView.addItemDecoration(dividerLine);
        filterLevelTV.setText(Config.VERBOSE);
        filterTagTV.setText(Config.TAG);
        levelAdapter = new LogFilterAdapter(getActivity(), UILogBridge.getInstance().getLevels(), this);
        return rootView;
    }

    @OnClick(R.id.action_level)
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

    @OnClick(R.id.action_tag)
    public void onTagClick() {
        filterLevelTV.setSelected(false);
        if (filterTagTV.isSelected()) {
            filterTagTV.setSelected(false);
            filterRecyclerView.setVisibility(View.GONE);
        } else {
            filterTagTV.setSelected(true);
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
        if (filterTagTV.isSelected()) {
            TextView tv = (TextView) v;
            filterTagTV.setText(tv.getText());
            filterRecyclerView.setVisibility(View.GONE);
            filterTagTV.setSelected(false);
            UILogBridge.getInstance().setTag(tv.getText().toString());
        } else if (filterLevelTV.isSelected()) {
            TextView tv = (TextView) v;
            filterLevelTV.setText(tv.getText());
            filterRecyclerView.setVisibility(View.GONE);
            filterLevelTV.setSelected(false);
            UILogBridge.getInstance().setLevel(tv.getText().toString());
        }
    }
}
