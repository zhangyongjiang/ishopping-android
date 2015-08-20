package com.nextshopper.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class CreateReviewActivity extends BaseActivity implements View.OnClickListener{

    private ImageView startView;
    private Rect rectf;
    private int oneStart;
    private EditText comments;
    private int rating;
    private Rect localRect;
    private Rect gr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);
        startView = (ImageView) findViewById(R.id.star_rating);
        startView.setOnClickListener(this);
        comments = (EditText) findViewById(R.id.create_review_comment);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        if(event.getAction()==MotionEvent.ACTION_DOWN){
          if(gr!=null && x>=gr.left && x<=gr.right&& y>=gr.top && y<=gr.bottom+100)
              if(y>=gr.top && y<gr.top+ (gr.bottom+100-gr.top)/5) {
                  startView.setImageResource(R.drawable.stars_1);
                  rating=1;
              }
              else if(y>=gr.top +(gr.bottom+100-gr.top)/5 && y<gr.top+ (gr.bottom+100-gr.top)/5*2){
                  startView.setImageResource(R.drawable.stars_2);
                  rating=2;
              }else if(y>=gr.top +(gr.bottom+100-gr.top)/5*2 && y<gr.top+ (gr.bottom+100-gr.top)/5*3){
                  startView.setImageResource(R.drawable.stars_3);
                  rating=3;
              }else if(y>=gr.top +(gr.bottom+100-gr.top)/5*3 && y<gr.top+ (gr.bottom+100-gr.top)/5*4){
                  startView.setImageResource(R.drawable.stars_4);
                  rating=4;
              }else if(y>=gr.top +(gr.bottom+100-gr.top)/5*4 && y<gr.top+ (gr.bottom+100-gr.top)/5*5){
                  startView.setImageResource(R.drawable.stars_5);
                  rating=5;
              }
        }

        return false;
    }


    @Override
    public void onClick(View v) {
        localRect = new Rect();
        startView.getLocalVisibleRect(localRect);
        gr = new Rect();
        startView.getGlobalVisibleRect(gr);
    }

    public void rightOnClick(View view){
        Intent intent = new Intent();
        intent.putExtra("comment", comments.getText().toString());
        intent.putExtra("rating", rating);
        setResult(RESULT_OK, intent);
        finish();
    }
}
