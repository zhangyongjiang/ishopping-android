package com.nextshopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nextshopper.view.CartListFragment;


public class OrderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportFragmentManager().beginTransaction().add(R.id.order_container,CartListFragment.newInstance(true)).commit();

    }

    public void rightOnClick(View view){
       Intent intent = new Intent(this, ShippingActivity.class);
        startActivity(intent);
    }
}
