package com.nextshopper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.nextshopper.activity.R;


public class InputItem extends FrameLayout {

   private EditText item;

    public InputItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.input_item, this);
        item = (EditText) findViewById(R.id.input_edit);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.InputItem);
        item.setText(ta.getString(R.styleable.InputItem_edit));
    }
}