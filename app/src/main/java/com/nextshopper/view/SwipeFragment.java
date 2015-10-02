package com.nextshopper.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextshopper.activity.R;

public abstract class SwipeFragment extends Fragment{
    protected View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    protected abstract int getLayoutId();
    protected abstract void refresh();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(getLayoutId(), container, false);
        swipeRefreshLayout =(SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

}
