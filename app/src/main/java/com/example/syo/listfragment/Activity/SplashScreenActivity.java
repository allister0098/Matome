package com.example.syo.listfragment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.syo.listfragment.R;

/**
 * Created by syo on 2015/07/21.
 */
public class SplashScreenActivity extends AppCompatActivity {
    private Handler hdl = new Handler();
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // splash.xmlをViewに指定します。
        setContentView(R.layout.activity_splash_screen);
        // タイトルを非表示にします。
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 1000ms遅延させてsplashHandlerを実行します。
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        };

        hdl.postDelayed(runnable, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hdl.removeCallbacks(runnable);
    }
}
