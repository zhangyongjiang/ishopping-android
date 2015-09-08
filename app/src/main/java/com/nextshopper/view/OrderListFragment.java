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
public class OrderListFragment extends Fragment {
    private ListView listView;


    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.order_history_list, container, false);
        listView = (ListView) view.findViewById(R.id.order_history_listview);
         OrderHistoryAdapter adapter  =new OrderHistoryAdapter(getActivity(), listView);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(adapter);
        return view;
    }
}
