package com.example.syo.listfragment.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.syo.listfragment.Adapter.ListAdapter;
import com.example.syo.listfragment.Fragment.ListFragment;
import com.example.syo.listfragment.Manager.RssParserTask;
import com.example.syo.listfragment.Model.Content;
import com.example.syo.listfragment.Model.Item;
import com.example.syo.listfragment.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;

    private ArrayList<Item> mItems;
    private ListAdapter mAdapter;
    private ListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.navigation_item_1 || itemId == R.id.navigation_item_2) {
                    Toast.makeText(getApplicationContext(), menuItem.getTitle() + "が選択中になりました", Toast.LENGTH_SHORT).show();
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                }

                if (itemId == R.id.navigation_sub_item_1 || itemId == R.id.navigation_sub_item_2) {
                    Toast.makeText(getApplicationContext(), menuItem.getTitle() + "を選択しました", Toast.LENGTH_SHORT).show();
                    mDrawerLayout.closeDrawers();
                    return true;
                }

                return false;
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        mItems = new ArrayList<Item>();
//        mFragment = new ListFragment();
//        mAdapter = new ListAdapter(this, mItems);
//        RssParserTask task = new RssParserTask(this, mFragment, mAdapter);
//        task.execute(Content.MIND_MATOME);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }
}
