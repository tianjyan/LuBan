package org.tianjyan.luban.host.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.host.R;

import java.util.List;

public class FloatingAdapter extends RecyclerView.Adapter<FloatingAdapter.ItemViewHolder> {
    private final String Floating_Area_Title;
    private final String Normal_Area_Title;
    static final int Type_Title = 0;
    static final int Type_Item = 1;
    protected List<OutPara> list;
    protected Context context;
    protected LayoutInflater inflate;

    public FloatingAdapter(Context context, List<OutPara> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
        Floating_Area_Title = context.getString(org.tianjyan.luban.plugin.op.R.string.para_floating_title);
        Normal_Area_Title = context.getString(org.tianjyan.luban.plugin.op.R.string.para_normal_title);

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflate.inflate(R.layout.list_view_para_floating_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        OutPara outPara = list.get(position);
        holder.keyTV.setText(outPara.getKey());
        holder.valueTV.setText(outPara.getValue());
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

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView keyTV;
        TextView valueTV;

        public ItemViewHolder(View itemView) {
            super(itemView);
            keyTV = (TextView) itemView.findViewById(R.id.para_key_tv);
            valueTV = (TextView) itemView.findViewById(R.id.para_value_tv);
        }
    }
}
