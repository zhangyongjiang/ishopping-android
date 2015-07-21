package com.nextshopper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.nextshopper.activity.R;

/**
 * TODO: document your custom view class.
 */
public class CategoryView extends View {
    private String category; // TODO: use a default from R.string...
    private String[] categories;
    private int textColor = Color.BLACK; // TODO: use a default from R.color...
    private float textDimension = 0; // TODO: use a default from R.dimen...
    private Drawable bg;
    private float paddingTop=0;
    private float paddingDown=0;
    private float paddingLeft=0;
    private float paddingRight=0;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public CategoryView(Context context) {
        super(context);
        init(null, 0);
    }

    public CategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CategoryView, defStyle, 0);

        category = a.getString(R.styleable.CategoryView_category);
        categories = category.split(",");

        textColor = a.getColor(R.styleable.CategoryView_textColor, textColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        textDimension = a.getDimension(R.styleable.CategoryView_textSize, textDimension);
        paddingLeft = a.getDimension(R.styleable.CategoryView_paddingLeft,paddingLeft);
        paddingRight = a.getDimension(R.styleable.CategoryView_paddingRight, paddingRight);
        paddingTop = a.getDimension(R.styleable.CategoryView_paddingTop, paddingTop);
        paddingDown = a.getDimension(R.styleable.CategoryView_paddingDown, paddingDown);

        if (a.hasValue(R.styleable.CategoryView_bg)) {
           bg = a.getDrawable(R.styleable.CategoryView_bg);
            bg.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(textDimension);
        mTextPaint.setColor(textColor);
        //mTextPaint.set
        mTextWidth = mTextPaint.measureText(category);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(category,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

    }

}
