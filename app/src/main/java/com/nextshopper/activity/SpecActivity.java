package com.nextshopper.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.rest.BitmapWorkerTask;


public class SpecActivity extends BaseActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec);
        imageView = (ImageView) findViewById(R.id.spec_img);
        textView =(TextView) findViewById(R.id.spec_des_details);
       Intent intent =  getIntent();
        String imgUrl  = intent.getStringExtra("imgUrl");
        imageView.setTag(imgUrl);
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, false, 0);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imgUrl);
        String spec = intent.getStringExtra("spec");
        textView.setText(spec);


    }

}
