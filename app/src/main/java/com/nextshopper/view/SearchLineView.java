package com.nextshopper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextshopper.activity.R;


public class SearchLineView extends LinearLayout{
    private TextView textViewleft;
    private TextView textViewmiddle;
    private TextView textViewright;

    public SearchLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.search_line_view, this);
        textViewleft = (TextView) findViewById(R.id.search_line_left);
        textViewmiddle = (TextView) findViewById(R.id.search_line_middle);
        textViewright = (TextView) findViewById(R.id.search_line_right);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SearchLineView);
        textViewleft.setText(ta.getString(R.styleable.SearchLineView_left));
        textViewmiddle.setText(ta.getString(R.styleable.SearchLineView_middle));
        textViewright.setText(ta.getString(R.styleable.SearchLineView_right));
        ta.recycle();
    }


}
