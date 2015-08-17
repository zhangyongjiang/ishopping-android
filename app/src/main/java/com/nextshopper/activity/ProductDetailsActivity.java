package com.nextshopper.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.nextshopper.common.Constant;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.ProductDetails;
import com.nextshopper.view.ImageFragment;
import com.nextshopper.view.SettingItem;
import com.nextshopper.view.TrendingFragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProductDetailsActivity extends SwipeRefreshActivity {

    private ViewPager viewPager;
    private TextView likesView;
    private TextView nameView;
    private TextView priceView;
    private TextView oriPriceView;
    private Button buyNowButton;
    private SettingItem specView;
    private SettingItem reviewView;
    private TextView followerView;
    private TextView storeNameView;
    private ProductDetails details= new ProductDetails();
    private ScreenSlidePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        likesView =(TextView) findViewById(R.id.details_like);
        nameView =(TextView) findViewById(R.id.details_name);
        priceView =(TextView) findViewById(R.id.details_price);
        oriPriceView  =(TextView) findViewById(R.id.details_org_price);
        buyNowButton =(Button) findViewById(R.id.details_buy_now);
        specView =(SettingItem) findViewById(R.id.details_spec);
        reviewView=(SettingItem) findViewById(R.id.details_review);
        followerView =(TextView) findViewById(R.id.details_follower);
        storeNameView =(TextView) findViewById(R.id.details_store_name);
        refresh();
    }

    protected void refresh() {
        String productId = getIntent().getStringExtra("productId");
        NextShopperService service = ApiService.getService();
        service.ProductAPI_Get(productId, new Callback<ProductDetails>() {
            @Override
            public void success(ProductDetails productDetails, Response response) {
                adapter.addAll(productDetails.product.imgs);
                likesView.setText(Integer.toString(productDetails.product.likes));
                nameView.setText(productDetails.product.name);
                priceView.setText(String.format(getResources().getString(R.string.salesprice),productDetails.product.salePrice));
                oriPriceView.setText(String.format(getResources().getString(R.string.listprice),productDetails.product.listPrice));
                specView.setItem(String.format(getResources().getString(R.string.spec), productDetails.product.description));
                reviewView.setItem(String.format(getResources().getString(R.string.review), productDetails.product.reviews));
                followerView.setText(String.format(getResources().getString(R.string.product_followers), productDetails.storeDetails.summary.products, productDetails.storeDetails.summary.followers));
                getSupportFragmentManager().beginTransaction().add(R.id.details_fragment, TrendingFragment.newInstance("Product", null, null, productDetails.product.id)).commit();
                storeNameView.setText(productDetails.storeDetails.store.info.name);
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
             ImageFragment fragment= ImageFragment.newInstance(details.product.imgs.get(position));
             return fragment;

        }

        @Override
        public int getCount() {
            return details.product.imgs.size();
        }

        void addAll(List<String> imgUrls){
            details.product.imgs = imgUrls;
            notifyDataSetChanged();
        }
    }
}
