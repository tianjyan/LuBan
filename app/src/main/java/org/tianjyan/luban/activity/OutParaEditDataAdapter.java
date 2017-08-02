package org.tianjyan.luban.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.tianjyan.luban.R;
import org.tianjyan.luban.aidl.AidlEntry;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.model.Const;

import java.util.List;

public class OutParaEditDataAdapter extends BaseAdapter {
    protected List<OutPara> list;
    protected Context context;
    protected LayoutInflater inflate;

    public OutParaEditDataAdapter(Context context, List<OutPara> source) {
        this.list = source;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        OutPara outPara = list.get(position);
        switch (type) {
            case Const.Type_Title:
                TitleViewHolder titleViewHolder;
                if (convertView == null) {
                    convertView = inflate.inflate(R.layout.list_view_para_item_title, parent, false);
                    titleViewHolder = new TitleViewHolder(convertView);
                    convertView.setTag(titleViewHolder);
                } else {
                    titleViewHolder = (TitleViewHolder)convertView.getTag();
                }
                titleViewHolder.titleTV.setText(outPara.getKey());
                break;
            default:
                ItemDisableViewHolder itemDisableViewHolder;
                if (convertView == null) {
                    convertView = inflate.inflate(R.layout.list_view_para_item_drag, parent, false);
                    itemDisableViewHolder = new ItemDisableViewHolder(convertView);
                    convertView.setTag(itemDisableViewHolder);
                } else {
                    itemDisableViewHolder = (ItemDisableViewHolder) convertView.getTag();
                }
                itemDisableViewHolder.keyTV.setText(outPara.getKey());
                break;
        }
        return convertView;
    }

    public class TitleViewHolder {
        TextView titleTV;

        public TitleViewHolder(View itemView) {
            titleTV = (TextView) itemView.findViewById(R.id.para_title_tv);
        }
    }

    public class ItemDisableViewHolder {
        TextView keyTV;

        public ItemDisableViewHolder(View itemView) {
            keyTV = (TextView) itemView.findViewById(R.id.para_key_tv);
        }
    }
}
