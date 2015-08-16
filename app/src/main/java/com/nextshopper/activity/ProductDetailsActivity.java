package com.nextshopper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.ProductDetails;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProductDetailsActivity extends SwipeRefreshActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        refresh();
    }

    protected void refresh() {
        String productId = getIntent().getStringExtra("productId");
        NextShopperService service = ApiService.getService();
        service.ProductAPI_Get(productId, new Callback<ProductDetails>() {
            @Override
            public void success(ProductDetails productDetails, Response response) {
                Log.i("TEXT", productDetails.product.name);
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailsActivity.this);
                builder.setMessage("Product name: " + productDetails.product.name)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("TEXT", error.toString());
            }
        });
    }
}
