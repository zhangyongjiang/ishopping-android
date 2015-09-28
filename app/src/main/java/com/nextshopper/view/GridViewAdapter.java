package com.nextshopper.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 9/25/15.
 */
public class GridViewAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context ctx;
    private int start = 0;
    private int numOfItem = 20;
    private boolean call =true;
    private String storeId;
    private List<ArrayList<SearchableProduct>> searchableProductList = new ArrayList<>();

    public GridViewAdapter(Context ctx, String storeId) {
        this.ctx = ctx;
        this.storeId = storeId;
    }

    @Override
    public int getCount() {
        return searchableProductList.size();
    }

    @Override
    public List<SearchableProduct> getItem(int position) {
        return searchableProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final List<SearchableProduct> spList = searchableProductList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_row, parent, false);
        }
        ItemView left = (ItemView) convertView.findViewById(R.id.item_left_view);
        ItemView right = (ItemView) convertView.findViewById(R.id.item_right_view);
        left.setValue(spList.get(0));
        if(spList.size()==2){
            right.setValue(spList.get(1));
        }
        return convertView;
    }

    void setImageView(ImageView imageView, String url) {
        ((NextShopperApplication) ctx.getApplicationContext()).loadBitmaps(url, imageView, false, 0);
    }

    public void updateList(SearchableProductList list) {
        List<ArrayList<SearchableProduct>> result = new ArrayList<>();
        ArrayList<SearchableProduct> spList = null;
        for(int i=0; i<list.items.size(); i++){
            if(i%2==0){
                spList = new ArrayList<>();
                spList.add(list.items.get(i));
                if(i==list.items.size()-1){
                    result.add(spList);
                }
            }else{
                spList.add(list.items.get(i));
                result.add(spList);
            }
        }
        searchableProductList.addAll(result);
        if(result.size()==numOfItem/2)
            call =true;
        notifyDataSetChanged();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.i(Constant.NEXTSHOPPER, "ONScroll called");
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount() && call) {
            final ProgressDialog progressDialog= Util.getProgressDialog(ctx);
            NextShopperService service = ApiService.getService();
            call=false;
                service.ProductAPI_ListStorePublicProducts(storeId, start, numOfItem, new Callback<ProductList>() {
                    @Override
                    public void success(ProductList productList, Response response) {
                        progressDialog.dismiss();
                        updateList(convert(productList));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                    }
                });
            start = start + numOfItem;
        }
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
