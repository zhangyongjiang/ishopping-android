package com.nextshopper.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

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
    private List<StoreFollowDetails> messageItemDetailsList = new ArrayList<>();
    private int start = 0;
    private int numOfItem = 20;
    private ListView listView;

    public FollowedStoreAdapter(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount()) {

            ApiService.getService().SocialAPI_MyFollowingStores(start, numOfItem, new Callback<ListFollowingStore>() {
                @Override
                public void success(ListFollowingStore listFollowingStore, Response response) {
                    messageItemDetailsList.addAll(listFollowingStore.items);
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
       // OrderDetailsActivity.startActivity(ctx, getItem(position).message.id);
    }
}
