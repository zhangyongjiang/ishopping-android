package com.nextshopper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.beans.SearchableProduct;
import com.nextshopper.rest.beans.SearchableProductList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siyiliu on 9/25/15.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context ctx;
    private List<ArrayList<SearchableProduct>> searchableProductList = new ArrayList<>();

    public GridViewAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return searchableProductList.size();
    }

    @Override
    public List<SearchableProduct> getItem(int position) {
        return searchableProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final List<SearchableProduct> spList = searchableProductList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_row, parent, false);
        }
        ItemView left = (ItemView) convertView.findViewById(R.id.item_left_view);
        ItemView right = (ItemView) convertView.findViewById(R.id.item_right_view);
        left.setValue(spList.get(0));
        if(spList.size()==2){
            right.setValue(spList.get(1));
        }
        return convertView;
    }

    void setImageView(ImageView imageView, String url) {
        ((NextShopperApplication) ctx.getApplicationContext()).loadBitmaps(url, imageView, false, 0);
    }

    public void updateList(SearchableProductList list) {
        List<ArrayList<SearchableProduct>> result = new ArrayList<>();
        ArrayList<SearchableProduct> spList = null;
        for(int i=0; i<list.items.size(); i++){
            if(i%2==0){
                spList = new ArrayList<>();
                spList.add(list.items.get(i));
                if(i==list.items.size()-1){
                    result.add(spList);
                }
            }else{
                spList.add(list.items.get(i));
                result.add(spList);
            }
        }
        searchableProductList = result;
        notifyDataSetChanged();
    }
}
