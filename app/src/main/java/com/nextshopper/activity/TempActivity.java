package com.nextshopper.activity;

import android.os.Bundle;
import android.widget.TextView;

public class TempActivity extends BaseActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_temp);
        
    }
}
