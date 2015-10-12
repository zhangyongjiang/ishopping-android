package com.nextshopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.CartItemDetailsList;
import com.nextshopper.view.CartListFragment;
import com.nextshopper.view.EmptyCartFragment;
import com.nextshopper.view.TitleView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class OrderActivity extends BaseActivity {

    private TitleView titleView;

    //@Override
    protected void refresh() {
        ApiService.getService().ShoppingAPI_List(null, new Callback<CartItemDetailsList>() {
            @Override
            public void success(CartItemDetailsList cartItemDetailsList, Response response) {
                if (cartItemDetailsList.items.size() > 0) {
                    getSupportFragmentManager().beginTransaction().add(R.id.order_container, CartListFragment.newInstance(true)).commit();
                } else{
                    getSupportFragmentManager().beginTransaction().add(R.id.order_container, new EmptyCartFragment()).commit();
                    titleView.getTextRight().setText("");
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

  // @Override
   // protected int getLayoutId() {
    //    return R.layout.activity_order;
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        titleView = (TitleView) findViewById(R.id.cart_title);
        refresh();
    }

    public void rightOnClick(View view){
       Intent intent = new Intent(this, ShippingActivity.class);
        intent.putExtra("source", "checkout");
        startActivity(intent);
    }
}
