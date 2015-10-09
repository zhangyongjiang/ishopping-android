package com.nextshopper.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nextshopper.common.Constant;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.CartItemDetailsList;
import com.nextshopper.rest.beans.CartItemRequest;
import com.nextshopper.rest.beans.GenericResponse;
import com.nextshopper.rest.beans.Option;
import com.nextshopper.rest.beans.OptionValue;
import com.nextshopper.rest.beans.ProductDetails;
import com.nextshopper.rest.beans.ProductSku;
import com.nextshopper.rest.beans.SearchableProductList;
import com.nextshopper.view.GridViewAdapter;
import com.nextshopper.view.ImageFragment;
import com.nextshopper.view.TitleView;
import com.nextshopper.view.ToggleButtonGroupTableLayout;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    private int requestCode = 0;
    private TitleView titleView;
    private ImageFragment fragment;
    private String productId;
    private ListView listView;
    private View header;
    private String cookie;
    private RadioGroup radioGroupColor;
    private RadioGroup radioGroupSize;
    private ImageView reviewStartView;
    private LinearLayout linearLayoutSku;
    private static String SUBJECT="subject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.suggestion_listview);
        header = getLayoutInflater().inflate(R.layout.product_details_heaser, listView, false);
        listView.addHeaderView(header, null, false);
        reviewStartView = (ImageView) findViewById(R.id.details_review_star);
        viewPager = (ViewPager) header.findViewById(R.id.viewpager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        titleView = (TitleView) header.findViewById(R.id.details_title);
        likesView = (TextView) header.findViewById(R.id.details_like);
        nameView = (TextView) header.findViewById(R.id.details_name);
        priceView = (TextView) header.findViewById(R.id.details_price);
        oriPriceView = (TextView) header.findViewById(R.id.details_org_price);
        buyNowButton = (Button) header.findViewById(R.id.details_buy_now);
        buyNowButton.setOnClickListener(this);
        detailsQuestionView = header.findViewById(R.id.details_question);
        detailsQuestionView.setOnClickListener(this);
        specView = (TextView) header.findViewById(R.id.spec_details);
        reviewView = (TextView) header.findViewById(R.id.details_review);
        followerView = (TextView) header.findViewById(R.id.details_follower);
        storeNameView = (TextView) header.findViewById(R.id.details_store_name);
        storeLogoView = (ImageView) header.findViewById(R.id.details_store_logo);
        detailsShareView = header.findViewById(R.id.details_share);
        detailsShareView.setOnClickListener(this);
        storeView = header.findViewById(R.id.details_social);
        storeView.setOnClickListener(this);
        supportView = header.findViewById(R.id.details_support);
        supportView.setOnClickListener(this);
        detailsSpecView = header.findViewById(R.id.details_spec);
        detailsSpecView.setOnClickListener(this);
        reviewViewAll = header.findViewById(R.id.details_review_all);
        reviewViewAll.setOnClickListener(this);
        storeHeight = (int) (60 * getResources().getDisplayMetrics().density);
        View likeView = header.findViewById(R.id.details_like);
        likeView.setOnClickListener(this);
        SharedPreferences pref = getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
        cookie = pref.getString(Constant.COOKIE, "");
       // ApiService.buildService(cookie);
        refresh();
    }

    protected int getLayoutId(){
        return R.layout.activity_product_details;
    }

    protected void refresh() {
        productId = getIntent().getStringExtra("productId");
        NextShopperService service = ApiService.getService();
        final ProgressDialog progressDialog = Util.getProgressDialog(this);
        final GridViewAdapter gridViewAdapter = new GridViewAdapter(this, null);
        listView.setAdapter(gridViewAdapter);
        if (cookie.length() > 0) {
            service.ShoppingAPI_List(null, new Callback<CartItemDetailsList>() {
                @Override
                public void success(CartItemDetailsList cartItemDetailsList, Response response) {
                    if (cartItemDetailsList.items.size() > 0) {
                        titleView.setImageRight(R.drawable.shopping_cart_full);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Util.alertBox(ProductDetailsActivity.this, error);
                }
            });
        }
        service.ProductAPI_RecommendationsForProduct(0, 6, productId, new Callback<SearchableProductList>() {
            @Override
            public void success(SearchableProductList searchableProductList, Response response) {
                gridViewAdapter.updateList(searchableProductList);
            }

            @Override
            public void failure(RetrofitError error) {
                Util.alertBox(ProductDetailsActivity.this, error);
            }
        });
        service.ProductAPI_Get(productId, new Callback<ProductDetails>() {
            @Override
            public void success(ProductDetails productDetails, Response response) {
                progressDialog.dismiss();
                adapter.addAll(productDetails);
                likesView.setText(Integer.toString(productDetails.product.likes));
                nameView.setText(productDetails.product.name);
                priceView.setText(String.format(getResources().getString(R.string.salesprice), productDetails.product.salePrice));
                oriPriceView.setText(String.format(getResources().getString(R.string.listprice), productDetails.product.listPrice));
                specView.setText(productDetails.product.description);
                if (productDetails.reviewed) {
                    reviewView.setText(String.format(getResources().getString(R.string.review), productDetails.product.reviews));
                    setReviewStar();
                }
                else
                    reviewView.setText(R.string.review_product);
                followerView.setText(String.format(getResources().getString(R.string.product_followers), productDetails.storeDetails.summary.products, productDetails.storeDetails.summary.followers));
                storeNameView.setText(productDetails.storeDetails.store.info.name);
                storeLogoView.setTag(productDetails.storeDetails.store.info.logo);
                like = productDetails.liked;
                if (like)
                    likesView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_color_full, 0, 0, 0);

                ((NextShopperApplication) getApplicationContext()).loadBitmaps(productDetails.storeDetails.store.info.logo, storeLogoView, false, storeHeight);
                //BitmapWorkerTask task = new BitmapWorkerTask(storeLogoView, false, storeHeight);
                //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, productDetails.storeDetails.store.info.logo);
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Log.e(Constant.NEXTSHOPPER, error.getMessage());
                Util.alertBox(ProductDetailsActivity.this, error);
            }
        });
    }

    public void setReviewStar(){
        int star = details.product.totalRating/details.product.reviews;
        switch(star){
            case 1: reviewStartView.setImageResource(R.drawable.stars_1);break;
            case 2: reviewStartView.setImageResource(R.drawable.stars_2); break;
            case 3: reviewStartView.setImageResource(R.drawable.stars_3);break;
            case 4: reviewStartView.setImageResource(R.drawable.stars_4);break;
            case 5: reviewStartView.setImageResource(R.drawable.stars_5);break;
        }
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
            if (cookie.length() == 0) {
                Util.loginBox(this);
                return;
            }
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
            if (cookie.length() == 0) {
                Util.loginBox(this);
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View view = inflater.inflate(R.layout.buy_now, null);
            if (details.product.prodOptions.size() > 0) {
                linearLayoutSku = (LinearLayout) view.findViewById(R.id.buy_sku);
                /*
                TextView colorView = (TextView) view.findViewById(R.id.buy_color);
                radioGroupColor = (RadioGroup) view.findViewById(R.id.buy_color_radio);
                TextView sizeView = (TextView) view.findViewById(R.id.buy_size);
                radioGroupSize = (RadioGroup) view.findViewById(R.id.buy_size_radio);
                colorView.setVisibility(View.VISIBLE);
                radioGroupColor.setVisibility(View.VISIBLE);
                sizeView.setVisibility(View.VISIBLE);
                radioGroupSize.setVisibility(View.VISIBLE);
                */
                Collection<Option> options = details.product.prodOptions.values();
                for (Option option : options) {
                    TextView textView = new TextView(this);
                    textView.setText(option.title);
                    linearLayoutSku.addView(textView);
                    //RadioGroup radioGroup = new RadioGroup(this);
                    //radioGroup.setOrientation(LinearLayout.HORIZONTAL);
                    ToggleButtonGroupTableLayout buttonGroup = new ToggleButtonGroupTableLayout(this);
                    int i =0;
                    TableRow tr=null;
                    for (OptionValue ov : option.allowedValues.values()) {
                        if(i%3==0){
                            if(tr!=null)
                                buttonGroup.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            tr = new TableRow(this);
                            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                        }

                        RadioButton radioButton = new RadioButton(this);
                        radioButton.setId(Integer.valueOf(ov.id));
                        radioButton.setTag(option.id);
                        radioButton.setText(ov.title);
                        radioButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                        tr.addView(radioButton);
                        i++;
                       // radioGroup.addView(radioButton);
                    }
                    buttonGroup.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    //linearLayoutSku.addView(radioGroup);
                    linearLayoutSku.addView(buttonGroup);
                }

            }
            builder.setView(view).setPositiveButton(R.string.add_to_cart, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int quanity = Integer.parseInt(((EditText) view.findViewById(R.id.product_quantity)).getText().toString());
                    if (quanity <= 0) {
                        Util.alertBox(ProductDetailsActivity.this, getString(R.string.quanity_positve));
                        return;
                    }
                    String skuId = null;

                    if (linearLayoutSku != null && linearLayoutSku.getChildCount() > 0) {
                        Map<String, String> checkedMap = new HashMap<>();
                        for (int i = 0; i < linearLayoutSku.getChildCount(); i++) {
                            View view = linearLayoutSku.getChildAt(i);
                            if (linearLayoutSku.getChildAt(i) instanceof ToggleButtonGroupTableLayout) {
                               // RadioGroup radioGroup = (RadioGroup) linearLayoutSku.getChildAt(i);
                                ToggleButtonGroupTableLayout radioGroup = (ToggleButtonGroupTableLayout) linearLayoutSku.getChildAt(i);
                                int checkId = radioGroup.getCheckedRadioButtonId();
                                if (checkId == -1) {
                                    Util.alertBox(ProductDetailsActivity.this, "Please check required field");
                                    return;
                                }
                                RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkId);
                                String itemId = radioButton.getTag().toString();
                                checkedMap.put(itemId, String.valueOf(checkId));
                            }
                        }

                        List<ProductSku> skuList = details.product.skuPriceAndQuantity;
                        for (ProductSku sku : skuList) {
                            Map<String, String> map = sku.options;
                            int position = 0;
                            for (String key : checkedMap.keySet()) {
                                if (map.get(key) != null && !map.get(key).equals(checkedMap.get(key)))
                                    break;
                                else
                                    position++;
                            }
                            if (position == checkedMap.size()) {
                                skuId = sku.sku;
                                break;
                            }
                        }
                    }
                    CartItemRequest request = new CartItemRequest();
                    request.productId = productId;
                    request.quantity = quanity;
                    request.skuId = skuId;
                    ApiService.getService().ShoppingAPI_AddCartItem(request, new Callback<CartItemDetailsList>() {
                        @Override
                        public void success(CartItemDetailsList cartItemDetailsList, Response response) {

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Util.alertBox(ProductDetailsActivity.this, error);
                        }
                    });

                    // save cart to local
                    // details.product.quantity = Integer.parseInt(((EditText) view.findViewById(R.id.product_quantity)).getText().toString());
                    //details.product.storeName = details.storeDetails.store.info.name;
                    //Map<String, Product> map = ((NextShopperApplication) getApplicationContext()).getProductMap();
                    //if (map.get(details.product.id) == null) {
                    //   map.put(details.product.id, details.product);
                    //} else {
                    //    map.get(details.product.id).quantity +=details.product.quantity;
                    // }

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
        } else if (v.getId() == R.id.details_support) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.support, null));
            Dialog dialog = builder.create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);
        } else if (v.getId() == R.id.details_spec) {
            Intent intent = new Intent(this, SpecActivity.class);
            intent.putExtra("spec", details.product.description);
            intent.putExtra("imgUrl", details.product.imgs.get(0));
            startActivity(intent);
        } else if (v.getId() == R.id.details_review_all) {
            if (cookie.length() == 0) {
                Util.loginBox(this);
                return;
            }
            if(details.reviewed)
                ReviewActivity.startReviewActivity(productId, this, requestCode);
            else{
               CreateReviewActivity.startActivityForResult(this, productId, requestCode);
            }

        } else if (v.getId() == R.id.details_social) {
            StoreDetailsActivity.startActivity(this, details.storeDetails.id);
        } else if (v.getId() == R.id.details_share) {
            Util.share(this, details.product.name, fragment.getBitMap());
        } else if (v.getId() == R.id.details_question) {
            if (cookie.length() == 0) {
                Util.loginBox(this);
                return;
            }
           ContactSellerActivity.startActivity(this,details.product.name, details.storeDetails.id);
        }
    }

    public void onActivityResult(int code, int resultcode, Intent data) {
        if (code == requestCode && resultcode == RESULT_OK) {
            int newreview = data.getIntExtra("num_of_review", 0);
            details.product.reviews = details.product.reviews+ newreview;
            int rating = data.getIntExtra("rating",0);
            reviewView.setText(String.format(getResources().getString(R.string.review), details.product.reviews));
            if(data.getIntExtra("num_of_review",0)>0)
                details.reviewed=true;
            if(newreview>0) {
                details.product.totalRating = details.product.totalRating + rating;
                setReviewStar();
            }

        }
    }

    public void rightOnClick(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);

    }

}
