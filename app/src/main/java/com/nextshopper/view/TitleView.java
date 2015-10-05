package com.nextshopper.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.HomeActivity;
import com.nextshopper.activity.R;
import com.nextshopper.activity.ReviewActivity;


public class TitleView extends FrameLayout implements View.OnClickListener{

    private ImageView imageLeft;
    private TextView textMiddle;
    private TextView textRight;
    private ImageView imageRight;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_view, this);
        imageLeft = (ImageView) findViewById(R.id.tile_image_left);
        textMiddle = (TextView) findViewById(R.id.tile_text_middle);
        textRight = (TextView) findViewById(R.id.tile_text_right);
        imageRight = (ImageView) findViewById(R.id.titile_image_right);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        imageLeft.setImageDrawable(ta.getDrawable(R.styleable.TitleView_imageLeft));
        imageLeft.setOnClickListener(this);
        textMiddle.setText(ta.getString(R.styleable.TitleView_textMiddle));
        textMiddle.setTextColor(ta.getColor(R.styleable.TitleView_textMiddleColor, Color.WHITE));
        textRight.setText(ta.getString(R.styleable.TitleView_textRight));
        textRight.setTextColor(ta.getColor(R.styleable.TitleView_textRightColor, Color.WHITE));
        imageRight.setImageDrawable(ta.getDrawable(R.styleable.TitleView_imageRight));
        ta.recycle();
    }

    @Override
    public void onClick(View v) {
        Activity activity = ((Activity) v.getContext());
        if(! (activity instanceof HomeActivity) && !(activity instanceof ReviewActivity)){
            activity.finish();
        }else if((activity instanceof ReviewActivity)){
            ((ReviewActivity)activity).update();
        }
        else{
            ((HomeActivity) activity).getmNavigationDrawerFragment().getmDrawerLayout().openDrawer(Gravity.START);
        }
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

    public void setTextMiddle(String text) {
        this.textMiddle.setText(text);
    }

    public TextView getTextRight() {
        return textRight;
    }

    public void setTextRight(TextView textRight) {
        this.textRight = textRight;
    }

    public ImageView getImageRight() {
        return imageRight;
    }

    public void setImageRight(int id) {
        this.imageRight.setImageResource(id);
    }
}