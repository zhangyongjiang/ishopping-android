package com.nextshopper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nextshopper.activity.R;


public class SettingItem extends FrameLayout {

   private TextView item;

    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.setting_item, this);
        item = (TextView) findViewById(R.id.setting_item);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingItem);
        item.setText(ta.getString(R.styleable.SettingItem_item));
    }

    public void setItem(String text){

        item.setText(text);
        item.setMaxLines(3);
    }

}