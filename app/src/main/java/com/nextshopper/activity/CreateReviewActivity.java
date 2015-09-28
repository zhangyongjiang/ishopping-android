package com.nextshopper.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ProductReview;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CreateReviewActivity extends BaseActivity{

    private RatingBar startView;
    private Rect rectf;
    private int oneStart;
    private EditText comments;
    private float rating;
    private Rect localRect;
    private Rect gr;
    private String productId;
    private static String PRODUCT_ID="product_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);
        startView = (RatingBar) findViewById(R.id.star_rating);
        comments = (EditText) findViewById(R.id.create_review_comment);
        productId = getIntent().getStringExtra(PRODUCT_ID);
        startView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                CreateReviewActivity.this.rating= rating;
            }
        });

    }

    public static void startActivityForResult(Activity acitivity, String productId, int requestCode){
        Intent intent = new Intent(acitivity, CreateReviewActivity.class);
        intent.putExtra(PRODUCT_ID, productId);
        acitivity.startActivityForResult(intent, requestCode);
    }

    public void rightOnClick(View view){
        if((int)rating==0){
            Util.alertBox(this, "Rating must between 1 and 5");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("comment", comments.getText().toString());
        intent.putExtra("rating", (int)rating);
        intent.putExtra("num_of_review",1);
        ProductReview productView = new ProductReview();
        productView.rating = (int)rating;
        productView.comment = comments.getText().toString();
        productView.productId = productId;
        ApiService.getService().SocialAPI_ReviewProduct(productView, new Callback<ProductReview>() {
            @Override
            public void success(ProductReview productReview, Response response) {
                Log.d(Constant.NEXTSHOPPER, productReview.id);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(Constant.NEXTSHOPPER, error.getMessage());
            }
        });
        setResult(RESULT_OK, intent);
        finish();
    }
}
