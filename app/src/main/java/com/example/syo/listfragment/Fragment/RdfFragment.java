package com.example.syo.listfragment.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.syo.listfragment.Activity.MainActivity;
import com.example.syo.listfragment.Activity.WebDetailActivity;
import com.example.syo.listfragment.Adapter.ListAdapter;
import com.example.syo.listfragment.Manager.AtomParserTask;
import com.example.syo.listfragment.Manager.RdfParserTask;
import com.example.syo.listfragment.Model.Content;
import com.example.syo.listfragment.Model.Item;
import com.example.syo.listfragment.R;

import java.util.ArrayList;

/**
 * Created by syo on 2015/07/05.
 */
public class RdfFragment extends android.app.ListFragment {
    private MainActivity mActivity;
    private RdfParserTask rdfTask;
    private AtomParserTask atomTask;
    private ArrayList<Item> mItems;
    private String url;
    private LayoutInflater mInflater;
    private ListAdapter adapter;

    public RdfFragment() {
        url = null;
    }

    public RdfFragment(String url) {
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
        adapter = new ListAdapter(mActivity, mItems);
        rdfTask = new RdfParserTask(mActivity, this, adapter);

        if (url == null) {
            rdfTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Content.MIND_MATOME);
        } else if (url.equals(Content.MIND_MATOME)) {
            // RSSの形式がrdfだったとき
            rdfTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_list_fragment, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        this.setListAdapter(null);
//        adapter.clear();
//        adapter = null;
        Log.d("DETACH", "ondetach");
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent = new Intent(mActivity, WebDetailActivity.class);
        intent.putExtra("URL", mItems.get(position).getUrl());
        startActivity(intent);
    }

}
