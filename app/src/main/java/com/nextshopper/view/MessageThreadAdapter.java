package com.nextshopper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.beans.MessageDetails;
import com.nextshopper.rest.beans.MsgType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by siyiliu on 9/12/15.
 */
public class MessageThreadAdapter extends BaseAdapter {
    private Context ctx;
    private List<MessageDetails> detailsList = new ArrayList<>();

    public MessageThreadAdapter(Context ctx){
        this.ctx = ctx;
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
        ImageView logoView = (ImageView)convertView.findViewById(R.id.message_item_img);
        String imageUrl = null;
        TextView senderView = (TextView) convertView.findViewById(R.id.message_item_sender);
        TextView titleView = (TextView)convertView.findViewById(R.id.message_item_title);
        TextView contentView = (TextView) convertView.findViewById(R.id.message_item_content);
        TextView timeView = (TextView) contentView.findViewById(R.id.message_item_time);
        if(messageDetails.message.type.equals(MsgType.Store2User)){
            imageUrl = messageDetails.storeSenderInfo.info.logo;
            senderView.setText(messageDetails.storeSenderInfo.info.name);
        }else if(messageDetails.message.type.equals(MsgType.User2User)||messageDetails.message.type.equals(MsgType.User2Store)){
            imageUrl = messageDetails.userSenderInfo.info.imgPath;
            senderView.setText(messageDetails.userSenderInfo.info.firstName+" "+messageDetails.userSenderInfo.info.lastName);
        }
        titleView.setText(messageDetails.message.subject);
        contentView.setText(messageDetails.message.content);
        timeView.setText(new Date(messageDetails.message.created).toString());
        logoView.setTag(imageUrl);
        ((NextShopperApplication) ctx.getApplicationContext()).loadBitmaps(imageUrl, logoView, false, 0);
       // ContextWrapper cw = new ContextWrapper(ctx.getApplicationContext());
        //File imageFile = new File(cw.getDir("imageDir", Context.MODE_PRIVATE), Constant.ATTACH);
        //Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        return contentView;
    }

    public void updateList(List<MessageDetails> list){
        detailsList.addAll(list);
        notifyDataSetChanged();
    }
}
