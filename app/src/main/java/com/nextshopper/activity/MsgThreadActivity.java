package com.nextshopper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.MessageDetails;
import com.nextshopper.rest.beans.MessageThread;
import com.nextshopper.view.MessageThreadAdapter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MsgThreadActivity extends BaseActivity {
    private static String MSG_ID = "MsgId";
    private ListView listView;
    private TextView subjectView;
    private String subject;
    private int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_thread);
        String msgId = getIntent().getStringExtra(MSG_ID);
        final MessageThreadAdapter adapter = new MessageThreadAdapter(this);
        listView =(ListView) findViewById(R.id.msg_thread_listview);
        listView.setAdapter(adapter);
        subjectView = (TextView) findViewById(R.id.msg_thread_subject);


        ApiService.getService().MessageAPI_GetStoreMessageThreadsByMsgId(msgId, new Callback<MessageThread>() {
            @Override
            public void success(MessageThread messageThread, Response response) {
                subjectView.setText(messageThread.items.get(0).message.subject);
                subject = messageThread.items.get(0).message.subject;
                adapter.updateList(messageThread.items);
            }

            @Override
            public void failure(RetrofitError error) {
                Util.alertBox(MsgThreadActivity.this, error.getMessage());
            }
        });
    }

    public static void startActivity(Context ctx, String msgId){
        Intent intent = new Intent(ctx, MsgThreadActivity.class);
        intent.putExtra(MSG_ID, msgId);
        ctx.startActivity(intent);
    }

    public void rightOnClick(View view){
        ContactSellerActivity.startActivityForResult(this, subject, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE){
            if(resultCode== RESULT_OK){
                MessageDetails messageDetails = new MessageDetails();

            }
        }
    }

}
