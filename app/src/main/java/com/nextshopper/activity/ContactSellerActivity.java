package com.nextshopper.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.Message;
import com.nextshopper.rest.beans.Resource;
import com.nextshopper.view.TitleView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class ContactSellerActivity extends BaseActivity implements View.OnClickListener {
    private TextView attachView;
    private int REQUEST_CODE = 1;
    private ImageView imageView;
    private TextView subjectView;
    private static String SUBJECT="subject";
    private static String TITLE="title";
    private static String MSG_ID="msg_id";
    private static String STORE_ID="storeId";
    private EditText messageView;
    private String subject;
    private String msgId;
    private String title;
    private TitleView titleView;
    private boolean hasAttach;
    private String storeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_seller);
        subjectView = (EditText)findViewById(R.id.contact_subject);
        messageView =(EditText) findViewById(R.id.create_review_comment);
        titleView =(TitleView) findViewById(R.id.contact_seller_title);
        subject = getIntent().getStringExtra(SUBJECT);
        msgId = getIntent().getStringExtra(MSG_ID);
        storeId = getIntent().getStringExtra(STORE_ID);
        title = getIntent().getStringExtra(TITLE);
        if(subject!=null && !subject.isEmpty()){
            subjectView.setText(subject);
        }
        if(msgId!=null){
            titleView.setTextMiddle("Reply");
        }else if(title!=null){
            titleView.setTextMiddle("Contact "+ title);
        }
        attachView =(TextView) findViewById(R.id.contact_seller_attach);
        attachView.setOnClickListener(this);
        imageView =(ImageView) findViewById(R.id.contact_seller_img);
    }

    public static void startActivityForResult(Activity ctx, String subject, String msgId, int requestCode){
        Intent intent = new Intent(ctx, ContactSellerActivity.class);
        intent.putExtra(SUBJECT, subject);
        intent.putExtra(MSG_ID, msgId);
        ctx.startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Activity ctx, String subject, String storeId){
        Intent intent = new Intent(ctx, ContactSellerActivity.class);
        intent.putExtra(SUBJECT, subject);
        intent.putExtra(STORE_ID, storeId);
        ctx.startActivity(intent);
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
                    if(bitmap!=null)
                        hasAttach = true;
                    imageView.setImageBitmap(bitmap);
                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File dir = cw.getDir("imageDir", Context.MODE_PRIVATE);
                    File thumnail = new File(dir, Constant.ATTACHMENT);
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
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File dir = cw.getDir("imageDir", Context.MODE_PRIVATE);
        TypedFile typedFile = new TypedFile("image/jpeg", new File(dir, Constant.ATTACHMENT));
        Message msg = new Message();
        msg.subject = subject;
        msg.content = messageView.getText().toString();
        final ProgressDialog progressDialog= Util.getProgressDialog(this);
        if(msgId!=null) {
            ApiService.getService().MessageAPI_UserReplyMessage(msgId, msg, new Callback<Message>() {
                @Override
                public void success(Message message, Response response) {
                   progressDialog.dismiss();
                    setResultAndFinish();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Util.alertBox(ContactSellerActivity.this, error);
                }
            });
        }/*else if(storeId!=null){
            ApiService.getService().MessageAPI_UserToStoreMessage(storeId, msg, new Callback<Message>() {
                @Override
                public void success(Message message, Response response) {
                    progressDialog.dismiss();
                    setResultAndFinish();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Util.alertBox(ContactSellerActivity.this, error);
                }
            });
        }*/else{
            ApiService.getService().MessageAPI_UserToSystemMessage(msg, new Callback<Message>() {
                @Override
                public void success(Message message, Response response) {
                    progressDialog.dismiss();
                    finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    Util.alertBox(ContactSellerActivity.this, error);
                }
            });
        }


        if(hasAttach) {
            ApiService.getService().ResourceAPI_UploadForUser(typedFile, "", "", new Callback<Resource>() {
                @Override
                public void success(Resource resource, Response response) {
                }

                @Override
                public void failure(RetrofitError error) {
                    Util.alertBox(ContactSellerActivity.this, error);
                }
            });
        }

    }

    private void setResultAndFinish(){
        Intent intent = new Intent();
        intent.putExtra("content", messageView.getText().toString());
        intent.putExtra("subject", subject);
        intent.putExtra("imgPath", Constant.ATTACHMENT);
        this.setResult(RESULT_OK, intent);
        finish();
    }

    public static void startActivity(Context ctx, String title){
        Intent intent = new Intent(ctx, ContactSellerActivity.class);
        intent.putExtra(TITLE, title);
        ctx.startActivity(intent);
    }
}
