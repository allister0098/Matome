package com.example.syo.listfragment.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class RssFragment extends android.app.ListFragment implements SwipeRefreshLayout.OnRefreshListener {
    private MainActivity mActivity;
    private RdfParserTask rdfTask;
    private AtomParserTask atomTask;
    private ArrayList<Item> mItems;
    private String url;
    private ListAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public RssFragment() {
        url = null;
    }

    public RssFragment(String url) {
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
        atomTask = new AtomParserTask(mActivity, this, adapter);

        if (url == null) {
            rdfTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Content.MIND_MATOME);
        } else if (url.contains("atom")) {
            switch (url) {
                case Content.PHILOSOPHY_NEWS :
                    atomTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Content.PHILOSOPHY_NEWS);
                    break;
            }
        } else if (url.contains(".rdf")) {
            switch (url) {
                case Content.MIND_MATOME :
                    rdfTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Content.MIND_MATOME);
                    break;

                case Content.ALFA_MOSAIC :
                    rdfTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Content.ALFA_MOSAIC);
                    break;

                case Content.GAME_NEWS_JIN :
                    rdfTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Content.GAME_NEWS_JIN);
                    break;

                case Content.BIP_BLOG :
                    rdfTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Content.BIP_BLOG);
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list_fragment, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent = new Intent(mActivity, WebDetailActivity.class);
        intent.putExtra("URL", mItems.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        // 更新処理を実装する
        getFragmentManager().beginTransaction().replace(R.id.replace_layout, this).commit();
        // 更新が終了したらインジケータ非表示
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
