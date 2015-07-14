package com.nextshopper.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.ProductDetailsActivity;
import com.nextshopper.activity.R;
import com.nextshopper.rest.beans.SearchableProduct;
import com.nextshopper.rest.beans.SearchableProductList;

import java.io.InputStream;

/**
 * Created by Zhang_Kevin on 7/4/15.
 */
public class ProductGridAdapter extends BaseAdapter {
    private Context ctx;
    private SearchableProductList searchableProductList;

    public ProductGridAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        if(searchableProductList == null)
            return 0;
        return searchableProductList.items.size();
    }

    @Override
    public Object getItem(int position) {
        return searchableProductList.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchableProduct sp = searchableProductList.items.get(position);
        if(convertView == null) {
            convertView= LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false);
        }

        TextView textViewDisPrice = (TextView) convertView.findViewById(R.id.item_disount_price);
        textViewDisPrice.setText("$"+Math.round(sp.price*100)/100.0);
        TextView textViewPrice =(TextView) convertView.findViewById(R.id.item_ori_price);
        textViewPrice.setText("$"+Math.round(sp.listPrice*100)/100.0);
        TextView textViewName = (TextView) convertView.findViewById(R.id.item_name);
        textViewName.setText(sp.name);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.item_image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ProductDetailsActivity.class);
                intent.putExtra("productId", (String)v.getTag());
                ctx.startActivity(intent);
            }
        };
        imageView.setOnClickListener(listener);
        imageView.setTag(sp.id);
        new DownloadImageTask(imageView).execute(sp.imgUrl.get(0));
        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            if(!urldisplay.startsWith("http"))
                urldisplay = "http://api.onsalelocal.com/ws/resource/download?path=" + urldisplay;
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void setSearchableProductList(SearchableProductList searchableProductList) {
        this.searchableProductList = searchableProductList;
        notifyDataSetChanged();
    }
}
