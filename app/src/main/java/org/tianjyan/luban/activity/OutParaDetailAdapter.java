package org.tianjyan.luban.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tianjyan.luban.R;
import org.tianjyan.luban.model.ParaHistory;

import java.util.List;

public class OutParaDetailAdapter extends RecyclerView.Adapter<OutParaDetailAdapter.HistoryViewHolder> {
    protected List<ParaHistory> list;
    protected Context context;
    protected LayoutInflater inflate;

    public OutParaDetailAdapter(Context context, List<ParaHistory> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(inflate.inflate(R.layout.list_view_para_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        ParaHistory paraHistory = list.get(position);
        holder.valueTV.setText(paraHistory.getValue());
        holder.timeTV.setText(paraHistory.getDisplayTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView timeTV;
        TextView valueTV;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            timeTV = (TextView) itemView.findViewById(R.id.para_time_tv);
            valueTV = (TextView) itemView.findViewById(R.id.para_value_tv);
        }
    }
}
