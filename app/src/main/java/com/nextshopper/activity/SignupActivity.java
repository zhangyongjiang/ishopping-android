package com.nextshopper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.api.ApiService;
import com.nextshopper.api.UserService;
import com.nextshopper.bean.Gender;
import com.nextshopper.bean.RegisterRequest;
import com.nextshopper.bean.User;
import com.nextshopper.view.TitleView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignupActivity extends Activity {
    private TitleView titleView;
    private TextView textRightJoin;
    private ImageView imageLeft;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText gender;
    private static String INVITATION_CODE="DRAGON";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findView();
    }
    void findView(){
        titleView = (TitleView)findViewById(R.id.signup_title);
        textRightJoin = titleView.getTextRight();
        imageLeft = titleView.getImageLeft();
        firstName = (EditText)findViewById(R.id.signup_firstname);
        lastName = (EditText)findViewById(R.id.signup_lastname);
        email = (EditText)findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_password);
        gender = (EditText) findViewById(R.id.signup_gender);
    }

    public void rightOnClick(View view){
        RegisterRequest request= new RegisterRequest();
        request.setFirstName(firstName.getText().toString());
        request.setLastName(lastName.getText().toString());
        request.setEmail(email.getText().toString());
        request.setPassword(password.getText().toString());
        request.setGender(Gender.valueOf(gender.getText().toString()));
        request.setInvitationCode(INVITATION_CODE);
        UserService service = ApiService.getUserService();
        service.register(request, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Intent intent = new Intent(SignupActivity.this, TempActivity.class);
                intent.putExtra("userId", user.getId());
                SignupActivity.this.startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("TAG", error.getMessage(), error);
            }
        });

    }

    public void imageLeftOnClick(View view){
        finish();
    }
}
