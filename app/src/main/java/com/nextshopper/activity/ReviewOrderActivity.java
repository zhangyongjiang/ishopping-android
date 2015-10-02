package com.nextshopper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ProductReview;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ReviewOrderActivity extends BaseActivity {
    private RatingBar serviceRatingView;
    private RatingBar productRatingView;
    private EditText  reviewView;
    private float serviceRating;
    private float productRating;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        productId = getIntent().getStringExtra(Constant.PRODUCT_ID);
        reviewView =(EditText) findViewById(R.id.review_order_comment);
        serviceRatingView =(RatingBar) findViewById(R.id.service_ratingBar);
        serviceRatingView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                serviceRating = rating;
            }
        });
        productRatingView =(RatingBar) findViewById(R.id.product_ratingBar);
        productRatingView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                productRating = rating;
            }
        });
    }

    public void rightOnClick(View view){
        ProductReview productReview = new ProductReview();
        productReview.rating = (int)productRating;
        productReview.serviceRating =(int)serviceRating;
        productReview.comment = reviewView.getText().toString();
        productReview.productId = productId;
        ProgressBar progressBar = Util.getProgressBar(this);
        ApiService.getService().SocialAPI_ReviewProduct(productReview, new Callback<ProductReview>() {
            @Override
            public void success(ProductReview productReview, Response response) {

                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Util.alertBox(ReviewOrderActivity.this,error);

            }
        });

    }

}
