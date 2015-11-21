package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cn.edu.cqupt.nmid.headline.R;

/**
 * Created by xwysun on 2015/11/19.
 */
public class SpinnerAdapter extends BaseAdapter {
    private ArrayList<String> mList;
    private Context mContext;

    public SpinnerAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        convertView = mLayoutInflater.inflate(R.layout.layout_simplespinner_iten, null);
        if (convertView != null) {
            TextView spinnerText = (TextView) convertView.findViewById(R.id.spinner_item_text);
            spinnerText.setText(mList.get(position));
        }
        return convertView;
    }
}
