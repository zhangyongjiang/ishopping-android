package com.nextshopper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextshopper.activity.R;


public class SearchLineForthView extends LinearLayout {
    private TextView textViewFirst;
    private TextView textViewSec;
    private TextView textViewThird;
    private TextView textViewForth;

    public SearchLineForthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.search_line__forth_view, this);
        textViewFirst = (TextView) findViewById(R.id.search_line_first);
        textViewSec = (TextView) findViewById(R.id.search_line_sec);
        textViewThird = (TextView) findViewById(R.id.search_line_third);
        textViewForth = (TextView) findViewById(R.id.search_line_forth);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SearchLineForthView);
        textViewFirst.setText(ta.getString(R.styleable.SearchLineForthView_first));
        textViewSec.setText(ta.getString(R.styleable.SearchLineForthView_sec));
        textViewThird.setText(ta.getString(R.styleable.SearchLineForthView_third));
        textViewForth.setText(ta.getString(R.styleable.SearchLineForthView_forth));
        if (ta.getString(R.styleable.SearchLineForthView_third) == null) {
            textViewThird.setVisibility(GONE);
            if (ta.getString(R.styleable.SearchLineForthView_forth) == null)
                textViewForth.setVisibility(INVISIBLE);
            ta.recycle();
        }


    }
}
