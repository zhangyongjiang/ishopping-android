package com.nextshopper.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.ProductDetailsActivity;
import com.nextshopper.activity.R;
import com.nextshopper.common.Constant;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.Product;
import com.nextshopper.rest.beans.ProductList;
import com.nextshopper.rest.beans.SearchableProduct;
import com.nextshopper.rest.beans.SearchableProductList;
import com.nextshopper.rest.beans.TrendProductList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Zhang_Kevin on 7/4/15.
 */
public class ProductGridAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private Context ctx;
    private GridView gridView;
    private SearchableProductList searchableProductList = new SearchableProductList();
    private int start = 0;
    private int numOfItem = 20;
    private TrendingFragment trendingFragment;
    private boolean call =true;

    public ProductGridAdapter(Context ctx, GridView gridView, TrendingFragment trendingFragment) {
        this.ctx = ctx;
        this.gridView = gridView;
        this.trendingFragment = trendingFragment;
    }

    public void refresh(){
        start =0;
        searchableProductList.items.clear();
    }
    @Override
    public int getCount() {
        if (searchableProductList == null)
            return 0;
        return searchableProductList.items.size();
    }

    @Override
    public  SearchableProduct getItem(int position) {
        return searchableProductList.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SearchableProduct sp = searchableProductList.items.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false);
        }

        TextView textViewDisPrice = (TextView) convertView.findViewById(R.id.item_disount_price);
        textViewDisPrice.setText("$" + Math.round(sp.price * 100) / 100.0);
        TextView textViewPrice = (TextView) convertView.findViewById(R.id.item_ori_price);
        textViewPrice.setText("$" + Math.round(sp.listPrice * 100) / 100.0);
        TextView textViewName = (TextView) convertView.findViewById(R.id.item_name);
        textViewName.setText(sp.name);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ProductDetailsActivity.class);
                intent.putExtra("productId", sp.id);
                Log.d("NextShopper", sp.imgUrl.get(0));
                ctx.startActivity(intent);
            }
        };
        //imageView.setTag(sp.imgUrl.get(0));
        imageView.setImageBitmap(null);
        setImageView(imageView, sp.imgUrl.get(0));
        convertView.setOnClickListener(listener);
        //new DownloadImageTask(imageView).execute(sp.imgUrl.get(0));
        return convertView;
    }

    void setImageView(ImageView imageView, String url) {
        ((NextShopperApplication)ctx.getApplicationContext()).loadBitmaps(url, imageView, false, 0);
    }

    public void setSearchableProductList(SearchableProductList searchableProductList) {
        if (searchableProductList.items == null) return;
        if(searchableProductList.items.size()==0) return;
        call =true;
        this.searchableProductList.items.addAll(searchableProductList.items);
        notifyDataSetChanged();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.i(Constant.NEXTSHOPPER, "ONScroll called");
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount() && call) {
            final ProgressDialog progressDialog= Util.getProgressDialog(ctx);
            NextShopperService service = ApiService.getService();
            call=false;
            if (trendingFragment.getArguments().getString("Tab").equals("Trending")) {
                Log.i(Constant.NEXTSHOPPER, "Trending, " + start + ", " + numOfItem + ", " +lastVisibleItem+", "+ ProductGridAdapter.this.getCount() + ", ");
                service.ProductAPI_PopularProducts(null, start, numOfItem, new Callback<TrendProductList>() {
                    @Override
                    public void success(TrendProductList trendProductList, Response response) {
                        progressDialog.dismiss();
                        setSearchableProductList(trendProductList);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                        Log.e("NextShopper", error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);

                    }
                });
            } else if (trendingFragment.getArguments().getString("Tab").equals("Newest")) {
                Log.i(Constant.NEXTSHOPPER, "Newest, " + start + ", " + numOfItem + ", " +lastVisibleItem+", "+ ProductGridAdapter.this.getCount() + ", ");
                service.ProductAPI_NewProducts(start, numOfItem, new Callback<SearchableProductList>() {
                    @Override
                    public void success(SearchableProductList searchableProductList, Response response) {
                        progressDialog.dismiss();
                        setSearchableProductList(searchableProductList);
                       }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                        Log.i(Constant.NEXTSHOPPER, error.getMessage());
                    }
                });
            } else if (trendingFragment.getArguments().getString("Tab").equals("Just For You")) {
                Log.i(Constant.NEXTSHOPPER, "Just For You, " + start + ", " + numOfItem + ", " +lastVisibleItem+", "+ ProductGridAdapter.this.getCount() + ", ");
                service.ProductAPI_RecommendationsForUser(start, numOfItem, null, null, new Callback<SearchableProductList>() {
                    @Override
                    public void success(SearchableProductList searchableProductList, Response response) {
                        progressDialog.dismiss();
                        setSearchableProductList(searchableProductList);
                     }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                        Log.e("HomeActivity", error.getMessage());
                    }
                });

            } else if (trendingFragment.getArguments().get("Tab").equals("")) {
                service.ProductAPI_Search(trendingFragment.getArguments().getString("Keywords"), trendingFragment.getArguments().getString("Cat"), null, null, null, start, numOfItem, null, new Callback<ProductList>() {
                    @Override
                    public void success(ProductList productList, Response response) {
                        progressDialog.dismiss();
                        setSearchableProductList(convert(productList));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                        Log.e(Constant.NEXTSHOPPER, error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);

                    }
                });
            } else if(trendingFragment.getArguments().get("Tab").equals("Store")){
                service.ProductAPI_ListStorePublicProducts(((String) trendingFragment.getArguments().get("ProductId")), start, numOfItem, new Callback<ProductList>() {
                    @Override
                    public void success(ProductList productList, Response response) {
                        progressDialog.dismiss();
                        setSearchableProductList(convert(productList));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                       progressDialog.dismiss();
                    }
                });
            } else {
                Log.i(Constant.NEXTSHOPPER, "Recommendation, " + start + ", " + numOfItem + ", " +lastVisibleItem+", "+ ProductGridAdapter.this.getCount() + ", ");
                service.ProductAPI_RecommendationsForProduct(start, numOfItem, trendingFragment.getArguments().getString("ProductId"), new Callback<SearchableProductList>() {
                    @Override
                    public void success(SearchableProductList searchableProductList, Response response) {
                        progressDialog.dismiss();
                        setSearchableProductList(searchableProductList);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                        Log.e(Constant.NEXTSHOPPER, error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);
                    }
                });
            }

            start = start + numOfItem;
        }
    }


    void fetchMore(){
        final ProgressDialog progressDialog= Util.getProgressDialog(ctx);
        NextShopperService service = ApiService.getService();
        if (trendingFragment.getArguments().getString("Tab").equals("Trending")) {
            service.ProductAPI_PopularProducts(null, start, numOfItem, new Callback<TrendProductList>() {
                @Override
                public void success(TrendProductList trendProductList, Response response) {
                    progressDialog.dismiss();
                    setSearchableProductList(trendProductList);
                    Log.i(Constant.NEXTSHOPPER, "Trending");
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e("NextShopper", error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);

                }
            });
        } else if (trendingFragment.getArguments().getString("Tab").equals("Newest")) {
            service.ProductAPI_NewProducts(start, numOfItem, new Callback<SearchableProductList>() {
                @Override
                public void success(SearchableProductList searchableProductList, Response response) {
                    progressDialog.dismiss();
                    setSearchableProductList(searchableProductList);
                    Log.i(Constant.NEXTSHOPPER, "Newest");
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.i(Constant.NEXTSHOPPER, error.getMessage());
                }
            });
        } else if (trendingFragment.getArguments().getString("Tab").equals("Just For You")) {
            service.ProductAPI_RecommendationsForUser(start, numOfItem, null, null, new Callback<SearchableProductList>() {
                @Override
                public void success(SearchableProductList searchableProductList, Response response) {
                    progressDialog.dismiss();
                    setSearchableProductList(searchableProductList);
                    Log.i(Constant.NEXTSHOPPER, "Just For You, " + start + ", " + numOfItem + ", " + ProductGridAdapter.this.getCount() + ", ");
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e("HomeActivity", error.getMessage());
                }
            });

        } else if (trendingFragment.getArguments().get("Tab").equals("")) {
            service.ProductAPI_Search(trendingFragment.getArguments().getString("Keywords"), trendingFragment.getArguments().getString("Cat"), null, null, null, start, numOfItem, null, new Callback<ProductList>() {
                @Override
                public void success(ProductList productList, Response response) {
                    progressDialog.dismiss();
                    setSearchableProductList(convert(productList));
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e(Constant.NEXTSHOPPER, error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);

                }
            });
        } else if(trendingFragment.getArguments().get("Tab").equals("Store")){
            service.ProductAPI_ListStorePublicProducts(((String) trendingFragment.getArguments().get("ProductId")), start, numOfItem, new Callback<ProductList>() {
                @Override
                public void success(ProductList productList, Response response) {
                    progressDialog.dismiss();
                    setSearchableProductList(convert(productList));
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                }
            });
        } else {
            service.ProductAPI_RecommendationsForProduct(start, numOfItem, trendingFragment.getArguments().getString("ProductId"), new Callback<SearchableProductList>() {
                @Override
                public void success(SearchableProductList searchableProductList, Response response) {
                    progressDialog.dismiss();
                    setSearchableProductList(searchableProductList);
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e(Constant.NEXTSHOPPER, error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);
                }
            });
        }

        start = start + numOfItem;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    SearchableProductList convert(ProductList productList) {
        SearchableProductList spList = new SearchableProductList();
        for (Product p : productList.items) {
            SearchableProduct sp = new SearchableProduct();
            sp.imgUrl = p.imgs;
            sp.name = p.name;
            sp.price = p.salePrice;
            sp.listPrice = p.salePrice;
            spList.items.add(sp);
        }
        return spList;
    }
}
