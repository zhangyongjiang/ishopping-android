package com.nextshopper.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.nextshopper.activity.R;

public class TrendingFragment extends SwipeFragment {
    private ProductGridAdapter adapter;
    private GridView gridView;
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
    protected int getLayoutId() {
        return R.layout.fragment_tab_trending;
    }

    @Override
    protected void refresh() {
       adapter.refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //View view = inflater.inflate(R.layout.fragment_tab_trending, container, false);
        gridView = (GridView) view.findViewById(R.id.product_grid_view);
        adapter = new ProductGridAdapter(this.getActivity(), gridView, this);
        gridView.setAdapter(adapter);
        gridView.setOnScrollListener(adapter);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
