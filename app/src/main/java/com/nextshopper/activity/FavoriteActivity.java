package com.nextshopper.activity;

import android.os.Bundle;
import android.widget.GridView;

import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ListFavoriteDetails;
import com.nextshopper.view.FavoriteAdapter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FavoriteActivity extends BaseActivity {
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        gridView = (GridView) findViewById(R.id.favorite_gridview);
        final FavoriteAdapter adapter = new FavoriteAdapter(this);
        gridView.setAdapter(adapter);
        ApiService.getService().FavoriteAPI_FavoriteList(new Callback<ListFavoriteDetails>() {
            @Override
            public void success(ListFavoriteDetails listFavoriteDetails, Response response) {
                adapter.updateList(listFavoriteDetails.items);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

}
