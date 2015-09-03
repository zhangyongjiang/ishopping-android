package com.nextshopper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ShippingInfo;
import com.nextshopper.rest.beans.User;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ShippingActivity extends BaseActivity {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ShippingInfo shippingInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        listView =(ListView) findViewById(R.id.shipping_checkout);
        String[] strs = new String[]{"First Name", "Last Name", "Phone", "Address","Country", "state", "City","Zip Code"};
        arrayAdapter = new ArrayAdapter<>(this, R.layout.shipping_item, R.id.shipping_title, strs);
        listView.setAdapter(arrayAdapter);
        shippingInfo = new ShippingInfo();
        for(int i =0; i<listView.getAdapter().getCount(); i++){
            View view = listView.getChildAt(i);
            EditText editText = (EditText)view.findViewById(R.id.shipping_info);
            if(i==0) shippingInfo.firstName = editText.getText().toString();
            if(i==1) shippingInfo.lastName = editText.getText().toString();
            if(i==2) shippingInfo.phoneNumber = editText.getText().toString();
            if(i==3) shippingInfo.address = editText.getText().toString();
            if(i==4) shippingInfo.country = editText.getText().toString();
            if(i==5) shippingInfo.state = editText.getText().toString();
            if(i==6) shippingInfo.city = editText.getText().toString();
            if(i==7) shippingInfo.zipcode = editText.getText().toString();
        }

    }

    public void rightOnClick(View view){
        ApiService.getService().UserAPI_AddShipping(shippingInfo, new Callback<User>() {
            @Override
            public void success(User user, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        finish();
    }
}
