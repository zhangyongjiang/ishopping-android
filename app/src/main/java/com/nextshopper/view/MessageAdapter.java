package com.nextshopper.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nextshopper.activity.MsgThreadActivity;
import com.nextshopper.activity.R;
import com.nextshopper.common.Constant;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.MessageDetails;
import com.nextshopper.rest.beans.MessageDetailsList;

import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 9/8/15.
 */
public class MessageAdapter extends NextShopperAdapter<MessageDetails> implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    private Activity ctx;
    private ListView listView;
    private String userId;

    public MessageAdapter(Activity ctx, ListView listView) {
        this.ctx = ctx;
        this.listView = listView;
        this.listView.setOnItemClickListener(this);
        SharedPreferences pre = ctx.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
        userId = pre.getString(Constant.USER_ID, "");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MessageDetails messageDetails = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.message_item, parent, false);
        }
        ImageView logoView = (ImageView) convertView.findViewById(R.id.message_item_img);
        String imageUrl = null;
        TextView senderView = (TextView) convertView.findViewById(R.id.message_item_sender);
        TextView titleView = (TextView) convertView.findViewById(R.id.message_item_title);
        TextView contentView = (TextView) convertView.findViewById(R.id.message_item_content);
        TextView timeView = (TextView) convertView.findViewById(R.id.message_item_time);
        if (userId.equals(messageDetails.message.senderId)) {
            if (messageDetails.userReceiverInfo != null) {
                if (messageDetails.userReceiverInfo.info != null) {
                    imageUrl = messageDetails.userReceiverInfo.info.imgPath;
                    senderView.setText(messageDetails.userReceiverInfo.info.firstName + " " + messageDetails.userReceiverInfo.info.lastName);
                }
            } else if (messageDetails.storeReceiverInfo != null) {
                imageUrl = messageDetails.storeReceiverInfo.info.logo;
                senderView.setText(messageDetails.storeReceiverInfo.info.name);
            }

        } else if (userId.equals(messageDetails.message.recipientId)) {
            if (messageDetails.userSenderInfo != null) {
                if (messageDetails.userSenderInfo.info != null) {
                    imageUrl = messageDetails.userSenderInfo.info.imgPath;
                    senderView.setText(messageDetails.userSenderInfo.info.firstName + " " + messageDetails.userSenderInfo.info.lastName);
                } else if (messageDetails.storeSenderInfo != null) {
                    imageUrl = messageDetails.storeSenderInfo.info.logo;
                    senderView.setText(messageDetails.storeReceiverInfo.info.name);
                }
            }
        }
        titleView.setText(messageDetails.message.subject);
        contentView.setText(messageDetails.message.content);
        timeView.setText(new Date(messageDetails.message.created).toString().substring(0, 10));
        if (imageUrl != null) {
            logoView.setTag(imageUrl);
            ((NextShopperApplication) ctx.getApplicationContext()).loadBitmaps(imageUrl, logoView, false, 0);
        }
        return convertView;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount() && call) {
            final ProgressDialog progressDialog = Util.getProgressDialog(ctx);
            call = false;
            ApiService.getService().MessageAPI_GetUserMessages(start, numOfItem, new Callback<MessageDetailsList>() {
                @Override
                public void success(MessageDetailsList messageDetailsList, Response response) {
                    progressDialog.dismiss();
                    if (messageDetailsList.items.size() > 0) {
                        MessageAdapter.this.list.addAll(messageDetailsList.items);
                        notifyDataSetChanged();
                    }
                    if (messageDetailsList.items.size() == numOfItem)
                        call = true;
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Util.alertBox(ctx, error);
                }
            });

            start = start + numOfItem;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MsgThreadActivity.startActivity(ctx, getItem(position).message.id);
    }

}
