package com.nextshopper.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nextshopper.activity.ContactSellerActivity;
import com.nextshopper.activity.OrderDetailsActivity;
import com.nextshopper.activity.R;
import com.nextshopper.activity.ReviewOrderActivity;
import com.nextshopper.common.Constant;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.OrderItemDetails;
import com.nextshopper.rest.beans.OrderItemList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by siyiliu on 9/6/15.
 */
public class OrderHistoryAdapter extends BaseAdapter implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, View.OnClickListener{
    private List<OrderItemDetails> orderItemDetailsList = new ArrayList<>();
    private int start = 0;
    private int numOfItem = 20;
    private Activity ctx;
    private ListView listView;

    public OrderHistoryAdapter(Activity ctx, ListView listView){
        this.ctx = ctx;
        this.listView = listView;
        this.listView.setOnItemClickListener(this);
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.order_history_item, parent, false);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.item_img);
        imageView.setTag(orderItemDetails.product.imgs.get(0));
        ((NextShopperApplication)ctx.getApplicationContext()).loadBitmaps(orderItemDetails.product.imgs.get(0), imageView, false, 0);
        TextView nameView = (TextView)convertView.findViewById(R.id.order_item_name);
        nameView.setText(orderItemDetails.product.name);
        TextView quView = (TextView) convertView.findViewById(R.id.order_item_quantity);
        TextView priceView =(TextView) convertView.findViewById(R.id.order_item_price);
        quView.setText(String.format(ctx.getResources().getString(R.string.quantity_num), orderItemDetails.item.quantity));
        priceView.setText(String.format(ctx.getString(R.string.price_usd), orderItemDetails.product.salePrice));
        TextView timeView = (TextView) convertView.findViewById(R.id.item_time);
        timeView.setText(new Date(orderItemDetails.item.created).toString());
        TextView statusView =(TextView) convertView.findViewById(R.id.item_status);
        statusView.setText(orderItemDetails.order.status.toString());
        TextView raveView = (TextView) convertView.findViewById(R.id.item_rave_it);
        raveView.setTag(orderItemDetails);
        raveView.setOnClickListener(this);
        TextView contactSellerView = (TextView) convertView.findViewById(R.id.item_contact_seller);
        contactSellerView.setTag(orderItemDetails);
        contactSellerView.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount()) {
            ApiService.getService().OrderAPI_UserOrderItemList(start, numOfItem, new Callback<OrderItemList>() {
                @Override
                public void success(OrderItemList list, Response response) {
                    if(list.total==0 && start==0){
                        ctx.getFragmentManager().beginTransaction().replace(R.id.order_history_container, new EmptyOrderFragment()).commit();
                    }else {
                        OrderHistoryAdapter.this.orderItemDetailsList.addAll(list.items);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(Constant.NEXTSHOPPER, error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);

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
        OrderDetailsActivity.startActivity(ctx, getItem(position).order.id);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.item_rave_it){
            Intent intent = new Intent(ctx, ReviewOrderActivity.class);
            ctx.startActivity(intent);

        }else if(v.getId() == R.id.item_contact_seller){
            Intent intent = new Intent(ctx, ContactSellerActivity.class);
            ctx.startActivity(intent);
        }
    }
}
