package com.example.syo.listfragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.syo.listfragment.Fragment.ItemLayout;
import com.example.syo.listfragment.Model.Item;
import com.example.syo.listfragment.R;

import java.util.List;

/**
 * Created by syo on 2015/07/05.
 */
public class ListAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;

    public ListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    // 1行ごとのビューを生成する
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemLayout view = null;

        if (convertView == null) {
            view = (ItemLayout) mInflater.inflate(R.layout.item_row, null);
        } else {
            view = (ItemLayout) convertView;
        }
        // Itemとitem_rowを紐づける
        view.bindView(getItem(position));

        return view;
    }



}
