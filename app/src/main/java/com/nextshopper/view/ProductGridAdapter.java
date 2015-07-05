package com.nextshopper.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nextshopper.activity.HomeActivity;
import com.nextshopper.activity.ProductDetailsActivity;
import com.nextshopper.activity.R;
import com.nextshopper.rest.beans.ProductDetails;
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
        ImageView imageView = null;
        if(convertView == null) {
            imageView = new ImageView(ctx);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, ProductDetailsActivity.class);
                    intent.putExtra("productId", (String)v.getTag(R.id.ProductId));
                    ctx.startActivity(intent);
                }
            };
            imageView.setOnClickListener(listener);
        }
        else {
            imageView = (ImageView)convertView;
        }
        imageView.setTag(R.id.ProductId, searchableProductList.items.get(position).id);
        new DownloadImageTask(imageView).execute(searchableProductList.items.get(position).imgUrl.get(0));
        return imageView;
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
