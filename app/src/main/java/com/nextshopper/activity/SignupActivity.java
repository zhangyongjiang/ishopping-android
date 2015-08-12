package com.nextshopper.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.nextshopper.common.Constant;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.Gender;
import com.nextshopper.rest.beans.RegisterRequest;
import com.nextshopper.rest.beans.User;
import com.nextshopper.view.TitleView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
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
                        Bitmap thumnailBitmap = Bitmap.createScaledBitmap(getThumbnail(imageUri,240), 240, 240,false);
                        Bitmap roundBitmap = toRoundCorner(thumnailBitmap, 120);
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
        saveUserData(request);
        NextShopperService service = ApiService.getService();
        service.UserAPI_Register(request, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                String cookie = getCookieString(response);
                ApiService.buildService(cookie);
                if (dir != null) {
                    TypedFile typedFile = new TypedFile("image/jpeg", new File(dir, Constant.THUMNAIL));
                    ApiService.getService().UserAPI_Upload(typedFile, new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {
                            Log.d(ACTIVITY_NAME, user.info.imgPath);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e(ACTIVITY_NAME, error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);
                        }
                    });
                }

                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                intent.putExtra(Constant.USER_ID, user.id);
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

    public Bitmap getThumbnail(Uri uri, int THUMBNAIL_SIZE) throws FileNotFoundException, IOException {
        InputStream input = this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int inSampleSize = calculateInSampleSize(onlyBoundsOptions, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = inSampleSize;
        return BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), null,o2);
    }
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float)height / (float)reqHeight);
            final int widthRatio = Math.round((float)width / (float)reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private String getCookieString(Response response) {
        for (Header header : response.getHeaders()) {
            if (null != header.getName() && header.getName().equals("Set-Cookie")) {
                return header.getValue();
            }
        }
        return null;
    }

    private void saveUserData(RegisterRequest request) {
        SharedPreferences.Editor editor = this.getSharedPreferences(Constant.USER, MODE_PRIVATE).edit();
        editor.putString(Constant.FIRST_NAME, request.firstName);
        editor.putString(Constant.LAST_NAME, request.lastName);
        editor.putString(Constant.EMAIL, request.email);
        editor.putString(Constant.PASSWORD, request.password);
        editor.putString(Constant.GENDER, request.gender.toString());
        editor.commit();
    }
}
