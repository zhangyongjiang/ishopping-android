package com.nextshopper.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.ProductDetailsActivity;
import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.FavoriteDetails;
import com.nextshopper.rest.beans.ListFavoriteDetails;
import com.nextshopper.rest.beans.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 9/13/15.
 */
public class FavoriteAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private Context ctx;
    private int start = 0;
    private int numOfItem = 20;
    private List<FavoriteDetails> list = new ArrayList<>();

    public FavoriteAdapter(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FavoriteDetails getItem(int position) {
        return list.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = list.get(position).product;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false);
        }

        TextView textViewDisPrice = (TextView) convertView.findViewById(R.id.item_disount_price);
        textViewDisPrice.setText("$" + Math.round(product.salePrice * 100) / 100.0);
        TextView textViewPrice = (TextView) convertView.findViewById(R.id.item_ori_price);
        textViewPrice.setText("$" + Math.round(product.listPrice * 100) / 100.0);
        TextView textViewName = (TextView) convertView.findViewById(R.id.item_name);
        textViewName.setText(product.name);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ProductDetailsActivity.class);
                intent.putExtra("productId", product.id);
                Log.d("NextShopper", product.imgs.get(0));
                ctx.startActivity(intent);
            }
        };
        //imageView.setTag(sp.imgUrl.get(0));
        imageView.setImageBitmap(null);
        setImageView(imageView, product.imgs.get(0));
        convertView.setOnClickListener(listener);
        //new DownloadImageTask(imageView).execute(sp.imgUrl.get(0));
        return convertView;
    }

    public void updateList(List<FavoriteDetails> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    void setImageView(ImageView imageView, String url) {
        ((NextShopperApplication)ctx.getApplicationContext()).loadBitmaps(url, imageView, false, 0);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount()) {
            final ProgressDialog progressDialog= Util.getProgressDialog(ctx);
            ApiService.getService().SocialAPI_ListMyFavProducts(start, numOfItem, new Callback<ListFavoriteDetails>() {
                @Override
                public void success(ListFavoriteDetails listFavoriteDetails, Response response) {
                    progressDialog.dismiss();
                    FavoriteAdapter.this.list.addAll(listFavoriteDetails.items);
                    notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();Util.alertBox(ctx, error);
                }
            });

            start = start + numOfItem;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

}
