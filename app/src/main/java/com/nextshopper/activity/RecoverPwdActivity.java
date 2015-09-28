package com.nextshopper.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.GenericResponse;
import com.nextshopper.rest.beans.LoginRequest;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RecoverPwdActivity extends BaseActivity {
    private EditText emailEditTExt;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pwd);
        emailEditTExt = (EditText) findViewById(R.id.recover_pwd_email);
    }



    public void imageLeftOnClick(View view){
        finish();
    }

    public void rightOnClick(View view){
        LoginRequest request = new LoginRequest();
        request.email= emailEditTExt.getText().toString();
        final ProgressDialog progressDialog= Util.getProgressDialog(this);
        ApiService.getService().UserAPI_ResetPassword(request, new Callback<GenericResponse>(){

            @Override
            public void success(GenericResponse genericResponse, Response response) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(RecoverPwdActivity.this);
                builder.setMessage(R.string.recover_pwd_sent).setTitle(R.string.dialog_infomration);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                dialog = builder.create();
                dialog.show();
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(RecoverPwdActivity.this);
                builder.setMessage(((GenericResponse) error.getBody()).errorCode+", "+((GenericResponse) error.getBody()).errorMsg).setTitle(R.string.dialog_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

    }
}
