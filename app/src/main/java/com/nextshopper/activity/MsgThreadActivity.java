package com.nextshopper.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.Message;
import com.nextshopper.rest.beans.MessageDetails;
import com.nextshopper.rest.beans.MessageThread;
import com.nextshopper.rest.beans.User;
import com.nextshopper.rest.beans.UserBasicInfo;
import com.nextshopper.view.MessageThreadAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MsgThreadActivity extends BaseActivity {
    private static String MSG_ID = "MsgId";
    private ListView listView;
    private TextView subjectView;
    private String subject;
    private int REQUEST_CODE=1;
    private String msgId;
    private MessageThreadAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_thread);
        msgId = getIntent().getStringExtra(MSG_ID);
        adapter = new MessageThreadAdapter(this);
        listView =(ListView) findViewById(R.id.msg_thread_listview);
        listView.setAdapter(adapter);
        subjectView = (TextView) findViewById(R.id.msg_thread_subject);
        final ProgressDialog progressDialog= Util.getProgressDialog(this);

        ApiService.getService().MessageAPI_GetUserMessageThreadsByMsgId(msgId, new Callback<MessageThread>() {
            @Override
            public void success(MessageThread messageThread, Response response) {
                progressDialog.dismiss();
                subjectView.setText(messageThread.items.get(0).message.subject);
                subject = messageThread.items.get(0).message.subject;
                adapter.updateList(messageThread.items);
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Util.alertBox(MsgThreadActivity.this, error);
            }
        });
    }

    public static void startActivity(Context ctx, String msgId){
        Intent intent = new Intent(ctx, MsgThreadActivity.class);
        intent.putExtra(MSG_ID, msgId);
        ctx.startActivity(intent);
    }

    public void rightOnClick(View view){
        ContactSellerActivity.startActivityForResult(this, subject, msgId, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
               MessageDetails messageDetails = new MessageDetails();
                Message msg = new Message();
                msg.subject = data.getStringExtra("subject");
                msg.content = data.getStringExtra("content");
                msg.attachments = new ArrayList<>();
                msg.attachments.add(data.getStringExtra("content"));
                messageDetails.message = msg;
                User user = new User();
                user.info = new UserBasicInfo();
                SharedPreferences pref = this.getSharedPreferences(Constant.USER, MODE_PRIVATE);
                user.info.firstName = pref.getString(Constant.FIRST_NAME, "");
                user.info.lastName = pref. getString(Constant.LAST_NAME, "");
                user.info.imgPath = pref.getString(Constant.IMG_PATH, "");
                messageDetails.userReceiverInfo = user;
                List<MessageDetails> list = new ArrayList<>();
                list.add(messageDetails);
                adapter.updateList(list);
            }
        }
    }

}
