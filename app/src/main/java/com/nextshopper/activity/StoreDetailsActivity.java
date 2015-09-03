package com.nextshopper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.BitmapWorkerTask;
import com.nextshopper.rest.beans.GenericResponse;
import com.nextshopper.rest.beans.StoreDetails;
import com.nextshopper.view.TrendingFragment;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class StoreDetailsActivity extends BaseActivity implements View.OnClickListener {
    private static String STORE_ID = "store_id";
    private View storeInfo;
    private ImageView storeLogoView;
    private TextView storeNameView;
    private TextView storeProductView;
    private TextView storeFollowView;
    private String storeId;
    private TextView storeContactInfoView;
    private boolean followed;
    private StoreDetails storeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        storeInfo = findViewById(R.id.store_info);
        storeNameView = (TextView) findViewById(R.id.store_name);
        storeLogoView = (ImageView) findViewById(R.id.store_logo);
        storeProductView = (TextView) findViewById(R.id.store_products);
        storeFollowView = (TextView) findViewById(R.id.store_follow);
        storeFollowView.setOnClickListener(this);
        storeContactInfoView = (TextView) findViewById(R.id.store_contact_info);
        storeContactInfoView.setOnClickListener(this);
        storeId = getIntent().getStringExtra(STORE_ID);
        ApiService.getService().StoreAPI_GetStoreDetails(storeId, new Callback<StoreDetails>() {
            @Override
            public void success(StoreDetails storeDetails, Response response) {
                StoreDetailsActivity.this.storeDetails = storeDetails;
                storeInfo.setTag(storeDetails.store.info.logo);
                BitmapWorkerTask task = new BitmapWorkerTask(storeInfo, false, 0);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, storeDetails.store.info.logo);
                storeNameView.setText(storeDetails.store.info.name);
                ((NextShopperApplication) getApplication()).loadBitmaps(storeDetails.store.info.logo, storeLogoView, false, 0);
                storeProductView.setText(String.format(getResources().getString(R.string.product_followers), storeDetails.summary.products, storeDetails.summary.followers));
                getSupportFragmentManager().beginTransaction().add(R.id.store_fragment, TrendingFragment.newInstance("Store", null, null, storeId)).commit();
                if (storeDetails.followed) {
                    storeFollowView.setText(getString(R.string.following));
                    followed = true;
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public static void startActivity(Context ctx, String storeId) {
        Intent intent = new Intent(ctx, StoreDetailsActivity.class);
        intent.putExtra(STORE_ID, storeId);
        ctx.startActivity(intent);
    }

    public void rightOnClick(View view){
        Util.share(this, storeDetails.store.info.name, ((BitmapDrawable)storeInfo.getBackground()).getBitmap());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.store_follow) {
            if (!followed) {
                storeFollowView.setText(getString(R.string.following));
                followed = true;
                ApiService.getService().SocialAPI_FollowStore(storeId, new Callback<GenericResponse>() {
                    @Override
                    public void success(GenericResponse genericResponse, Response response) {
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            } else{
                storeFollowView.setText(getString(R.string.follow));
                followed = false;
                ApiService.getService().SocialAPI_UnfollowStore(storeId, new Callback<GenericResponse>() {
                    @Override
                    public void success(GenericResponse genericResponse, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        }else if(v.getId() == R.id.store_contact_info){
            Intent intent = new Intent(this, ContactSellerActivity.class);
            startActivity(intent);
        }
    }
}
