package com.example.syo.listfragment.Fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.syo.listfragment.Model.Item;
import com.example.syo.listfragment.R;
import com.squareup.picasso.Picasso;

import java.util.jar.Attributes;

/**
 * Created by syo on 2015/07/11.
 */
public class ItemLayout extends RelativeLayout {
    // タイトル
    TextView mTitleView;
    // アイコン
    ImageView mImageView;

    public ItemLayout(Context context, AttributeSet attrs) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitleView = (TextView) findViewById(R.id.item_title);
        mImageView = (ImageView) findViewById(R.id.item_img);
    }

    public void bindView(Item item) {
        mTitleView.setText(item.getTitle());
//        mImageView.setImageBitmap(item.getImage());
        Picasso.with(getContext()).load(item.getImgUrl().toString()).into(mImageView);
    }
}
