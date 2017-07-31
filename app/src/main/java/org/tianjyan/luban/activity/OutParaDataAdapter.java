package org.tianjyan.luban.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.model.Const;

import java.util.List;

public class OutParaDataAdapter extends AbsParaDataAdapter {

    public OutParaDataAdapter(Context context, List<? extends AidlEntry> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Const.Type_Floating_Title:
                break;
            case Const.Type_Normal_Title:
                break;
            case Const.Type_Disable_Title:
                break;
            case Const.Type_Item:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
