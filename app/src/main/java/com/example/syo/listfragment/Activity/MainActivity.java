package com.example.syo.listfragment.Activity;

import android.app.AlertDialog;
import android.app.Fragment;
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
import android.view.View;

import com.example.syo.listfragment.Fragment.RssFragment;
import com.example.syo.listfragment.Manager.Util;
import com.example.syo.listfragment.Model.Content;
import com.example.syo.listfragment.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private Fragment fragment;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(R.string.mind_channel);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Fragment起動
        getFragmentManager().beginTransaction().replace(R.id.replace_layout, new RssFragment()).commit();

        final View view = findViewById(R.id.header);
        view.setBackgroundResource(R.mipmap.mind_background);

        mTitle = getString(R.string.mind_channel);

        // DrawerLayoutの動作
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                switch (itemId) {
                    case R.id.navigation_item_1 :
                        fragment = new RssFragment(Content.MIND_MATOME);
                        // headerのバックグランド変更
                        view.setBackgroundResource(R.mipmap.mind_background);
                        // DrawerLayoutを閉じる
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mTitle = getString(R.string.mind_channel);

                        break;

                    case R.id.navigation_item_2 :
                        fragment = new RssFragment(Content.PHILOSOPHY_NEWS);
                        // headerのバックグランド変更
                        view.setBackgroundResource(R.mipmap.philosophy_background);
                        // DrawerLayoutを閉じる
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mTitle = getString(R.string.philosophy_news);

                        break;

                    case R.id.navigation_item_3 :
                        fragment = new RssFragment(Content.ALFA_MOSAIC);
                        // headerのバックグランド変更
                        view.setBackgroundResource(R.mipmap.alfa_background);
                        // DrawerLayoutを閉じる
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mTitle = getString(R.string.alfa_mosaic);

                        break;

                    case R.id.navigation_item_4 :
                        fragment = new RssFragment(Content.GAME_NEWS_JIN);
                        // headerのバックグランド変更
                        view.setBackgroundResource(R.mipmap.oreteki_logo);
                        // DrawerLayoutを閉じる
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mTitle = getString(R.string.game_new_jin);

                        break;

                    case R.id.navigation_item_5 :
                        fragment = new RssFragment(Content.BIP_BLOG);
                        // headerのバックグランド変更
                        view.setBackgroundResource(R.mipmap.bip_blog);
                        // DrawerLayoutを閉じる
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mTitle = getString(R.string.bip_blog);

                        break;

                    case R.id.navigation_item_6 :
                        fragment = new RssFragment(Content.HACHIMA_BLOG);
                        // headerのバックグランド変更
                        view.setBackgroundResource(R.mipmap.hachima_blog);
                        // DrawerLayoutを閉じる
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mTitle = getString(R.string.hachima_blog);

                        break;
                }
                getFragmentManager().beginTransaction().replace(R.id.replace_layout, fragment).commit();

                return false;
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                toolbar.setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Util.netWorkCheck(getApplicationContext())) {
            new AlertDialog.Builder(MainActivity.this).setTitle("Network Error").setMessage("ネットワークに接続できません。\nネットワーク設定を確認してください。").setCancelable(false).show();
        }
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
}
