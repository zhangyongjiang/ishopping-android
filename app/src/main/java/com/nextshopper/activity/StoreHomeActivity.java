package com.nextshopper.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.StoreDetails;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Zhang_Kevin on 7/5/15.
 */
public class StoreHomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refresh();
    }

    public void refresh() {
        String storeId = getIntent().getStringExtra("storeId");
        NextShopperService service = ApiService.getService();
        final ProgressDialog progressDialog= Util.getProgressDialog(this);
        service.StoreAPI_GetStoreDetails(storeId, new Callback<StoreDetails>() {
            @Override
            public void success(StoreDetails storeDetails, Response response) {
              progressDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
             progressDialog.dismiss();
                Util.alertBox(StoreHomeActivity.this, error);
            }
        });
    }
}
