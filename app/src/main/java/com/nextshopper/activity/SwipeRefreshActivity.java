package com.nextshopper.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Zhang_Kevin on 7/5/15.
 */
public abstract class SwipeRefreshActivity extends BaseActivity {

    protected SwipeRefreshLayout swipeRefreshLayout;
    protected abstract void refresh();
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setRefresh();
    }

    public void setRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
