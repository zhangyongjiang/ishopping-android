package com.nextshopper.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.R;
import com.nextshopper.common.Constant;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.beans.ProductReviewDetails;

import java.io.File;
import java.util.List;

/**
 * Created by siyiliu on 8/18/15.
 */
public class ReviewAdapter extends BaseAdapter {
    private List<ProductReviewDetails> list;
    private Context ctx;
    private int userHeight;


    public ReviewAdapter(List<ProductReviewDetails> list, Context ctx){
        this.list = list;
        this.ctx = ctx;
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
}
