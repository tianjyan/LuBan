package org.tianjyan.luban.host.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.tianjyan.luban.host.R;

import java.util.ArrayList;
import java.util.List;

public class MainMenuFragmentAdapter extends BaseAdapter {
    private List<String> mFunctions = new ArrayList<>();
    private Context mContext;
    private String mCurrentFunction;

    public MainMenuFragmentAdapter(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mFunctions.size();
    }

    @Override
    public String getItem(int position) {
        return mFunctions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.navigation_menu_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.arrowView = convertView.findViewById(R.id.arrow_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String function = getItem(position);
        viewHolder.name.setText(function);

        if (mCurrentFunction.equals(function)) {
            viewHolder.arrowView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.arrowView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public String getCurrentFunction() {
        return mCurrentFunction;
    }

    public void setCurrentFunction(String currentFunction) {
        this.mCurrentFunction = currentFunction;
        notifyDataSetChanged();
    }

    public void setData(List<String> functions) {
        this.mFunctions.clear();
        this.mFunctions.addAll(functions);
    }

    private class ViewHolder {
        TextView name;
        View arrowView;
    }
}
