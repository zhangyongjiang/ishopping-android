package com.nextshopper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ShippingInfo;
import com.nextshopper.rest.beans.UserOrderDetails;
import com.nextshopper.view.Item;
import com.nextshopper.view.OneOrderAdapter;

import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class OrderDetailsActivity extends BaseActivity {
    private Item orderNumberView;
    private Item orderDateView;
    private Item orderStatusView;
    private Item orderGrandTotalView;
    private Item storeCreditView;
    private Item couponView;
    private TextView nameView;
    private TextView addressView;
    private TextView cityView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Intent intent = getIntent();
        String storeId = intent.getStringExtra("storeId");
        orderNumberView = (Item) findViewById(R.id.order_number);
        orderDateView = (Item) findViewById(R.id.order_date);
        orderStatusView = (Item) findViewById(R.id.order_status);
        orderGrandTotalView = (Item) findViewById(R.id.order_total);
        storeCreditView =(Item) findViewById(R.id.order_credit);
        couponView =(Item) findViewById(R.id.order_coupon);
        nameView = (TextView) findViewById(R.id.order_details_name);
        addressView =(TextView) findViewById(R.id.order_details_address);
        cityView =(TextView) findViewById(R.id.order_details_city);
        listView = (ListView) findViewById(R.id.order_details_all_list);
        final OneOrderAdapter adapter = new OneOrderAdapter(this);
        listView.setAdapter(adapter);

        ApiService.getService().OrderAPI_GetOrderById(storeId, new Callback<UserOrderDetails>() {
            @Override
            public void success(UserOrderDetails userOrderDetails, Response response) {
                orderNumberView.setRight(userOrderDetails.order.orderNumber);
                orderDateView.setRight(new Date(userOrderDetails.order.created).toString());
                orderStatusView.setRight(userOrderDetails.order.status.toString());
                orderGrandTotalView.setRight(Float.toString(userOrderDetails.order.total));
                storeCreditView.setRight(Float.toString(userOrderDetails.order.creditUsed));
                couponView.setRight(userOrderDetails.order.couponCode);
                ShippingInfo shippingInfo = userOrderDetails.order.shipping;
                nameView.setText(shippingInfo.firstName + " " + shippingInfo.lastName);
                addressView.setText(shippingInfo.address);
                addressView.setText(shippingInfo.city+", "+ shippingInfo.state+" "+ shippingInfo.zipcode+" "+ shippingInfo.country);
                adapter.updateList(userOrderDetails.items);
                Log.d(Constant.NEXTSHOPPER, userOrderDetails.toString());

            }

            @Override
            public void failure(RetrofitError error) {
                Util.alertBox(OrderDetailsActivity.this, new String(((TypedByteArray)error.getBody()).getBytes()));
            }
        });

    }

    public static void startActivity(Context ctx, String orderId){
        Intent intent = new Intent(ctx, OrderDetailsActivity.class);
        intent.putExtra("storeId", orderId);
        ctx.startActivity(intent);
    }
}
