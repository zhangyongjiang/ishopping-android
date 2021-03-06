package com.nextshopper.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.Gender;
import com.nextshopper.rest.beans.RegisterRequest;
import com.nextshopper.rest.beans.User;
import com.nextshopper.view.TitleView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;

public class SignupActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private TitleView titleView;
    private TextView textRightJoin;
    private ImageView imageLeft;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private Spinner gender;
    private ViewStub photoViewStub;
    private String genderChosen;
    private ImageView singup_pics;
    private AlertDialog dialog;
    private static final String INVITATION_CODE = "DRAGON";
    private static final String ACTIVITY_NAME = SignupActivity.class.toString();
    private static final int CAPTURE_ACTIVITY_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_REQUEST = 200;
    private File dir;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_ACTIVITY_REQUEST_CODE || requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                try {
                        //Bitmap thumnailCamera = data.getParcelableExtra("data");
                        Uri imageUri = data.getData();
                        Bitmap thumnailBitmap = Bitmap.createScaledBitmap(Util.getThumbnail(this, imageUri,240), 240, 240,false);
                        Bitmap roundBitmap = Util.toRoundCorner(thumnailBitmap, 120);
                        singup_pics.setImageBitmap(roundBitmap);
                        photoViewStub.setVisibility(View.INVISIBLE);
                        // save image
                        ContextWrapper cw = new ContextWrapper(getApplicationContext());
                        dir = cw.getDir("imageDir", Context.MODE_PRIVATE);
                        File thumnail = new File(dir, Constant.THUMNAIL);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(thumnail);
                            roundBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (Exception e) {
                            Log.e(ACTIVITY_NAME, e.getMessage(), e);
                        }
                    }
                 catch (Exception e) {
                    Log.e(ACTIVITY_NAME, e.getMessage(), e);
                }

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }

    void findView() {
        titleView = (TitleView) findViewById(R.id.signup_title);
        textRightJoin = titleView.getTextRight();
        imageLeft = titleView.getImageLeft();
        firstName = (EditText) findViewById(R.id.signup_firstname);
        lastName = (EditText) findViewById(R.id.signup_lastname);
        email = (EditText) findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_password);
        gender = (Spinner) findViewById(R.id.signup_gender);
        gender.setOnItemSelectedListener(this);
        singup_pics = (ImageView) findViewById(R.id.signup_pics);
        // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //gender.setAdapter(adapter);
    }

    public void rightOnClick(View view) {
        RegisterRequest request = new RegisterRequest();
        if (firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            if (dialog != null) {
                dialog.show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            dialog = builder.create();
            dialog.show();
            return;
        }
        request.firstName = firstName.getText().toString();
        request.lastName = lastName.getText().toString();
        request.email = email.getText().toString();
        request.password = password.getText().toString();
        request.gender = Gender.valueOf(genderChosen);
        request.invitationCode = INVITATION_CODE;
        NextShopperService service = ApiService.getService();
        final ProgressDialog progressDialog= Util.getProgressDialog(SignupActivity.this);
        service.UserAPI_Register(request, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                SignupActivity.this.user = user;
                String cookie = Util.getCookieString(response);
                Util.saveCookie(SignupActivity.this, cookie);
                ApiService.buildService(cookie);
                if (dir != null) {
                    TypedFile typedFile = new TypedFile("image/jpeg", new File(dir, Constant.THUMNAIL));
                    ApiService.getService().UserAPI_Upload(typedFile, new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {
                            progressDialog.dismiss();
                            SignupActivity.this.user.info.imgPath = user.info.imgPath;
                            Log.d(ACTIVITY_NAME, user.info.imgPath);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            progressDialog.dismiss();
                            Log.e(ACTIVITY_NAME, error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);
                            Util.alertBox(SignupActivity.this, error);
                        }
                    });
                }
                Util.saveUserData(SignupActivity.this, user);
                Util.sendRegistrationToServer(SignupActivity.this);
                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                intent.putExtra(Constant.USER_ID, user.id);
                SignupActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Util.alertBox(SignupActivity.this, error);
                Log.e(ACTIVITY_NAME, error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);
            }
        });

    }

    public void imageLeftOnClick(View view) {
        finish();
    }

    public void photoUploadOnClick(View view) {
        if (photoViewStub != null) {
            photoViewStub.setVisibility(View.VISIBLE);
            return;
        }
        photoViewStub = (ViewStub) findViewById(R.id.signup_upload);
        if (photoViewStub != null) {
            View infatedView = photoViewStub.inflate();
        }
    }

    public void cameraOnClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // Uri fileUri = getOutputMediaFileUri();
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_ACTIVITY_REQUEST_CODE);
    }

    public void galleryOnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void cancelOnClick(View view) {
        if (photoViewStub != null) {
            photoViewStub.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        genderChosen = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private static Uri getOutputMediaFileUri() {
        File photoFile = new File(Environment.DIRECTORY_PICTURES);
        if (!photoFile.exists()) {
            if (!photoFile.mkdir())
                return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(photoFile.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return Uri.fromFile(mediaFile);
    }


}
