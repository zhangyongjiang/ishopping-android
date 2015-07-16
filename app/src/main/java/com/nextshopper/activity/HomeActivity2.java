package com.nextshopper.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.TrendProductList;
import com.nextshopper.view.ProductGridAdapter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Zhang_Kevin on 7/4/15.
 */
public class HomeActivity2 extends BaseActivity {

    private ProductGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_grid);

        GridView gridView = (GridView)findViewById(R.id.product_grid_view);
        adapter = new ProductGridAdapter(this, gridView);
        gridView.setAdapter(adapter);

        NextShopperService service = ApiService.getService();
        service.ProductAPI_PopularProducts(null, 0, 20, new Callback<TrendProductList>() {
            @Override
            public void success(TrendProductList trendProductList, Response response) {
                adapter.setSearchableProductList(trendProductList);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("HomeActivity", error.getMessage());
            }
        });
    }
}
