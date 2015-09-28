package com.nextshopper.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.Gender;
import com.nextshopper.rest.beans.User;
import com.nextshopper.rest.beans.UserBasicInfo;
import com.nextshopper.view.InputItem;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ProfileActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, TextWatcher{

    private InputItem firstName;
    private InputItem lastName;
    private ImageView imageView;
    private boolean changed;
    private Spinner genderSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        genderSpinner =(Spinner)findViewById(R.id.profile_gender);
        genderSpinner.setOnItemSelectedListener(this);
        firstName = (InputItem) findViewById(R.id.profile_firstname);
        lastName =(InputItem) findViewById(R.id.profile_lastname);
        imageView = (ImageView) findViewById(R.id.profile_img);
        SharedPreferences pref = getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
        firstName.setEditText(pref.getString(Constant.FIRST_NAME, ""));
        lastName.setEditText(pref.getString(Constant.LAST_NAME, ""));
        ArrayAdapter genderAdapter = (ArrayAdapter)genderSpinner.getAdapter();
        int pos = genderAdapter.getPosition(pref.getString(Constant.GENDER, ""));
        genderSpinner.setSelection(pos);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File imageFile = new File(cw.getDir("imageDir", Context.MODE_PRIVATE), Constant.THUMNAIL);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
        firstName.getEditText().addTextChangedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void rightOnClick(View view) {
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            userBasicInfo.firstName = firstName.getEditText().getText().toString();
            userBasicInfo.lastName = lastName.getEditText().getText().toString();
            userBasicInfo.gender = Gender.valueOf(genderSpinner.getSelectedItem().toString());
            ApiService.getService().UserAPI_Update(userBasicInfo, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    Util.saveUserData(ProfileActivity.this, user);
                }

                @Override
                public void failure(RetrofitError error) {
                  Util.alertBox(ProfileActivity.this, error);
                }
            });
            finish();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
       changed = true;
    }
}
