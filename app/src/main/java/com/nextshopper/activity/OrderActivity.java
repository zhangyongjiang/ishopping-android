package com.nextshopper.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.beans.Product;
import com.nextshopper.view.Item;
import com.nextshopper.view.OrderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrderActivity extends BaseActivity {
    private ListView listView;
    private Item subtotalView;
    private Item shippingView;
    private Item totalView;
    private Item creditView;
    private Item couponView;
    private Item netPayView;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        listView = (ListView)findViewById(R.id.list_of_order);
        Map<String, Product> productMap= ((NextShopperApplication)getApplicationContext()).getProductMap();
        productList = new ArrayList<Product>(productMap.values());
        OrderAdapter orderAdapter= new OrderAdapter(this, productList);
        listView.setAdapter(orderAdapter);;
        subtotalView = (Item) findViewById(R.id.order_subtotal);
        shippingView =(Item) findViewById(R.id.order_shipping);
        totalView =(Item) findViewById(R.id.order_total);
        creditView =(Item) findViewById(R.id.order_credit);
        couponView =(Item) findViewById(R.id.order_coupon);
        netPayView =(Item) findViewById(R.id.order_netpay);
        float subtotal=0;
        for(Product p: productList){
            subtotal+=p.quantity*p.salePrice;
        }
        float shipping=0;
        float credit=0;
        float total = subtotal+ shipping;
        subtotalView.setRight(String.format(getResources().getString(R.string.price),subtotal));
        shippingView.setRight(String.format(getResources().getString(R.string.price),shipping));
        totalView.setRight(String.format(getResources().getString(R.string.price),total));
        creditView.setRight(String.format(getResources().getString(R.string.price),credit));
        netPayView.setRight(String.format(getResources().getString(R.string.price),total));
    }
}
