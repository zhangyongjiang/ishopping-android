package com.nextshopper.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nextshopper.activity.ProductDetailsActivity;
import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.beans.SearchableProduct;

/**
 * Created by siyiliu on 9/26/15.
 */
public class ItemView extends RelativeLayout {

    private TextView textViewDisPrice;
    private TextView textViewPrice;
    private TextView textViewName ;
    private ImageView imageView;
    private Context context;
    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.list_item, this);
        this.context = context;
        textViewDisPrice = (TextView)findViewById(R.id.item_disount_price);
        textViewPrice = (TextView)findViewById(R.id.item_ori_price);
        textViewName = (TextView)findViewById(R.id.item_name);
        imageView  = (ImageView) findViewById(R.id.item_image);
    }

    public void setValue(final SearchableProduct sp){
        textViewDisPrice.setText("$" + Math.round(sp.price * 100) / 100.0);
        textViewPrice.setText("$" + Math.round(sp.listPrice * 100) / 100.0);
        textViewName.setText(sp.name);
        imageView.setImageBitmap(null);
        setImageView(imageView, sp.imgUrl.get(0));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productId", sp.id);
                Log.d("NextShopper", sp.imgUrl.get(0));
                context.startActivity(intent);

            }
        };
        this.setOnClickListener(listener);
    }

    void setImageView(ImageView imageView, String url) {
        ((NextShopperApplication)context.getApplicationContext()).loadBitmaps(url, imageView, false, 0);
    }
}
