package com.nextshopper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nextshopper.activity.R;


public class InputItem extends FrameLayout {

   private TextView field;
    private EditText editText;

    public InputItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.input_item, this);
        field = (TextView) findViewById(R.id.input_view);
        editText = (EditText) findViewById(R.id.input_edit);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.InputItem);
        field.setText(ta.getString(R.styleable.InputItem_field));
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(String text) {
        this.editText.setText(text);
    }

    public void setEditTextHint(String hint){
        this.editText.setHint(hint);
    }
    
}