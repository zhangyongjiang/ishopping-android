package com.nextshopper.activity;

import android.os.Bundle;

import com.nextshopper.view.FollowedStoreAdapter;


public class FollowedStoreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followed_store);
        //listView = (ListView) findViewById(R.id.following_listview);
        FollowedStoreAdapter adapter = new FollowedStoreAdapter(this);
        //listView.setAdapter(adapter);
    }

}
