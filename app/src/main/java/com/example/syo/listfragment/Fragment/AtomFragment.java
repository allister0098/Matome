package com.example.syo.listfragment.Fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
 * Created by syo on 2015/07/15.
 */
public class AtomFragment extends ListFragment {
    private MainActivity mActivity;
    private RdfParserTask rdfTask;
    private AtomParserTask atomTask;
    private ArrayList<Item> mItems;
    private String url;
    private LayoutInflater mInflater;

    public AtomFragment() {
        url = null;
    }

    public AtomFragment(String url) {
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

        atomTask = new AtomParserTask(mActivity, this, adapter);
        atomTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_list_fragment, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("DETACH", "ondetach");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(mActivity, WebDetailActivity.class);
        intent.putExtra("URL", mItems.get(position).getUrl());
        startActivity(intent);
    }
}
