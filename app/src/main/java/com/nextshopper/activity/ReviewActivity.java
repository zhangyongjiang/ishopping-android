package com.nextshopper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.nextshopper.rest.beans.ProductReviewDetails;
import com.nextshopper.view.ReviewAdapter;

import java.util.ArrayList;
import java.util.List;


public class ReviewActivity extends BaseActivity {

    private static String KEY="review_list";
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        listView= (ListView)findViewById(R.id.review_list);
        List<ProductReviewDetails> reviewList= getIntent().getParcelableArrayListExtra(KEY);
        listView.setAdapter(new ReviewAdapter(reviewList, this));
    }

    public static void startReviewActivity(ArrayList<ProductReviewDetails> reviewList, Context ctx){
        Intent intent = new Intent(ctx, ReviewActivity.class);
        intent.putParcelableArrayListExtra(KEY, reviewList);
        ctx.startActivity(intent);
    }

}
