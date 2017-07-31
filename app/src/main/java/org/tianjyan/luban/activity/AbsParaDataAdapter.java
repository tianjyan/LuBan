package org.tianjyan.luban.activity;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import org.tianjyan.luban.R;
import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.model.Const;

import java.util.List;

public abstract class AbsParaDataAdapter extends RecyclerView.Adapter {
    protected List<? extends AidlEntry> list;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected View floating_title_view;
    protected View normal_title_view;
    protected View disable_title_view;

    public AbsParaDataAdapter(Context context, List<? extends AidlEntry> list) {
        this.mContext = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(mContext);

        floating_title_view = mInflater.inflate(R.layout.list_view_para_item_title, null);
        normal_title_view = mInflater.inflate(R.layout.list_view_para_item_title, null);
        disable_title_view = mInflater.inflate(R.layout.list_view_para_item_title, null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        AidlEntry aidlEntry = list.get(position);
        String key = "";
        if (aidlEntry instanceof OutPara) {
            key = ((OutPara) aidlEntry).getKey();
        } else if (aidlEntry instanceof InPara) {
            key = ((InPara) aidlEntry).getKey();
        }

        int type = -1;
        switch (key) {
            case Const.Floating_Area_Title:
                type = Const.Type_Floating_Title;
                break;
            case Const.Normal_Area_Title:
                type = Const.Type_Normal_Title;
                break;
            case Const.Disable_Area_Title:
                type = Const.Type_Disable_Title;
                break;
            default:
                type = Const.Type_Item;
                break;
        }
        return type;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
