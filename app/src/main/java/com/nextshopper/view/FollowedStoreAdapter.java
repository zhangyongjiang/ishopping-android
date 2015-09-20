package com.nextshopper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nextshopper.activity.R;
import com.nextshopper.activity.StoreDetailsActivity;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ListFollowingStore;
import com.nextshopper.rest.beans.StoreFollowDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 9/13/15.
 */
public class FollowedStoreAdapter extends BaseAdapter implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener{
    private Context ctx;
    private List<StoreFollowDetails> list = new ArrayList<>();
    private int start = 0;
    private int numOfItem = 20;
    private ListView listView;

    public FollowedStoreAdapter(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StoreFollowDetails getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreFollowDetails item = list.get(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.following_item, parent,false);
        }
        ImageView logoView = (ImageView)convertView.findViewById(R.id.following_store_logo);
        logoView.setTag(item.store.logo);
        ((NextShopperApplication) ctx.getApplicationContext()).loadBitmaps(item.store.logo, logoView, false, 0);
        TextView nameView = (TextView) convertView.findViewById(R.id.following_store_name);
        nameView.setText(item.store.name);
        TextView proAndFollView = (TextView) convertView.findViewById(R.id.following_store_pf);
        proAndFollView.setText(String.format(ctx.getResources().getString(R.string.product_followers), item.storeSummary.products, item.storeSummary.followers));
        return convertView;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount()) {

            ApiService.getService().SocialAPI_MyFollowingStores(start, numOfItem, new Callback<ListFollowingStore>() {
                @Override
                public void success(ListFollowingStore listFollowingStore, Response response) {
                    list.addAll(listFollowingStore.items);
                    notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    Util.alertBox(ctx, error);
                }
            });
            start = start + numOfItem;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StoreDetailsActivity.startActivity(ctx, list.get(position).storeFollow.storeId);
    }
}
