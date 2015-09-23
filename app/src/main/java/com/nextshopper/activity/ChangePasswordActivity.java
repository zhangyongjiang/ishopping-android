package com.nextshopper.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.ChangePasswordRequest;
import com.nextshopper.rest.beans.GenericResponse;
import com.nextshopper.view.InputItem;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ChangePasswordActivity extends BaseActivity{

    private InputItem editTextPwd;
    private InputItem current;
    private InputItem confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        current =(InputItem) findViewById(R.id.change_pwd_current);
        confirm =(InputItem) findViewById(R.id.change_pwd_confirm);
        editTextPwd = (InputItem)findViewById(R.id.change_pwd_new_pwd);
        editTextPwd.setEditTextHint(getResources().getString(R.string.min_pwd));
    }

    public void rightOnClick(View view){
        if(current.getEditText().getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.current_pwd_required).setTitle(R.string.error);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if(editTextPwd.getEditText().getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.new_pwd_required).setTitle(R.string.error);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if(!editTextPwd.getEditText().getText().toString().equals(confirm.getEditText().getText().toString())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.pwd_not_match).setTitle(R.string.error);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else{
            ChangePasswordRequest request = new ChangePasswordRequest();
            request.oldPassword = current.getEditText().getText().toString();
            request.password = editTextPwd.getEditText().getText().toString();
            final ProgressDialog progressDialog= Util.getProgressDialog(ChangePasswordActivity.this);
            ApiService.getService().UserAPI_ChangePassword(request, new Callback<GenericResponse>() {
                @Override
                public void success(GenericResponse genericResponse, Response response) {
                    progressDialog.dismiss();
                    finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Util.alertBox(ChangePasswordActivity.this, ((GenericResponse) error.getBody()).errorCode);
                     }
            });
        }
    }
}
