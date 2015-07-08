package com.example.syo.listfragment.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syo.listfragment.Model.Item;
import com.example.syo.listfragment.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by syo on 2015/07/05.
 */
public class ListAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;
    private TextView mTitle;
    private ImageView mImage;
    private URL imageUrl;
    private InputStream iStream;
    private Bitmap bitmap;

    public ListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    // 1行ごとのビューを生成する
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_row, null);
        }

        // 現在参照しているリストの位置からItemを取得する
        Item item = this.getItem(position);
        if (item != null) {
            // Itemから必要なデータを取り出し、ImageViewとTextViewをセットする
            String title = item.getTitle().toString();
            mTitle = (TextView) view.findViewById(R.id.item_title);
            mTitle.setText(title);

//            String imgUrl = item.getImgUrl().toString();
//
//            try {
//                // 画像のURLを入力
//                imageUrl = new URL(imgUrl);
//                // インプットストリームで画像を読み込む
//                iStream = imageUrl.openStream();
//                // 読み込んだファイルをビットマップに変換
//                bitmap = BitmapFactory.decodeStream(iStream);
//                // ビットマップをImageViewに設定
//                mImage.setImageBitmap(bitmap);
//                // インプットストリームを閉じる
//                iStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
        return view;
    }

}
