package com.nextshopper.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.nextshopper.api.NextShopperService;
import com.nextshopper.bean.RegisterRequest;
import com.nextshopper.bean.User;
import com.nextshopper.view.TitleView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;

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
    private AlertDialog dialog;
    private String imagePath;
    private static final String INVITATION_CODE = "DRAGON";
    private static final String ACTIVITY_NAME = SignupActivity.class.toString();
    private static final int CAPTURE_ACTIVITY_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_REQUEST = 200;


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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
                    scaled = toRoundCorner(scaled, 120);
                    singup_pics.setImageBitmap(scaled);
                    photoViewStub.setVisibility(View.INVISIBLE);
                    //upload image
                    // ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    //scaled.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    //TypedByteArray typedByteArray = new TypedByteArray("image/jpeg",bos.toByteArray());
                    imagePath = getRealPathFromURI(this, data.getData());

                } catch (Exception e) {
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
        request.setFirstName(firstName.getText().toString());
        request.setLastName(lastName.getText().toString());
        request.setEmail(email.getText().toString());
        request.setPassword(password.getText().toString());
        request.setGender(valueOf(genderChosen));
        request.setInvitationCode(INVITATION_CODE);
        NextShopperService service = ApiService.getService();
        service.register(request, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                String cookie = getCookieString(response);
                ApiService.buildService(cookie);
                TypedFile typedFile = new TypedFile("image/jpeg", new File(imagePath));
                ApiService.getService().uploadImage(typedFile, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Log.d(ACTIVITY_NAME, user.getId());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(ACTIVITY_NAME, error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);
                    }
                });

                Intent intent = new Intent(SignupActivity.this, TempActivity.class);
                intent.putExtra("userId", user.getId());
                SignupActivity.this.startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
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
        Uri fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
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

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private String getCookieString(Response response) {
        for (Header header : response.getHeaders()) {
            if (null != header.getName() && header.getName().equals("Set-Cookie")) {
                return header.getValue();
            }
        }
        return null;
    }
}
