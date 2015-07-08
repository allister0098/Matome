package com.example.syo.listfragment.Manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by syo on 2015/07/08.
 */
public class ImageUrlTask extends AsyncTask<String, Void, Bitmap> {
    private Bitmap bitmap;
    private InputStream is;
    private URL imageUrl;
    private ImageView mImage;

    public ImageUrlTask(ImageView mImage) {
        this.mImage = mImage;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        // 画像のURLを入力
        try {
            imageUrl = new URL(params[0]);
            // インプットストリームで画像を読み込む
            is = imageUrl.openStream();
            // 読み込んだファイルをビットマップに変換
            bitmap = BitmapFactory.decodeStream(is);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        // ビットマップをImageViewに設定
        mImage.setImageBitmap(bitmap);

        try {
            // インプットストリームを閉じる
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
