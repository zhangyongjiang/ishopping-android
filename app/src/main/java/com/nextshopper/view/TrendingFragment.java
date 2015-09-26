package com.nextshopper.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.nextshopper.activity.R;

public class TrendingFragment extends Fragment {
    private ProductGridAdapter adapter;
    private GridView gridView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String cat;
    private String keywords;


    public static TrendingFragment newInstance(String tab, String cat, String keywords, String prodcutId) {
        TrendingFragment fragment = new TrendingFragment();
        Bundle args = new Bundle();
        args.putString("Tab", tab);
        args.putString("Cat", cat);
        args.putString("Keywords", keywords);
        args.putString("ProductId", prodcutId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab_trending, container, false);
        swipeRefreshLayout =(SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                 android.R.color.holo_green_light,
                 android.R.color.holo_orange_light,
                 android.R.color.holo_red_light);

        gridView = (GridView) view.findViewById(R.id.product_grid_view);
        adapter = new ProductGridAdapter(this.getActivity(), gridView, this);
        gridView.setAdapter(adapter);
        gridView.setOnScrollListener(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               adapter.refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
