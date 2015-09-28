package com.nextshopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.nextshopper.rest.beans.ProductReviewDetails;
import com.nextshopper.view.ReviewAdapter;

import java.util.List;


public class ReviewActivity extends BaseActivity {

    private static String KEY="review_list";
    private ListView listView;
    private int requestCode=0;
    private List<ProductReviewDetails> reviewList;
    private ReviewAdapter reviewAdapter;
    private int newreview;
    private int rating;
    private static String PRODUCT_ID="productId";
    private String productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        listView= (ListView)findViewById(R.id.review_list);
        productId = getIntent().getStringExtra(PRODUCT_ID);
        //reviewList= getIntent().getParcelableArrayListExtra(KEY);
        reviewAdapter = new ReviewAdapter(this, productId);
        listView.setAdapter(reviewAdapter);
        listView.setOnScrollListener(reviewAdapter);
    }



    public static void startReviewActivity(String productId, ProductDetailsActivity ctx, int requestCode){
        Intent intent = new Intent(ctx, ReviewActivity.class);
        //intent.putParcelableArrayListExtra(KEY, reviewList);
        intent.putExtra(PRODUCT_ID, productId);
        ctx.startActivityForResult(intent, requestCode, null);
    }

    public void rightOnClick(View view){
        CreateReviewActivity.startActivityForResult(this, productId, requestCode);
    }

    public void onActivityResult(int code, int resultcode, Intent data){
        if(code == requestCode && resultcode ==RESULT_OK){
            newreview++;
            rating = data.getIntExtra("rating",0);

            /*
            ProductReviewDetails d = new ProductReviewDetails();
            d.review = new ProductReview();
            d.review.rating =data.getIntExtra("rating",0);
            rating = d.review.rating;
            d.review.comment =data.getStringExtra("comment");
            SharedPreferences pref = getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
            d.user = new UserBasicInfo();
            d.user.firstName = pref.getString(Constant.FIRST_NAME, "");
            d.user.lastName = pref.getString(Constant.LAST_NAME, "");
            d.review.created=0;
            reviewList.add(0,d);*/
            reviewAdapter.refresh();
        }

    }
    public void update(){
        Intent intent = new Intent();
        intent.putExtra("num_of_review",newreview);
        intent.putExtra("rating",rating);
        setResult(RESULT_OK, intent);
        finish();
    }
}
