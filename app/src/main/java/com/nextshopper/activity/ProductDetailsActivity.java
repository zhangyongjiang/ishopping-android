package com.nextshopper.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.common.Constant;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.BitmapWorkerTask;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.ProductDetails;
import com.nextshopper.view.ImageFragment;
import com.nextshopper.view.SettingItem;
import com.nextshopper.view.TrendingFragment;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProductDetailsActivity extends SwipeRefreshActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private TextView likesView;
    private TextView nameView;
    private TextView priceView;
    private TextView oriPriceView;
    private Button buyNowButton;
    private TextView specView;
    private SettingItem reviewView;
    private TextView followerView;
    private TextView storeNameView;
    private ImageView storeLogoView;
    private View supportView;
    private View detailsSpecView;
    private ProductDetails details = new ProductDetails();
    private ScreenSlidePagerAdapter adapter;
    private boolean like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        likesView = (TextView) findViewById(R.id.details_like);
        nameView = (TextView) findViewById(R.id.details_name);
        priceView = (TextView) findViewById(R.id.details_price);
        oriPriceView = (TextView) findViewById(R.id.details_org_price);
        buyNowButton = (Button) findViewById(R.id.details_buy_now);
        buyNowButton.setOnClickListener(this);
        specView = (TextView) findViewById(R.id.spec_details);
        reviewView = (SettingItem) findViewById(R.id.details_review);
        followerView = (TextView) findViewById(R.id.details_follower);
        storeNameView = (TextView) findViewById(R.id.details_store_name);
        storeLogoView = (ImageView) findViewById(R.id.details_store_logo);
        supportView = findViewById(R.id.details_support);
        supportView.setOnClickListener(this);
        detailsSpecView = findViewById(R.id.details_spec);
        detailsSpecView.setOnClickListener(this);
        View likeView = findViewById(R.id.details_like);
        likeView.setOnClickListener(this);
        refresh();
    }

    protected void refresh() {
        String productId = getIntent().getStringExtra("productId");
        NextShopperService service = ApiService.getService();
        service.ProductAPI_Get(productId, new Callback<ProductDetails>() {
            @Override
            public void success(ProductDetails productDetails, Response response) {
                adapter.addAll(productDetails);
                likesView.setText(Integer.toString(productDetails.product.likes));
                nameView.setText(productDetails.product.name);
                priceView.setText(String.format(getResources().getString(R.string.salesprice), productDetails.product.salePrice));
                oriPriceView.setText(String.format(getResources().getString(R.string.listprice), productDetails.product.listPrice));
                specView.setText( productDetails.product.description);
                reviewView.setItem(String.format(getResources().getString(R.string.review), productDetails.product.reviews));
                followerView.setText(String.format(getResources().getString(R.string.product_followers), productDetails.storeDetails.summary.products, productDetails.storeDetails.summary.followers));
                getSupportFragmentManager().beginTransaction().add(R.id.details_fragment, TrendingFragment.newInstance("Product", null, null, productDetails.product.id)).commit();
                storeNameView.setText(productDetails.storeDetails.store.info.name);
                storeLogoView.setTag(productDetails.storeDetails.store.info.logo);
                like = productDetails.liked;
                if (like)
                    likesView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_color_full, 0, 0, 0);
                BitmapWorkerTask task = new BitmapWorkerTask(storeLogoView);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, productDetails.storeDetails.store.info.logo);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(Constant.NEXTSHOPPER, error.toString());
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ImageFragment fragment = ImageFragment.newInstance(details.product.imgs.get(position));
            return fragment;

        }

        @Override
        public int getCount() {
            return details.product.imgs.size();
        }

        void addAll(ProductDetails productDetail) {
            details = productDetail;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.details_like) {
            if (!like) {
                likesView.setText(Integer.toString(details.product.likes + 1));
                likesView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_color_full, 0, 0, 0);
                like = true;
            } else {
                likesView.setText(Integer.toString(details.product.likes));
                likesView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_color, 0, 0, 0);
                like = false;
            }
        } else if (v.getId() == R.id.details_buy_now) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.buy_now, null)).setPositiveButton(R.string.add_to_cart, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                      SharedPreferences sp = getSharedPreferences(Constant.CART, MODE_PRIVATE);
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create();
            builder.show();
        } else if(v.getId()==R.id.details_support){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.support, null));
            Dialog dialog = builder.create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);
        }
        else if(v.getId()==R.id.details_spec){
           Intent intent = new Intent(this, SpecActivity.class);
            intent.putExtra("spec",details.product.description);
            intent.putExtra("imgUrl", details.product.imgs.get(0));
            startActivity(intent);
        }
    }
}
