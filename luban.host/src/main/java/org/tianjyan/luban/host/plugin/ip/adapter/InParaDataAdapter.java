package org.tianjyan.luban.host.plugin.ip.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.host.R;
import org.tianjyan.luban.host.plugin.ip.activity.InParaDetailActivity;

import java.util.List;

public class InParaDataAdapter extends RecyclerView.Adapter<InParaDataAdapter.ItemViewHolder>  {

    protected List<InPara> list;
    protected Context context;
    protected LayoutInflater inflate;

    public InParaDataAdapter(Context context, List<InPara> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflate.inflate(R.layout.list_view_para_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        InPara inPara = list.get(position);
        holder.keyTV.setText(inPara.getKey());
        holder.pkgNameTV.setText(inPara.getClient());
        holder.valueTV.setText(inPara.getSelectedValue());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
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
            Intent intent = new Intent(context, InParaDetailActivity.class);
            intent.putExtra("pkgName", list.get(getAdapterPosition()).getClient());
            intent.putExtra("paraName", list.get(getAdapterPosition()).getKey());
            context.startActivity(intent);
        }
    }
}
