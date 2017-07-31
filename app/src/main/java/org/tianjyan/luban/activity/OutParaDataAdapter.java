package org.tianjyan.luban.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tianjyan.luban.R;
import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.model.Const;

import java.util.List;

public class OutParaDataAdapter extends RecyclerView.Adapter {
    protected List<OutPara> list;
    protected Context context;
    protected LayoutInflater inflate;

    public OutParaDataAdapter(Context context, List<OutPara> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case Const.Type_Title:
                viewHolder = new TitleViewHolder(inflate.inflate(R.layout.list_view_para_item_title, null));
                break;
            case Const.Type_Item:
                viewHolder = new ItemEnableViewHolder(inflate.inflate(R.layout.list_view_para_item, null));
                break;
            case Const.Type_Item_Disable:
                viewHolder = new ItemDisableViewHolder(inflate.inflate(R.layout.list_view_para_item_disable, null));
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        OutPara outPara = list.get(position);
        switch (outPara.getKey()) {
            case Const.Floating_Area_Title:
            case Const.Normal_Area_Title:
            case Const.Disable_Area_Title:
                type = Const.Type_Title;
                break;
            default:
                if (outPara.getDisplayProperty() == AidlEntry.DISPLAY_DISABLE) {
                    type = Const.Type_Item_Disable;
                } else {
                    type = Const.Type_Item;
                }
                break;
        }

        return type;
    }

    public class ItemEnableViewHolder extends RecyclerView.ViewHolder {

        public ItemEnableViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ItemDisableViewHolder extends RecyclerView.ViewHolder {

        public ItemDisableViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        public TitleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
