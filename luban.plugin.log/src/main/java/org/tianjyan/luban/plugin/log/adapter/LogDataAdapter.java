package org.tianjyan.luban.plugin.log.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.tianjyan.luban.aidl.Config;
import org.tianjyan.luban.plugin.log.R;
import org.tianjyan.luban.plugin.log.model.LogEntry;
import org.tianjyan.luban.plugin.log.model.RemoveRangeArrayList;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public class LogDataAdapter extends RecyclerView.Adapter<LogDataAdapter.ItemViewHolder>  implements Filterable {
    protected List<LogEntry> source;
    protected ReadWriteLock lock;
    protected RemoveRangeArrayList<LogEntry> list;
    protected Context context;
    protected LayoutInflater inflate;
    protected Filter filter;
    protected String filterMsg = "";
    protected int filterLevel = Config.LOG_VERBOSE;
    protected String filterTag = Config.TAG;

    public LogDataAdapter(Context context, RemoveRangeArrayList<LogEntry> source, ReadWriteLock lock) {
        this.context = context;
        this.source = source;
        this.lock = lock;
        this.list = source;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LogDataAdapter.ItemViewHolder(inflate.inflate(R.layout.list_view_log_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        LogEntry logEntry = list.get(position);
        holder.logTV.setText(logEntry.getDisplayMsg());
        int resId;
        switch (logEntry.getLevel()) {
            case Config.LOG_VERBOSE:
                resId = R.color.log_verbose;
                break;
            case Config.LOG_DEBUG:
                resId = R.color.log_debug;
                break;
            case Config.LOG_INFO:
                resId = R.color.log_info;
                break;
            case Config.LOG_WARNING:
                resId = R.color.log_warning;
                break;
            case Config.LOG_ERROR:
                resId = R.color.log_error;
                break;
            default:
                resId = R.color.log_assert;

        }

        holder.logTV.setTextColor(context.getResources().getColor(resId));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new SearchFilter();
        }
        return filter;
    }

    public void onNewEntries(List<LogEntry> entries) {
        if (list != source) {
            for (LogEntry logEntry: entries) {
                if (matchCondition(logEntry, filterTag, filterLevel, filterMsg)) {
                    list.add(logEntry);
                }
            }
            if (list.size() > Config.MAX_LOG_SUPPORT) {
                list.remove(0, list.size() - Config.MAX_LOG_SUPPORT);
            }
        }
        notifyDataSetChanged();
    }

    public void setTag(String tag) {
        filterTag = tag;
        getFilter().filter(filterMsg);
    }

    public void setLevel(int level) {
        filterLevel = level;
        getFilter().filter(filterMsg);
    }

    public void setMsg(String msg) {
        filterMsg = msg;
        getFilter().filter(filterMsg);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView logTV;

        public ItemViewHolder(View itemView) {
            super(itemView);
            logTV = (TextView) itemView.findViewById(R.id.log_tv);
        }
    }

    private boolean matchCondition(LogEntry logEntry, String tag, int level, String msg) {
        if (level <= logEntry.getLevel()
            && (Config.TAG.equals(tag) || logEntry.getTag().equals(tag))
            && (msg.isEmpty() || logEntry.getMsg().contains(msg))) {
            return true;
        }
        return false;
    }

    class SearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            filterMsg = constraint.toString();
            if (filterLevel == Config.LOG_VERBOSE
                    && filterTag == Config.TAG
                    && filterMsg.isEmpty()) {
                lock.readLock().lock();
                results.values = source;
                results.count = source.size();
                lock.readLock().unlock();
            } else {
                lock.readLock().lock();
                List<LogEntry> dataSet = new RemoveRangeArrayList<>();
                for (LogEntry logEntry : source) {
                    if (matchCondition(logEntry, filterTag, filterLevel, filterMsg)) {
                        dataSet.add(logEntry);
                    }
                }
                results.values = dataSet;
                results.count = dataSet.size();
                lock.readLock().unlock();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (RemoveRangeArrayList<LogEntry>) results.values;
            notifyDataSetChanged();
        }
    }
}
