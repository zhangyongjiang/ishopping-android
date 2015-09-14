package com.nextshopper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ContactSellerActivity extends BaseActivity implements View.OnClickListener {
    private TextView attachView;
    private int REQUEST_CODE = 1;
    private ImageView imageView;
    private TextView subjectView;
    private static String SUBJECT="subject";
    private EditText messageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_seller);
        subjectView = (TextView)findViewById(R.id.contact_subject);
        messageView =(EditText) findViewById(R.id.create_review_comment);
        String subject = getIntent().getStringExtra(SUBJECT);
        if(subject!=null && !subject.isEmpty()){
            subjectView.setText(subject);
        }
        attachView =(TextView) findViewById(R.id.contact_seller_attach);
        attachView.setOnClickListener(this);
        imageView =(ImageView) findViewById(R.id.contact_seller_img);
    }

    public static void startActivityForResult(Activity ctx, String subject, int requestCode){
        Intent intent = new Intent(ctx, ContactSellerActivity.class);
        intent.putExtra(SUBJECT, subject);
        ctx.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooserIntent = Intent.createChooser(intent,"Select Source");
        List<Intent> cameraIntents = new ArrayList<Intent>();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntents.add(cameraIntent);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        startActivityForResult(chooserIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    //Bitmap thumnailCamera = data.getParcelableExtra("data");
                    Uri imageUri = data.getData();
                    Bitmap bitmap = Util.getThumbnail(this, imageUri, 240);
                    imageView.setImageBitmap(bitmap);
                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File dir = cw.getDir("imageDir", Context.MODE_PRIVATE);
                    File thumnail = new File(dir, Constant.THUMNAIL);
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(thumnail);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } catch (Exception e) {
                        Log.e(Constant.NEXTSHOPPER, e.getMessage(), e);
                    }
                }
                catch (Exception e) {
                    Log.e(Constant.NEXTSHOPPER, e.getMessage(), e);
                }

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }

    public void rightOnClick(View view) {
         Intent intent = new Intent();
         intent.putExtra("message", messageView.getText().toString());
         this.setResult(RESULT_OK, intent);
         finish();
    }

}
