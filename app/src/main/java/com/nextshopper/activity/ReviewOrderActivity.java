package com.nextshopper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class ReviewOrderActivity extends BaseActivity {
    private ImageView serviceView;
    private ImageView productView;
    private EditText  reviewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        reviewView =(EditText) findViewById(R.id.review_order_comment);
    }

    public void rightOnClick(View view){

    }

}
