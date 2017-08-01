package org.tianjyan.luban.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
                viewHolder = new TitleViewHolder(inflate.inflate(R.layout.list_view_para_item_title, parent, false));
                break;
            case Const.Type_Item:
                viewHolder = new ItemEnableViewHolder(inflate.inflate(R.layout.list_view_para_item, parent, false));
                break;
            case Const.Type_Item_Disable:
                viewHolder = new ItemDisableViewHolder(inflate.inflate(R.layout.list_view_para_item_disable, parent, false));
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        OutPara outPara = list.get(position);

        switch (type) {
            case Const.Type_Title:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.titleTV.setText(outPara.getKey());
                break;
            case Const.Type_Item:
                ItemEnableViewHolder itemEnableViewHolder = (ItemEnableViewHolder) holder;
                itemEnableViewHolder.keyTV.setText(outPara.getKey());
                itemEnableViewHolder.pkgNameTV.setText(outPara.getClient());
                itemEnableViewHolder.valueTV.setText(outPara.getValue());
                break;
            case Const.Type_Item_Disable:
                ItemDisableViewHolder itemDisableViewHolder = (ItemDisableViewHolder) holder;
                itemDisableViewHolder.keyTV.setText(outPara.getKey());
                break;
            default:
                break;
        }
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
        TextView keyTV;
        TextView pkgNameTV;
        TextView valueTV;

        public ItemEnableViewHolder(View itemView) {
            super(itemView);
            keyTV = (TextView) itemView.findViewById(R.id.para_key_tv);
            pkgNameTV = (TextView) itemView.findViewById(R.id.pkg_name_tv);
            valueTV = (TextView) itemView.findViewById(R.id.para_value_tv);
        }
    }

    public class ItemDisableViewHolder extends RecyclerView.ViewHolder {
        TextView keyTV;

        public ItemDisableViewHolder(View itemView) {
            super(itemView);
            keyTV = (TextView) itemView.findViewById(R.id.para_key_tv);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;

        public TitleViewHolder(View itemView) {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.para_title_tv);
        }
    }
}
