package com.nextshopper.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.common.Constant;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.GenericResponse;
import com.nextshopper.rest.beans.Product;
import com.nextshopper.rest.beans.ProductDetails;
import com.nextshopper.view.ImageFragment;
import com.nextshopper.view.TitleView;
import com.nextshopper.view.TrendingFragment;

import java.util.Map;

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
    private TextView reviewView;
    private TextView followerView;
    private TextView storeNameView;
    private ImageView storeLogoView;
    private View supportView;
    private View detailsSpecView;
    private View reviewViewAll;
    private View storeView;
    private View detailsShareView;
    private View detailsQuestionView;
    private ProductDetails details = new ProductDetails();
    private ScreenSlidePagerAdapter adapter;
    private boolean like;
    private int storeHeight;
    private int requestCode =0;
    private TitleView titleView;
    private ImageFragment fragment;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        titleView =(TitleView) findViewById(R.id.details_title);
        likesView = (TextView) findViewById(R.id.details_like);
        nameView = (TextView) findViewById(R.id.details_name);
        priceView = (TextView) findViewById(R.id.details_price);
        oriPriceView = (TextView) findViewById(R.id.details_org_price);
        buyNowButton = (Button) findViewById(R.id.details_buy_now);
        buyNowButton.setOnClickListener(this);
        detailsQuestionView = findViewById(R.id.details_question);
        detailsQuestionView.setOnClickListener(this);
        specView = (TextView) findViewById(R.id.spec_details);
        reviewView = (TextView) findViewById(R.id.details_review);
        followerView = (TextView) findViewById(R.id.details_follower);
        storeNameView = (TextView) findViewById(R.id.details_store_name);
        storeLogoView = (ImageView) findViewById(R.id.details_store_logo);
        detailsShareView = findViewById(R.id.details_share);
        detailsShareView.setOnClickListener(this);
        storeView = findViewById(R.id.details_social);
        storeView.setOnClickListener(this);
        supportView = findViewById(R.id.details_support);
        supportView.setOnClickListener(this);
        detailsSpecView = findViewById(R.id.details_spec);
        detailsSpecView.setOnClickListener(this);
        reviewViewAll = findViewById(R.id.details_review_all);
        reviewViewAll.setOnClickListener(this);
        storeHeight = (int)(60 * getResources().getDisplayMetrics().density);
        View likeView = findViewById(R.id.details_like);
        likeView.setOnClickListener(this);
        refresh();
    }

    protected void refresh() {
        productId = getIntent().getStringExtra("productId");
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
                reviewView.setText(String.format(getResources().getString(R.string.review), productDetails.product.reviews));
                followerView.setText(String.format(getResources().getString(R.string.product_followers), productDetails.storeDetails.summary.products, productDetails.storeDetails.summary.followers));
                getSupportFragmentManager().beginTransaction().add(R.id.details_fragment, TrendingFragment.newInstance("Product", null, null, productDetails.product.id)).commit();
                storeNameView.setText(productDetails.storeDetails.store.info.name);
                storeLogoView.setTag(productDetails.storeDetails.store.info.logo);
                like = productDetails.liked;
                if (like)
                    likesView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_color_full, 0, 0, 0);

                ((NextShopperApplication)getApplicationContext()).loadBitmaps(productDetails.storeDetails.store.info.logo,storeLogoView, false, storeHeight);
                //BitmapWorkerTask task = new BitmapWorkerTask(storeLogoView, false, storeHeight);
                //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, productDetails.storeDetails.store.info.logo);
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
            fragment = ImageFragment.newInstance(details.product.imgs.get(position));
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
                ApiService.getService().SocialAPI_LikeProduct(productId, new Callback<GenericResponse>() {
                    @Override
                    public void success(GenericResponse genericResponse, Response response) {
                        Log.d(Constant.NEXTSHOPPER, genericResponse.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Util.alertBox(ProductDetailsActivity.this, error);
                    }
                });
            } else {
                likesView.setText(Integer.toString(details.product.likes));
                likesView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_color, 0, 0, 0);
                like = false;
                ApiService.getService().FavoriteAPI_RemoveFavorite(productId, new Callback<GenericResponse>() {
                    @Override
                    public void success(GenericResponse genericResponse, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Util.alertBox(ProductDetailsActivity.this, error);
                    }
                });
            }
        } else if (v.getId() == R.id.details_buy_now) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View view = inflater.inflate(R.layout.buy_now, null);
            builder.setView(view).setPositiveButton(R.string.add_to_cart, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    details.product.quantity = Integer.parseInt(((EditText) view.findViewById(R.id.product_quantity)).getText().toString());
                    details.product.storeName = details.storeDetails.store.info.name;
                    Map<String, Product> map = ((NextShopperApplication) getApplicationContext()).getProductMap();
                    if (map.get(details.product.id) == null) {
                        map.put(details.product.id, details.product);
                    } else {
                        map.get(details.product.id).quantity +=details.product.quantity;
                    }

                    titleView.setImageRight(R.drawable.shopping_cart_full);

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
        } else if(v.getId()==R.id.details_review_all){
            ReviewActivity.startReviewActivity(details.reviews, this, requestCode);
        } else if(v.getId() == R.id.details_social){
            StoreDetailsActivity.startActivity(this, details.storeDetails.id);
        } else if(v.getId() == R.id.details_share){
            Util.share(this, details.product.name, fragment.getBitMap());
        } else if(v.getId() == R.id.details_question){
            Intent intent = new Intent(this, ContactSellerActivity.class);
            startActivity(intent);
        }
    }

    public void onActivityResult(int code, int resultcode, Intent data){
        if(code==requestCode && resultcode ==RESULT_OK){
            reviewView.setText(String.format(getResources().getString(R.string.review), details.product.reviews+data.getIntExtra("num_of_review", 0)));
        }
    }

    public void rightOnClick(View view){
        Intent intent  = new Intent(this, OrderActivity.class);
        startActivity(intent);

    }

}
