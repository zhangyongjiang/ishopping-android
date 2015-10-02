package com.nextshopper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ListFollowingStore;
import com.nextshopper.view.FollowedStoreAdapter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FollowedStoreActivity extends SwipeRefreshActivity {
    private ListView listView;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void refresh() {
        ApiService.getService().SocialAPI_MyFollowingStores(0, 5, new Callback<ListFollowingStore>() {
            @Override
            public void success(ListFollowingStore listFollowingStore, Response response) {
                if(listFollowingStore.total==0){
                    listView.setVisibility(View.GONE);
                }else{
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.alertBox(FollowedStoreActivity.this, error);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_followed_store;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_followed_store);
        listView = (ListView) findViewById(R.id.following_listview);
        imageView =(ImageView) findViewById(R.id.following_empty_img);
        textView =(TextView) findViewById(R.id.following_empty_text);
        FollowedStoreAdapter adapter = new FollowedStoreAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(adapter);
        listView.setOnItemClickListener(adapter);
        refresh();

    }

}
