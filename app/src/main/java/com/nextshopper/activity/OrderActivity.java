package com.nextshopper.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.beans.Product;
import com.nextshopper.view.OrderAdapter;

import java.util.List;


public class OrderActivity extends BaseActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        listView = (ListView)findViewById(R.id.list_of_order);
        List<Product> productList = ((NextShopperApplication)getApplicationContext()).getProductList();
        OrderAdapter orderAdapter= new OrderAdapter(this, productList);
        listView.setAdapter(orderAdapter);;
    }

}
