package com.nextshopper.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.nextshopper.activity.R;

public class TrendingFragment extends Fragment {
    private ProductGridAdapter adapter;
    private GridView gridView;


    public static TrendingFragment newInstance(String param1) {
        TrendingFragment fragment = new TrendingFragment();
        Bundle args = new Bundle();
        args.putString("Tab", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab_trending, container, false);
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
