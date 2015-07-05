package com.nextshopper.com.nextshopper.activity.home;

import android.os.Bundle;
import android.widget.GridView;

import com.nextshopper.activity.BaseActivity;
import com.nextshopper.activity.R;

/**
 * Created by Zhang_Kevin on 7/4/15.
 */
public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_grid);

        GridView gridView = (GridView)findViewById(R.id.product_grid_view);
    }
}
