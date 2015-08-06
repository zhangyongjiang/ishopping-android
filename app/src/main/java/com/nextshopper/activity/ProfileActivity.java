package com.nextshopper.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.nextshopper.common.Constant;
import com.nextshopper.view.InputItem;

import java.io.File;


public class ProfileActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private InputItem firstName;
    private InputItem lastName;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Spinner gender =(Spinner)findViewById(R.id.profile_gender);
        gender.setOnItemSelectedListener(this);
        firstName = (InputItem) findViewById(R.id.profile_firstname);
        lastName =(InputItem) findViewById(R.id.profile_lastname);
        imageView = (ImageView) findViewById(R.id.profile_img);
        SharedPreferences pref = getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
        firstName.setEditText(pref.getString(Constant.FIRST_NAME, ""));
        lastName.setEditText(pref.getString(Constant.LAST_NAME, ""));
        ArrayAdapter genderAdapter = (ArrayAdapter)gender.getAdapter();
        int pos = genderAdapter.getPosition(pref.getString(Constant.GENDER, ""));
        gender.setSelection(pos);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File imageFile = new File(cw.getDir("imageDir", Context.MODE_PRIVATE), Constant.THUMNAIL);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void imageLeftOnClick(View view){
        finish();
    }

}
