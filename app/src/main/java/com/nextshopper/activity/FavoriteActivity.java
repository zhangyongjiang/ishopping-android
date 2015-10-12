package com.nextshopper.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ListFavoriteDetails;
import com.nextshopper.view.TrendingFragment;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FavoriteActivity extends BaseActivity {
    private ImageView imageView;
    private TextView textView;
    private View containerView;

   // @Override
    protected void refresh() {
        final ProgressDialog progressDialog= Util.getProgressDialog(this);
        ApiService.getService().SocialAPI_ListMyFavProducts(0, 5, new Callback<ListFavoriteDetails>() {
            @Override
            public void success(ListFavoriteDetails listFavoriteDetails, Response response) {
                progressDialog.dismiss();
                if(listFavoriteDetails.total==0){
                    containerView.setVisibility(View.GONE);
                }else{
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().add(R.id.favorite_container, TrendingFragment.newInstance("favorite", null, null, null)).commit();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Util.alertBox(FavoriteActivity.this, error);
            }
        });

    }

   // @Override
   // protected int getLayoutId() {
   //     return R.layout.activity_favorite;
   // }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        containerView = findViewById(R.id.favorite_container);
        imageView =(ImageView) findViewById(R.id.empty_img);
        textView =(TextView) findViewById(R.id.empty_text);
        refresh();
        //final FavoriteAdapter adapter = new FavoriteAdapter(this);
       // gridView.setAdapter(adapter);
        //gridView.setOnScrollListener(adapter);
    }

}
