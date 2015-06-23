package com.nextshopper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.R;


public class TitleView extends FrameLayout {

    private ImageView imageLeft;
    private TextView textMiddle;
    private TextView textRight;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_view, this);
        imageLeft = (ImageView) findViewById(R.id.tile_image_left);
        textMiddle = (TextView) findViewById(R.id.tile_text_middle);
        textRight = (TextView) findViewById(R.id.tile_text_right);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        imageLeft.setImageDrawable(ta.getDrawable(R.styleable.TitleView_imageLeft));
        textMiddle.setText(ta.getString(R.styleable.TitleView_textMiddle));
        textRight.setText(ta.getString(R.styleable.TitleView_textRight));
    }

    public ImageView getImageLeft() {
        return imageLeft;
    }

    public void setImageLeft(ImageView imageLeft) {
        this.imageLeft = imageLeft;
    }

    public TextView getTextMiddle() {
        return textMiddle;
    }

    public void setTextMiddle(TextView textMiddle) {
        this.textMiddle = textMiddle;
    }

    public TextView getTextRight() {
        return textRight;
    }

    public void setTextRight(TextView textRight) {
        this.textRight = textRight;
    }
}