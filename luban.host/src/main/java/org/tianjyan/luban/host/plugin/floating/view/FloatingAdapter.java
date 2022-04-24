package org.tianjyan.luban.host.plugin.floating.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.host.R;

import java.util.List;

public class FloatingAdapter extends RecyclerView.Adapter<FloatingAdapter.ItemViewHolder> {
    protected List<OutPara> list;
    protected Context context;
    protected LayoutInflater inflate;

    public FloatingAdapter(Context context, List<OutPara> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);

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
