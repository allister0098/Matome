package com.example.syo.listfragment.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.syo.listfragment.Model.Item;
import com.example.syo.listfragment.R;

import java.net.URL;

public class WebDetailActivity extends AppCompatActivity {
    private Item mItem;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_detail);

        WebView mWebView = (WebView)findViewById(R.id.webView1);
        Intent intent = getIntent();
        url = intent.getStringExtra("URL");

        //標準ブラウザをキャンセル
        mWebView.setWebViewClient(new WebViewClient());
        // アプリ起動時に読み込むURL
//        mWebView.loadUrl(mItem.getUrl().toString());
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
