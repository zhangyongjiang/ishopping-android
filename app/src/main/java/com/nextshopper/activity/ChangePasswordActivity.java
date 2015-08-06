package com.nextshopper.activity;

import android.os.Bundle;
import android.view.View;

import com.nextshopper.view.InputItem;


public class ChangePasswordActivity extends BaseActivity{

    private InputItem editTextPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        editTextPwd = (InputItem)findViewById(R.id.change_pwd_new_pwd);
        editTextPwd.setEditTextHint(getResources().getString(R.string.min_pwd));
    }

    public void imageLeftOnClick(View view){
        finish();
    }
}
