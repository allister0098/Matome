package com.example.syo.listfragment.Manager;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.example.syo.listfragment.Activity.MainActivity;
import com.example.syo.listfragment.Adapter.ListAdapter;
import com.example.syo.listfragment.Model.Content;
import com.example.syo.listfragment.Model.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by syo on 2015/07/10.
 */
public class AtomParserTask extends AsyncTask<String, Integer, ListAdapter> {
    private MainActivity mActivity;
    private ProgressDialog mProgressDialog;
    private ListFragment fragment;
    private ListAdapter mAdapter;

    // コンストラクタ
    public AtomParserTask(MainActivity activity, ListFragment fragment, ListAdapter adapter) {
        mActivity = activity;
        this.fragment = fragment;
        mAdapter = adapter;
    }

    // タスクを実行した直後にコールされる
    @Override
    protected void onPreExecute() {
        // プログレスバーを表示する
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setMessage("Now Loading...");
        mProgressDialog.show();

    }

    @Override
    protected ListAdapter doInBackground(String... params) {
        Log.d("Atom:START", "start");
        ListAdapter result = null;
        try {
            // HTTP経由でアクセスし、InputStreamを取得する
            URL url = new URL(params[0]);
            InputStream is = url.openConnection().getInputStream();

            result = parseAtom(is);

            is.close();
            // URLの中身をLogで表示
            Log.d("URL", url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    // メインスレッド上で実行される
    @Override
    protected void onPostExecute(ListAdapter result) {
        fragment.setListAdapter(result);
        result.notifyDataSetChanged();
        // プログレスダイアログを消す
        mProgressDialog.dismiss();
        Log.d("Atom:END", "finish!!!!!!!!!");
    }

    // Atomをパースする
    private ListAdapter parseAtom(InputStream is) {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            Item currentItem = null;
            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tag = null;
                switch (eventType) {
                    case XmlPullParser.START_TAG :
                        tag = parser.getName();
                        if (tag.equals("entry")) {
                            currentItem = new Item();
                        } else if (currentItem != null) {
                            if (tag.equals("title")) {
                                currentItem.setTitle(parser.nextText());
                            } else if (tag.equals("content")) {

                                // 属性値から記事のURLを取得
                                String url = parser.getAttributeValue(null, "base");
                                // <![CDATA[ の中身を取得
                                String cdata = parser.nextText();
                                // cdataから画像URLを抽出
                                Pattern imgPattern = Pattern.compile("http?://[\\w/:%#\\$&\\?\\(\\)~\\.=\\+\\-]+(jpg|png|gif)");
                                Matcher image = imgPattern.matcher(cdata);
                                String result;
                                if (image.find()) {
                                    result = image.group();
                                } else {
                                    result = Content.NO_IMAGE;
                                }
                                // それぞれのURLをitemにセット
                                currentItem.setImgUrl(result);
                                currentItem.setUrl(url);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG :
                        tag = parser.getName();
                        if (tag.equals("entry")) {
                            mAdapter.add(currentItem);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mAdapter;
    }


}
