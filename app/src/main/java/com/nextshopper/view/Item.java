package com.nextshopper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nextshopper.activity.R;


public class Item extends FrameLayout {

   private TextView left;
    private TextView right;

    public Item(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item, this);
        left = (TextView) findViewById(R.id.left_value);
        right = (TextView) findViewById(R.id.right_value);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Item);
        left.setText(ta.getString(R.styleable.Item_left_value));
        right.setText(ta.getString(R.styleable.Item_right_value));
    }

    public void setRight(String text){
      right.setText(text);
    }
}