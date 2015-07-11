package com.example.syo.listfragment.Manager;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.example.syo.listfragment.Activity.MainActivity;
import com.example.syo.listfragment.Adapter.ListAdapter;
import com.example.syo.listfragment.Fragment.ListFragment;
import com.example.syo.listfragment.Model.Item;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by syo on 2015/07/06.
 */
public class RdfParserTask extends AsyncTask<String, Integer, ListAdapter> {
    private MainActivity mActivity;
    private List<Item> items;
    private ProgressDialog mProgressDialog;
    private ListFragment fragment;
    private RuntimeException error;
    private ListAdapter mAdapter;

    // コンストラクタ
    public RdfParserTask(MainActivity activity, ListFragment fragment, ListAdapter adapter) {
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
        Log.d("RDF:START", "start");
        ListAdapter result = null;
        try {
            // HTTP経由でアクセスし、InputStreamを取得する
            URL url = new URL(params[0]);
            InputStream is = url.openConnection().getInputStream();

            result = parseRdf(is);

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
        Log.d("RDF:END", "finish!!!");
    }

    // Rdfをパースする
    private ListAdapter parseRdf(InputStream is) {
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
                        if (tag.equals("item")) {
                            currentItem = new Item();
                        } else if (currentItem != null) {
                            if (tag.equals("title")) {
                                currentItem.setTitle(parser.nextText());
                            } else if (tag.equals("description")) {
                                currentItem.setDescription(parser.nextText());
                            } else if (tag.equals("encoded")) {

                                // <![CDATA[ の中身を取得
                                String cdata = parser.nextText();
                                // cdataから画像URLを抽出
                                String result = cdata.substring(cdata.indexOf("src=\"")+5, cdata.indexOf("</a")-2);
                                // cdataから記事のURLを取得
                                String url = cdata.substring(cdata.indexOf("a href=\"")+8, cdata.indexOf("続きを読む")-2);
                                // 画像URLをリファクタリング
                                if (result.contains("\"")) {
                                    result = result.replace(result.substring(result.indexOf("\""), result.length()), "");
                                }

                                // それぞれのURLをitemにセット
                                currentItem.setImage(parseImage(result));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mAdapter;
    }

    private Bitmap parseImage(String result) throws IOException {
        URL imageUrl = new URL(result);
        // インプットストリームで画像を読み込む
        InputStream is = imageUrl.openStream();
        // 読み込んだファイルをビットマップに変換
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        // インプットストリームを閉じる
        is.close();

        return bitmap;
    }



}
