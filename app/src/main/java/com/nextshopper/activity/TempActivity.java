package com.nextshopper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TempActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_temp);
        TextView textView  = (TextView)findViewById(R.id.temp_textView);
        textView.setText("UserId: " + getIntent().getStringExtra("userId"));
    }
}
