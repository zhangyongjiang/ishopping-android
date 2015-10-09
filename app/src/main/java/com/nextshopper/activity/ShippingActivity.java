package com.nextshopper.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;
import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.PaymentToken;
import com.nextshopper.rest.beans.ShippingInfo;
import com.nextshopper.rest.beans.User;
import com.nextshopper.view.InputItem;
import com.nextshopper.view.TitleView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ShippingActivity extends BaseActivity implements View.OnClickListener {

    private ShippingInfo shippingInfo;
    private InputItem firstNameView;
    private InputItem lastNameView;
    private InputItem phoneView;
    private InputItem addressView;
    private TextView countryView;
    private TextView stateView;
    private InputItem cityView;
    private InputItem zipView;
    private static int BT_REQUEST_CODE = 100;
    private String source;
    private NumberPicker countryPicker;
    private NumberPicker statePicker;
    private View shippingView;
    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        titleView =(TitleView) findViewById(R.id.shipping_title_view);
        firstNameView = (InputItem) findViewById(R.id.shipping_firstname);
        lastNameView = (InputItem) findViewById(R.id.shipping_lastname);
        phoneView = (InputItem) findViewById(R.id.shipping_phone);
        addressView = (InputItem) findViewById(R.id.shipping_address);
        countryView = (TextView) findViewById(R.id.country_wheel);
        stateView = (TextView) findViewById(R.id.state_wheel);
        cityView = (InputItem) findViewById(R.id.shipping_city);
        zipView = (InputItem) findViewById(R.id.shipping_zipcode);
        countryPicker = (NumberPicker) findViewById(R.id.shipping_country_picker);
        statePicker = (NumberPicker) findViewById(R.id.shipping_state_picker);
        shippingView = findViewById(R.id.shipping_all);
        fill();
    }

    private void fill() {
        SharedPreferences pref = getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
        firstNameView.setEditText(pref.getString(Constant.FIRST_NAME, ""));
        lastNameView.setEditText(pref.getString(Constant.LAST_NAME, ""));
        phoneView.setEditText(pref.getString(Constant.PHONE, ""));
        addressView.setEditText(pref.getString(Constant.ADDRESS, ""));
        countryView.setText(pref.getString(Constant.COUNTRY, ""));
        stateView.setText(pref.getString(Constant.STATE, ""));
        cityView.setEditText(pref.getString(Constant.CITY, ""));
        zipView.setEditText(pref.getString(Constant.ZIP, ""));
        source = getIntent().getStringExtra("source");
        if(source!=null && source.equals("setting"))
            titleView.getTextRight().setText("Save");
        countryView.setOnClickListener(this);
        shippingView.setOnClickListener(this);
        stateView.setOnClickListener(this);
        firstNameView.getEditText().setOnClickListener(this);
        firstNameView.setClickable(true);
        lastNameView.getEditText().setOnClickListener(this);
        lastNameView.setClickable(true);
        phoneView.getEditText().setOnClickListener(this);
        phoneView.setClickable(true);
        addressView.getEditText().setOnClickListener(this);
        addressView.setClickable(true);
        cityView.getEditText().setOnClickListener(this);
        cityView.setClickable(true);
        zipView.getEditText().setOnClickListener(this);
        zipView.setClickable(true);
    }

    public void rightOnClick(View view) {
        shippingInfo = new ShippingInfo();
        shippingInfo.firstName = firstNameView.getEditText().getText().toString();
        shippingInfo.lastName = lastNameView.getEditText().getText().toString();
        shippingInfo.phoneNumber = phoneView.getEditText().getText().toString();
        shippingInfo.address = addressView.getEditText().getText().toString();
        shippingInfo.country = countryView.getText().toString();
        shippingInfo.state = stateView.getText().toString();
        shippingInfo.city = cityView.getEditText().getText().toString();
        shippingInfo.zipcode = zipView.getEditText().getText().toString();
        if(source.equals("checkout")) {
            if (shippingInfo.firstName.isEmpty() || shippingInfo.lastName.isEmpty() || shippingInfo.country.isEmpty() || shippingInfo.state.isEmpty() || shippingInfo.city.isEmpty()|| shippingInfo.address.isEmpty()) {
                Util.alertBox(this, getString(R.string.shipping_required));
                return;
            }
        }
        if (source.equals("setting")) {
            Util.saveShippingInfo(this, shippingInfo);
            ApiService.getService().UserAPI_AddShipping(shippingInfo, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                  //progressDialog.dismiss();
                }

                @Override
                public void failure(RetrofitError error) {
                    //progressDialog.dismiss();
                    Util.alertBox(ShippingActivity.this, error);
                }
            });
            finish();
        } else if (source.equals("checkout")) {
            final ProgressDialog progressDialog= Util.getProgressDialog(ShippingActivity.this);
            ApiService.getService().ShoppingAPI_GetPaymentToken(null, new Callback<PaymentToken>() {
                @Override
                public void success(PaymentToken paymentToken, Response response) {
                    Intent intent = new Intent(ShippingActivity.this, BraintreePaymentActivity.class);
                    intent.putExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN, paymentToken.token);
                    startActivityForResult(intent, BT_REQUEST_CODE);
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Util.alertBox(ShippingActivity.this, error);
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

    @Override
    public void onClick(View v) {
        countryPicker.setVisibility(View.GONE);
        statePicker.setVisibility(View.GONE);
        if (v.getId() == R.id.country_wheel) {
            InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);

            TranslateAnimation slide = new TranslateAnimation(0, 0, 100,0 );
            slide.setDuration(1000);
            slide.setFillAfter(true);
            countryPicker.startAnimation(slide);
            countryPicker.setVisibility(View.VISIBLE);
            countryPicker.setMaxValue(Util.countries.size() - 1);
            countryPicker.setMinValue(0);
            countryPicker.setDisplayedValues(Util.countries.toArray(new String[Util.countries.size()]));
            countryPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    countryView.setText(Util.countries.get(newVal));
                    countryView.setTag(newVal);
                }
            });
        } else if (v.getId() == R.id.shipping_all) {
            countryPicker.setVisibility(View.GONE);
            statePicker.setVisibility(View.GONE);
        } else if (v.getId() == R.id.state_wheel) {

            InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
            statePicker.setVisibility(View.VISIBLE);
            int state=0;
            if(countryView.getTag()!=null)
                state = (int)countryView.getTag();
            final String[] states = Util.states[state];
            statePicker.setMaxValue(states.length - 1);
            statePicker.setMinValue(0);
            statePicker.setDisplayedValues(states);
            statePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    stateView.setText(states[newVal]);
                }
            });
        }

    }

}
