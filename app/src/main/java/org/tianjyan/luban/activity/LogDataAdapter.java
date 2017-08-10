package org.tianjyan.luban.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tianjyan.luban.R;
import org.tianjyan.luban.model.LogEntry;

import java.util.List;

public class LogDataAdapter extends RecyclerView.Adapter<LogDataAdapter.ItemViewHolder>  {
    protected List<LogEntry> list;
    protected Context context;
    protected LayoutInflater inflate;

    public LogDataAdapter(Context context, List<LogEntry> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LogDataAdapter.ItemViewHolder(inflate.inflate(R.layout.list_view_log_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        LogEntry logEntry = list.get(position);
        String displayMsg = logEntry.getDisplayMsg();
        int tagStart = displayMsg.indexOf("/") + 1;
        int tagEnd = displayMsg.indexOf("(", tagStart + 1);
        int pidStart = tagEnd + 1;
        int pidEnd = displayMsg.indexOf(")", pidStart + 1);

        SpannableString spannableString = new SpannableString(logEntry.getDisplayMsg());
        spannableString.setSpan(new ForegroundColorSpan(Color.argb(0xff, 0x9f, 0x9f, 0x9e)),
                0, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.argb(0xff, 0xcb, 0x74, 0x18)),
                20, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.argb(0xff, 0xcb, 0x74, 0x18)),
                tagStart, tagEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.argb(0xff, 0xcb, 0x74, 0x18)),
                pidStart, pidEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.logTV.setText(spannableString);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView logTV;

        public ItemViewHolder(View itemView) {
            super(itemView);
            logTV = (TextView) itemView.findViewById(R.id.log_rv);
        }
    }
}
