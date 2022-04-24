package org.tianjyan.luban.host.plugin.log.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.tianjyan.luban.host.R;

import java.util.List;

public class LogFilterAdapter extends RecyclerView.Adapter<LogFilterAdapter.ItemViewHolder> {
    protected List<String> list;
    protected Context context;
    protected LayoutInflater inflate;
    protected View.OnClickListener listener;

    public LogFilterAdapter(Context context, List<String> source, View.OnClickListener listener) {
        this.context = context;
        this.list = source;
        this.inflate = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LogFilterAdapter.ItemViewHolder(inflate.inflate(R.layout.list_view_filter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String item = list.get(position);
        holder.itemTV.setText(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemTV;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemTV = (TextView) itemView.findViewById(R.id.item_tv);
            itemView.setOnClickListener(listener);
        }
    }
}
