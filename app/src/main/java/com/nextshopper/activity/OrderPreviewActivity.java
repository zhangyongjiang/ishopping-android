package com.nextshopper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.BraintreeBillingInfo;
import com.nextshopper.rest.beans.CheckoutRequest;
import com.nextshopper.rest.beans.ShippingInfo;
import com.nextshopper.rest.beans.UserOrderDetailsList;
import com.nextshopper.view.CartListFragment;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class OrderPreviewActivity extends BaseActivity {
    private TextView nameView;
    private TextView addressView;
    private TextView cityView;
    private static String SHIPPING_INFO="shipping_info";
    private ShippingInfo shippingInfo;
    private static String NONCE = "bt_nonce";
    private String nonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_preview);
        nameView = (TextView) findViewById(R.id.preview_name);
        addressView =(TextView) findViewById(R.id.preview_address);
        cityView =(TextView) findViewById(R.id.preview_city);
        Intent intent = getIntent();
        shippingInfo = intent.getParcelableExtra(SHIPPING_INFO);
        String name = shippingInfo.firstName+" "+ shippingInfo.lastName;
        String address = shippingInfo.address;
        String city = shippingInfo.city+", "+ shippingInfo.state+" "+ shippingInfo.zipcode+" "+shippingInfo.country;
        nonce = intent.getStringExtra(NONCE);
        nameView.setText(name);
        addressView.setText(address);
        cityView.setText(city);
        CartListFragment fragment = CartListFragment.newInstance(false);
        getSupportFragmentManager().beginTransaction().add(R.id.preview_container, fragment).commit();
    }

    public static void startActivity(Context ctx, ShippingInfo shippingInfo, String nonce){
        Intent intent = new Intent(ctx, OrderPreviewActivity.class);
        intent.putExtra(SHIPPING_INFO, shippingInfo);
        intent.putExtra(NONCE, nonce);
        ctx.startActivity(intent);
    }

    public void rightOnClick(View view){

        CheckoutRequest request = new CheckoutRequest();
        request.shippingInfo = shippingInfo;
        request.braintreeInfo = new BraintreeBillingInfo();
        request.braintreeInfo.nonce = nonce;
        ApiService.getService().ShoppingAPI_Checkout(request, new Callback<UserOrderDetailsList>() {
            @Override
            public void success(UserOrderDetailsList userOrderDetailsList, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
                Util.alertBox(OrderPreviewActivity.this, error);

            }
        });
      Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("fragment", "Confirm");
        startActivity(intent);
    }

}
