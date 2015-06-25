package com.nextshopper.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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

import com.nextshopper.api.ApiService;
import com.nextshopper.api.UserService;
import com.nextshopper.bean.RegisterRequest;
import com.nextshopper.bean.User;
import com.nextshopper.view.TitleView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.nextshopper.bean.Gender.valueOf;

public class SignupActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private TitleView titleView;
    private TextView textRightJoin;
    private ImageView imageLeft;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private Spinner gender;
    private ViewStub photoViewStub;
    private ViewStub genderViewStub;
    private String genderChosen;
    private ImageView singup_pics;
    private static final String INVITATION_CODE = "DRAGON";
    private static final int CAPTURE_ACTIVITY_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap,240,240,false);
                    scaled = toRoundCorner(scaled,120);
                    singup_pics.setImageBitmap(scaled);
                    photoViewStub.setVisibility(View.INVISIBLE);
                }catch (Exception e){
                    Log.e("TAG", e.getMessage(), e);
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
        request.setFirstName(firstName.getText().toString());
        request.setLastName(lastName.getText().toString());
        request.setEmail(email.getText().toString());
        request.setPassword(password.getText().toString());
        request.setGender(valueOf(genderChosen));
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

    public void imageLeftOnClick(View view) {
        finish();
    }

    public void photoUploadOnClick(View view) {
        if(photoViewStub !=null) {
            photoViewStub.setVisibility(View.VISIBLE);
            return;
        }
        photoViewStub = (ViewStub) findViewById(R.id.signup_upload);
        if (photoViewStub != null) {
            View infatedView = photoViewStub.inflate();
            TextView cameraChooseView = (TextView) infatedView.findViewById(R.id.photo_upload_camera);
            TextView galleryView = (TextView) infatedView.findViewById(R.id.photo_upload_gallery);
            TextView cancelView = (TextView) infatedView.findViewById(R.id.photo_upload_cancel);
        }
    }

    public void cameraOnClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        genderChosen =  adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private static Uri getOutputMediaFileUri(){
        File photoFile = new File(Environment.DIRECTORY_PICTURES);
        if(!photoFile.exists()){
            if(!photoFile.mkdir())
                return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(photoFile.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
        return Uri.fromFile(mediaFile);
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
