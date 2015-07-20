package com.example.syo.listfragment.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.syo.listfragment.R;

import org.xwalk.core.XWalkView;


public class WebDetailActivity extends AppCompatActivity {
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_detail);

        // CrossWalkライブラリのWebView
        XWalkView xWalkView = (XWalkView) findViewById(R.id.webView1);
        // IntentからURL取得
        Intent intent = getIntent();
        url = intent.getStringExtra("URL");

        xWalkView.load(url, null);
    }

}
