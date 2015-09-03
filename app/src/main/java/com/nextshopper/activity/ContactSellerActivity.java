package com.nextshopper.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;

import java.util.ArrayList;
import java.util.List;


public class ContactSellerActivity extends BaseActivity implements View.OnClickListener {
    private TextView attachView;
    private int REQUEST_CODE = 1;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_seller);
        attachView =(TextView) findViewById(R.id.contact_seller_attach);
        attachView.setOnClickListener(this);
        imageView =(ImageView) findViewById(R.id.contact_seller_img);
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
        finish();
    }

}
