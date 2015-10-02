package com.nextshopper.view;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siyiliu on 9/30/15.
 */
public abstract class NextShopperAdapter<T> extends BaseAdapter {

    List<T> list = new ArrayList<>();
    boolean call =true;
    int start = 0;
    int numOfItem = 20;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void refresh(){
        start =0;
        list.clear();
        call = true;
        notifyDataSetChanged();
    }

}
