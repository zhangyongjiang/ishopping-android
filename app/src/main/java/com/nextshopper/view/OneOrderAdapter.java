package com.nextshopper.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.OrderDetailsActivity;
import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.beans.OrderItemDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siyiliu on 9/6/15.
 */
public class OneOrderAdapter extends BaseAdapter{
    private List<OrderItemDetails> orderItemDetailsList = new ArrayList<>();
    private Activity activity;

    public OneOrderAdapter(Activity ctx){
        this.activity = ctx;
    }

    @Override
    public int getCount() {
        return orderItemDetailsList.size();
    }

    @Override
    public OrderItemDetails getItem(int position) {
        return orderItemDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OrderItemDetails orderItemDetails = orderItemDetailsList.get(position);
        if(convertView==null){
            convertView = LayoutInflater.from(activity).inflate(R.layout.order_history_item, parent, false);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.item_img);
        imageView.setTag(orderItemDetails.product.imgs.get(0));
        ((NextShopperApplication)activity.getApplicationContext()).loadBitmaps(orderItemDetails.product.imgs.get(0), imageView, false, 0);
        TextView nameView = (TextView)convertView.findViewById(R.id.order_item_name);
        nameView.setText(orderItemDetails.product.name);
        TextView quView = (TextView) convertView.findViewById(R.id.order_item_quantity);
        TextView priceView =(TextView) convertView.findViewById(R.id.order_item_price);
        quView.setText(String.format(activity.getResources().getString(R.string.quantity_num), orderItemDetails.item.quantity));
        priceView.setText(String.format(activity.getString(R.string.price_usd), orderItemDetails.product.salePrice));
        if(activity instanceof OrderDetailsActivity){
            View raveView = convertView.findViewById(R.id.item_rave_it);
            raveView.setVisibility(View.GONE);
            View contactSeller = convertView.findViewById(R.id.item_contact_seller);
            contactSeller.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void updateList(List<OrderItemDetails> orderItemDetailsList){
        this.orderItemDetailsList.addAll(orderItemDetailsList);
        notifyDataSetChanged();
    }

}
