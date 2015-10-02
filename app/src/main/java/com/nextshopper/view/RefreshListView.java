package com.nextshopper.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.nextshopper.activity.R;

/**
 * Created by siyiliu on 9/30/15.
 */
public class RefreshListView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private NextShopperAdapter adapter;
    private ListView listView;

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.refresh_listview, this);
        swipeRefreshLayout =(SwipeRefreshLayout) findViewById(R.id.refreshlistview);
        listView =(ListView)findViewById(R.id.listview);
      //  swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void setAdapter(NextShopperAdapter adapter) {
        listView.setAdapter(adapter);
        this.adapter = adapter;
    }

    @Override
    public void onRefresh() {
        adapter.refresh();
        swipeRefreshLayout.setRefreshing(false);
    }

    public ListView getListView(){
        return listView;
    }
}
