package com.nextshopper.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ListFavoriteDetails;
import com.nextshopper.view.FavoriteAdapter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FavoriteActivity extends BaseActivity {
    private GridView gridView;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        gridView = (GridView) findViewById(R.id.favorite_gridview);
        imageView =(ImageView) findViewById(R.id.empty_img);
        textView =(TextView) findViewById(R.id.empty_text);
        final FavoriteAdapter adapter = new FavoriteAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnScrollListener(adapter);
        final ProgressDialog progressDialog= Util.getProgressDialog(this);
        ApiService.getService().SocialAPI_ListMyFavProducts(0, 5, new Callback<ListFavoriteDetails>() {
            @Override
            public void success(ListFavoriteDetails listFavoriteDetails, Response response) {
                progressDialog.dismiss();
                if(listFavoriteDetails.total==0){
                    gridView.setVisibility(View.GONE);
                }else{
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Util.alertBox(FavoriteActivity.this, error);
            }
        });

    }

}
