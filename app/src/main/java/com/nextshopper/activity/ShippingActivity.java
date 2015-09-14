package com.nextshopper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;
import com.nextshopper.common.Constant;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.PaymentToken;
import com.nextshopper.rest.beans.ShippingInfo;
import com.nextshopper.rest.beans.User;
import com.nextshopper.view.InputItem;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ShippingActivity extends BaseActivity {

    private ShippingInfo shippingInfo;
    private InputItem firstNameView;
    private InputItem lastNameView;
    private InputItem phoneView;
    private InputItem addressView;
    private InputItem countryView;
    private InputItem stateView;
    private InputItem cityView;
    private InputItem zipView;
    private static int BT_REQUEST_CODE=100;
    private String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        firstNameView =(InputItem) findViewById(R.id.shipping_firstname);
        lastNameView =(InputItem) findViewById(R.id.shipping_lastname);
        phoneView =(InputItem) findViewById(R.id.shipping_phone);
        addressView =(InputItem) findViewById(R.id.shipping_address);
        countryView =(InputItem) findViewById(R.id.shipping_country);
        stateView =(InputItem) findViewById(R.id.shipping_state);
        cityView =(InputItem) findViewById(R.id.shipping_city);
        zipView =(InputItem) findViewById(R.id.shipping_zipcode);
        SharedPreferences pref = getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
        firstNameView.setEditText(pref.getString(Constant.FIRST_NAME,""));
        lastNameView.setEditText(pref.getString(Constant.LAST_NAME,""));
        phoneView.setEditText(pref.getString(Constant.PHONE,""));
        addressView.setEditText(pref.getString(Constant.ADDRESS,""));
        countryView.setEditText(pref.getString(Constant.COUNTRY,""));
        stateView.setEditText(pref.getString(Constant.STATE,""));
        cityView.setEditText(pref.getString(Constant.CITY,""));
        zipView.setEditText(pref.getString(Constant.ZIP,""));
        source = getIntent().getStringExtra("source");
    }

    public void rightOnClick(View view){
        shippingInfo = new ShippingInfo();
        shippingInfo.firstName = firstNameView.getEditText().getText().toString();
        shippingInfo.lastName = lastNameView.getEditText().getText().toString();
        shippingInfo.phoneNumber = phoneView.getEditText().getText().toString();
        shippingInfo.address = addressView.getEditText().getText().toString();
        shippingInfo.country = countryView.getEditText().getText().toString();
        shippingInfo.state = stateView.getEditText().getText().toString();
        shippingInfo.city = cityView.getEditText().getText().toString();
        shippingInfo.zipcode = zipView.getEditText().getText().toString();
        if(source.equals("setting")){
            ApiService.getService().UserAPI_AddShipping(shippingInfo, new Callback<User>() {
                @Override
                public void success(User user, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
            finish();
        }else if(source.equals("checkout")){
            ApiService.getService().ShoppingAPI_GetPaymentToken(null, new Callback<PaymentToken>() {
                @Override
                public void success(PaymentToken paymentToken, Response response) {
                    Intent intent = new Intent(ShippingActivity.this, BraintreePaymentActivity.class);
                    intent.putExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN, paymentToken.token);
                    startActivityForResult(intent, BT_REQUEST_CODE);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BT_REQUEST_CODE) {
            if (resultCode == BraintreePaymentActivity.RESULT_OK) {
                String paymentMethodNonce = data.getStringExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);
                postNonceToServer(paymentMethodNonce);
            }
        }
    }
    void postNonceToServer(String nonce) {
          OrderPreviewActivity.startActivity(this, shippingInfo, nonce);
    }

}
