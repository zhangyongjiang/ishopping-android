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
public class MsgListFragment extends SwipeFragment {
    private MessageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        //View view=  inflater.inflate(R.layout.fragment_msg_list, container, false);
        ListView listview = (ListView)view.findViewById(R.id.listview);
        adapter = new MessageAdapter(getActivity(), listview);
        listview.setAdapter(adapter);
        listview.setOnScrollListener(adapter);
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
