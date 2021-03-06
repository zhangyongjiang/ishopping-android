package com.nextshopper.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 9/13/15.
 */
public class FollowedStoreAdapter extends NextShopperAdapter<StoreFollowDetails> implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener{
    private Context ctx;
    private ListView listView;

    public FollowedStoreAdapter(Context ctx){
        this.ctx = ctx;
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
        if (lastVisibleItem == this.getCount() && call) {
            call = false;
            final ProgressDialog progressDialog= Util.getProgressDialog(ctx);
            ApiService.getService().SocialAPI_MyFollowingStores(start, numOfItem, new Callback<ListFollowingStore>() {
                @Override
                public void success(ListFollowingStore listFollowingStore, Response response) {
                    progressDialog.dismiss();
                    list.addAll(listFollowingStore.items);
                    if(listFollowingStore.items.size()==numOfItem)
                        call =true;
                    if(listFollowingStore.items.size()>0)
                    notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
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
