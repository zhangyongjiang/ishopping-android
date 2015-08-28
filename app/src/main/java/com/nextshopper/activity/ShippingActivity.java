package com.nextshopper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ShippingActivity extends BaseActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        listView =(ListView) findViewById(R.id.shipping_checkout);
        String[] strs = new String[]{"First Name", "Last Name", "Phone", "Address","Country", "state", "City","Zip Code"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.shipping_item, R.id.shipping_title, strs);
        listView.setAdapter(adapter);

    }

    public void rightOnClick(View view){

    }


}
