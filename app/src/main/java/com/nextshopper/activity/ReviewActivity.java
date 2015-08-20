package com.nextshopper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.nextshopper.common.Constant;
import com.nextshopper.rest.beans.ProductReview;
import com.nextshopper.rest.beans.ProductReviewDetails;
import com.nextshopper.rest.beans.UserBasicInfo;
import com.nextshopper.view.ReviewAdapter;

import java.util.ArrayList;
import java.util.List;


public class ReviewActivity extends BaseActivity {

    private static String KEY="review_list";
    private ListView listView;
    private int requestCode=0;
    List<ProductReviewDetails> reviewList;
    private ReviewAdapter reviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        listView= (ListView)findViewById(R.id.review_list);
        reviewList= getIntent().getParcelableArrayListExtra(KEY);
        reviewAdapter = new ReviewAdapter(reviewList, this);
        listView.setAdapter(reviewAdapter);
    }

    public static void startReviewActivity(ArrayList<ProductReviewDetails> reviewList, Context ctx){
        Intent intent = new Intent(ctx, ReviewActivity.class);
        intent.putParcelableArrayListExtra(KEY, reviewList);
        ctx.startActivity(intent);
    }

    public void rightOnClick(View view){
        Intent intent = new Intent(this, CreateReviewActivity.class);
        startActivityForResult(intent, requestCode);
    }

    public void onActivityResult(int code, int resultcode, Intent data){
        if(code == requestCode && resultcode ==RESULT_OK){
            ProductReviewDetails d = new ProductReviewDetails();
            d.review = new ProductReview();
            d.review.rating =data.getIntExtra("rating",0);
            d.review.comment =data.getStringExtra("comment");
            SharedPreferences pref = getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
            d.user = new UserBasicInfo();
            d.user.firstName = pref.getString(Constant.FIRST_NAME, "");
            d.user.lastName = pref.getString(Constant.LAST_NAME, "");
            d.review.created=0;
            reviewList.add(0,d);
            reviewAdapter.update(reviewList);
        }

    }

}
