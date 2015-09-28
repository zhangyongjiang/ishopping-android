package com.nextshopper.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.R;
import com.nextshopper.common.Constant;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ProductReviewDetails;
import com.nextshopper.rest.beans.ProductReviewDetailsList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 8/18/15.
 */
public class ReviewAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private List<ProductReviewDetails> list = new ArrayList<>();
    private Context ctx;
    private int userHeight;
    private boolean call =true;
    private int start = 0;
    private int numOfItem = 20;
    private ProgressDialog progressDialog;
    private String productId;


    public ReviewAdapter(Context ctx, String prodcutId){
        this.ctx = ctx;
        this.productId = prodcutId;
        this.userHeight = (int)(60 * ctx.getResources().getDisplayMetrics().density);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ProductReviewDetails getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.review_item, parent, false);
        }
        ProductReviewDetails review = getItem(position);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.user_img);
        imageView.setTag(review.user.imgPath);
        if(position!=0) {
            ((NextShopperApplication)ctx.getApplicationContext()).loadBitmaps(review.user.imgPath,imageView, true, userHeight);
           // BitmapWorkerTask task = new BitmapWorkerTask(imageView, true, userHeight);
           // task.execute(review.user.imgPath);
        }else{
            File imageFile = new File(ctx.getDir("imageDir", Context.MODE_PRIVATE), Constant.THUMNAIL);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            if(bitmap!=null)
                 imageView.setImageBitmap(bitmap);
        }
        TextView name = (TextView) convertView.findViewById(R.id.review_user_name);
        name.setText(review.user.firstName + " " + review.user.lastName);
        ImageView ratingView = (ImageView) convertView.findViewById(R.id.review_rating);
        ratingView.setImageResource(getResourceId(review.review.rating));
        TextView comment = (TextView) convertView.findViewById(R.id.review_comment);
        comment.setText(review.review.comment);
        TextView timeView = (TextView) convertView.findViewById(R.id.review_time);
        timeView.setText((System.nanoTime() - review.review.created)+"");
        return convertView;
    }

   private int getResourceId(int rating){
       switch (rating){
           case 0: return R.drawable.start_0;
           case 1: return R.drawable.stars_1;
           case 2: return R.drawable.stars_2;
           case 3: return R.drawable.stars_3;
           case 4: return R.drawable.stars_4;
           case 5: return R.drawable.stars_5;
       }
       return R.drawable.start_0;
    }

    public void update(List<ProductReviewDetails> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void refresh(){
        start =0;
        list.clear();
        call = true;
        notifyDataSetChanged();
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount() && call) {
            call = false;
            if (lastVisibleItem != 0)
                progressDialog = Util.getProgressDialog(ctx);
            ApiService.getService().SocialAPI_GetProductReviews(productId, start, numOfItem, new Callback<ProductReviewDetailsList>() {
                @Override
                public void success(ProductReviewDetailsList list, Response response) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    if (list.items.size() > 0) {
                        ReviewAdapter.this.list.addAll(list.items);
                        notifyDataSetChanged();
                    }
                    if(list.items.size()<numOfItem)
                        call = false;
                    else
                        call =true;
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Util.alertBox(ctx, error);
                }
            });

            start = start + numOfItem;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }
}
