package com.example.syo.listfragment.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.syo.listfragment.Activity.MainActivity;
import com.example.syo.listfragment.Activity.WebDetailActivity;
import com.example.syo.listfragment.Adapter.ListAdapter;
import com.example.syo.listfragment.Manager.RdfParserTask;
import com.example.syo.listfragment.Model.Content;
import com.example.syo.listfragment.Model.Item;
import com.example.syo.listfragment.R;

import java.util.ArrayList;

/**
 * Created by syo on 2015/07/05.
 */
public class ListFragment extends android.app.ListFragment {
    private MainActivity mActivity;
    private RdfParserTask task;
    private ArrayList<Item> mItems;
    private String url;

    public ListFragment() {
        url = null;
    }

    public ListFragment(String url) {
        this.url = url;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mActivity = (MainActivity) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<>();
        ListAdapter adapter = new ListAdapter(mActivity, mItems);
        task = new RdfParserTask(mActivity, this, adapter);
        task.execute(Content.MIND_MATOME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_list_fragment, container, false);
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent = new Intent(mActivity, WebDetailActivity.class);
        intent.putExtra("URL", mItems.get(position).getUrl());
        startActivity(intent);
    }

}
