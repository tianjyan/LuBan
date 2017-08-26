package org.tianjyan.luban.plugin.op.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.plugin.op.R;
import org.tianjyan.luban.plugin.op.activity.OutParaDetailActivity;

import java.util.List;

public class OutParaDataAdapter extends RecyclerView.Adapter {
    private final String Floating_Area_Title;
    private final String Normal_Area_Title;
    static final int Type_Title = 0;
    static final int Type_Item = 1;
    protected List<OutPara> list;
    protected Context context;
    protected LayoutInflater inflate;

    public OutParaDataAdapter(Context context, List<OutPara> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
        Floating_Area_Title = context.getString(R.string.para_floating_title);
        Normal_Area_Title = context.getString(R.string.para_normal_title);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case Type_Title:
                viewHolder = new TitleViewHolder(inflate.inflate(R.layout.list_view_para_item_title, parent, false));
                break;
            case Type_Item:
                viewHolder = new ItemViewHolder(inflate.inflate(R.layout.list_view_para_item, parent, false));
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
            case Type_Title:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.titleTV.setText(outPara.getKey());
                break;
            case Type_Item:
                ItemViewHolder itemEnableViewHolder = (ItemViewHolder) holder;
                itemEnableViewHolder.keyTV.setText(outPara.getKey());
                itemEnableViewHolder.pkgNameTV.setText(outPara.getClient());
                itemEnableViewHolder.valueTV.setText(outPara.getValue());
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
        if (outPara.getKey() == Floating_Area_Title
            || outPara.getKey() == Normal_Area_Title) {
            type = Type_Title;
        } else {
            type = Type_Item;
        }
        return type;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView keyTV;
        TextView pkgNameTV;
        TextView valueTV;

        public ItemViewHolder(View itemView) {
            super(itemView);
            keyTV = (TextView) itemView.findViewById(R.id.para_key_tv);
            pkgNameTV = (TextView) itemView.findViewById(R.id.pkg_name_tv);
            valueTV = (TextView) itemView.findViewById(R.id.para_value_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, OutParaDetailActivity.class);
            intent.putExtra("pkgName", list.get(getAdapterPosition()).getClient());
            intent.putExtra("paraName", list.get(getAdapterPosition()).getKey());
            context.startActivity(intent);
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
