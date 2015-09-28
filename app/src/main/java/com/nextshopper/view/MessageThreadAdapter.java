package com.nextshopper.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.R;
import com.nextshopper.common.Constant;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.beans.MessageDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by siyiliu on 9/12/15.
 */
public class MessageThreadAdapter extends BaseAdapter {
    private Context ctx;
    private List<MessageDetails> detailsList = new ArrayList<>();
    private String userId;

    public MessageThreadAdapter(Context ctx){
        this.ctx = ctx;
        SharedPreferences pre = ctx.getSharedPreferences(Constant.USER_ID,Context.MODE_PRIVATE);
        userId = pre.getString(Constant.USER_ID,"");
    }

    @Override
    public int getCount() {
        return detailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailsList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MessageDetails messageDetails = detailsList.get(position);
        if(convertView==null){
            convertView = LayoutInflater.from(ctx).inflate(R.layout.message_item, parent, false);
        }
        ImageView arrowView =(ImageView) convertView.findViewById(R.id.message_arrow);
        arrowView.setVisibility(View.GONE);
        ImageView logoView = (ImageView)convertView.findViewById(R.id.message_item_img);
        String imageUrl = null;
        TextView senderView = (TextView) convertView.findViewById(R.id.message_item_sender);
        TextView titleView = (TextView)convertView.findViewById(R.id.message_item_title);
        TextView contentView = (TextView) convertView.findViewById(R.id.message_item_content);
        TextView timeView = (TextView) convertView.findViewById(R.id.message_item_time);
        if(userId.equals(messageDetails.message.senderId)){
            if(messageDetails.userReceiverInfo!=null) {
                if(messageDetails.userReceiverInfo.info!=null) {
                    imageUrl = messageDetails.userReceiverInfo.info.imgPath;
                    senderView.setText(messageDetails.userReceiverInfo.info.firstName+" "+messageDetails.userReceiverInfo.info.lastName);
                }
            }else if(messageDetails.storeReceiverInfo!=null){
                imageUrl = messageDetails.storeReceiverInfo.info.logo;
                senderView.setText(messageDetails.storeReceiverInfo.info.name);
            }

        }else if(userId.equals(messageDetails.message.recipientId)){
            if(messageDetails.userSenderInfo!=null){
                if(messageDetails.userSenderInfo.info!=null){
                    imageUrl = messageDetails.userSenderInfo.info.imgPath;
                    senderView.setText(messageDetails.userSenderInfo.info.firstName+" "+messageDetails.userSenderInfo.info.lastName);
                }else if(messageDetails.storeSenderInfo!=null){
                    imageUrl = messageDetails.storeSenderInfo.info.logo;
                    senderView.setText(messageDetails.storeReceiverInfo.info.name);
                }
            }
        }

        titleView.setText(messageDetails.message.subject);
        contentView.setText(messageDetails.message.content);
        timeView.setText(new Date(messageDetails.message.created).toString().substring(0,10));
        if(imageUrl!=null) {
            logoView.setTag(imageUrl);
            ((NextShopperApplication) ctx.getApplicationContext()).loadBitmaps(imageUrl, logoView, false, 0);
        }
       // ContextWrapper cw = new ContextWrapper(ctx.getApplicationContext());
        //File imageFile = new File(cw.getDir("imageDir", Context.MODE_PRIVATE), Constant.ATTACH);
        //Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        return convertView;
    }

    public void updateList(List<MessageDetails> list){
        detailsList.addAll(list);
        notifyDataSetChanged();
    }
}
