package com.example.syo.listfragment.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.syo.listfragment.Activity.MainActivity;
import com.example.syo.listfragment.Adapter.ListAdapter;
import com.example.syo.listfragment.Manager.RssParserTask;
import com.example.syo.listfragment.Model.Content;
import com.example.syo.listfragment.Model.Item;
import com.example.syo.listfragment.R;

import java.util.ArrayList;

/**
 * Created by syo on 2015/07/05.
 */
public class ListFragment extends android.support.v4.app.ListFragment {
    private ListAdapter adapter;
    private MainActivity mActivity;
    private RssParserTask task;
    private ArrayList<Item> mItems;

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
        task = new RssParserTask(mActivity, this, adapter);
        task.execute(Content.MIND_MATOME);
//        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_list_fragment, container, false);
    }
}
