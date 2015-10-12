package com.nextshopper.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nextshopper.activity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends SwipeFragment {
    private ListView listView;
    private OrderHistoryAdapter adapter;


    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listview);
        adapter  =new OrderHistoryAdapter(getActivity(), listView);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(adapter);
        //((HomeActivity)getActivity()).refresh(view, adapter);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_view;
    }

    @Override
    protected void refresh() {
      adapter.refresh();
    }
}
