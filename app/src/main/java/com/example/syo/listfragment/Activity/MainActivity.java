package com.example.syo.listfragment.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.syo.listfragment.Fragment.AtomFragment;
import com.example.syo.listfragment.Fragment.RdfFragment;
import com.example.syo.listfragment.Model.Content;
import com.example.syo.listfragment.R;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RdfFragment rdfFragment;
    private AtomFragment atomFragment;
    private Fragment fragment;
    private Handler mHandler = new Handler();

    private final Runnable mRefreshDone = new Runnable() {
        @Override
        public void run() {
            // 3. プログレスを終了させる
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };


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

        getFragmentManager().beginTransaction().add(R.id.replace_layout, new RdfFragment()).commit();


        // DrawerLayoutの動作
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                switch (itemId) {
                    case R.id.navigation_item_1:
                        rdfFragment = new RdfFragment(Content.MIND_MATOME);
                        // DrawerLayoutを閉じる
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        // DrawerLayoutで選択された箇所によってFragmentを切り替える
                        getFragmentManager().beginTransaction().replace(R.id.replace_layout, rdfFragment).commit();
                        return true;

                    case R.id.navigation_item_2:
                        atomFragment = new AtomFragment(Content.PHILOSOPHY_NEWS);
                        // DrawerLayoutを閉じる
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        // DrawerLayoutで選択された箇所によってFragmentを切り替える
                        getFragmentManager().beginTransaction().replace(R.id.replace_layout, atomFragment).commit();
                        return true;
                }



                return false;
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);



    }

    @Override
    public void onResume() {
        super.onResume();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
        mSwipeRefreshLayout.setOnRefreshListener(this);
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

    @Override
    public void onRefresh() {
        // スワイプ時に呼び出される。ここで更新の処理を行う。
        refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindDrawables(findViewById(R.id.item_img));

    }

//    private void unbindDrawables(View view) {
//        if (view.getBackground() != null) {
//            view.getBackground().setCallback(null);
//        }
//        if (view instanceof ViewGroup) {
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//                unbindDrawables(((ViewGroup) view).getChildAt(i));
//            }
//            ((ViewGroup) view).removeAllViews();
//        }
//    }


    private void refresh() {
        // 今のところダミーとして1秒後に更新終了処理を呼び出している。
        mHandler.removeCallbacks(mRefreshDone);
        mHandler.postDelayed(mRefreshDone, 1000);
    }
}
