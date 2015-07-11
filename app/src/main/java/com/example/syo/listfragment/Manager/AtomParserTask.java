package com.example.syo.listfragment.Manager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.example.syo.listfragment.Activity.MainActivity;
import com.example.syo.listfragment.Adapter.ListAdapter;
import com.example.syo.listfragment.Fragment.ListFragment;
import com.example.syo.listfragment.Model.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by syo on 2015/07/10.
 */
public class AtomParserTask extends AsyncTask<String, Integer, ListAdapter> {
    private MainActivity mActivity;
    private List<Item> items;
    private ProgressDialog mProgressDialog;
    private ListFragment fragment;
    private RuntimeException error;
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
                            } else if (tag.equals("description")) {
                                currentItem.setDescription(parser.nextText());
                            } else if (tag.equals("content")) {
                                // 属性値から記事のURLを取得
                                Log.d("aaaaaaaa", "asdfasdfasdfads");
//                                String url = parser.getAttributeValue("xml", "base");
                                String url = "null";
//                                Log.d("BASE", parser.getAttributeValue("xml", "base"));
                                // <![CDATA[ の中身を取得
                                String cdata = parser.nextText();
                                // cdataから画像URLを抽出
                                String result = cdata.substring(cdata.indexOf("img src=\"")+9, cdata.indexOf("</a")-2);
                                Log.d("IMAGE", result);
                                // 画像URLをリファクタリング
                                if (result.contains("\"")) {
                                    result = result.replace(result.substring(result.indexOf("\""), result.length()), "");
                                }
                                // それぞれのURLをitemにセット
//                                currentItem.setImgUrl(result);
                                currentItem.setUrl(url);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG :
                        tag = parser.getName();
                        if (tag.equals("item")) {
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
