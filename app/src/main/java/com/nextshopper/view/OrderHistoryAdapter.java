package com.nextshopper.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nextshopper.activity.ContactSellerActivity;
import com.nextshopper.activity.OrderDetailsActivity;
import com.nextshopper.activity.R;
import com.nextshopper.activity.ReviewOrderActivity;
import com.nextshopper.common.Constant;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.OrderItemDetails;
import com.nextshopper.rest.beans.OrderItemList;

import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 9/6/15.
 */
public class OrderHistoryAdapter extends NextShopperAdapter<OrderItemDetails> implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private Activity ctx;
    private ListView listView;
    private ProgressDialog progressDialog = null;

    public OrderHistoryAdapter(Activity ctx, ListView listView) {
        this.ctx = ctx;
        this.listView = listView;
        this.listView.setOnItemClickListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OrderItemDetails orderItemDetails = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.order_history_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_img);
        imageView.setTag(orderItemDetails.product.imgs.get(0));
        ((NextShopperApplication) ctx.getApplicationContext()).loadBitmaps(orderItemDetails.product.imgs.get(0), imageView, false, 0);
        TextView nameView = (TextView) convertView.findViewById(R.id.order_item_name);
        nameView.setText(orderItemDetails.product.name);
        TextView quView = (TextView) convertView.findViewById(R.id.order_item_quantity);
        TextView priceView = (TextView) convertView.findViewById(R.id.order_item_price);
        quView.setText(String.format(ctx.getResources().getString(R.string.quantity_num), orderItemDetails.item.quantity));
        priceView.setText(String.format(ctx.getString(R.string.price_usd), orderItemDetails.product.salePrice));
        TextView timeView = (TextView) convertView.findViewById(R.id.item_time);
        timeView.setText(new Date(orderItemDetails.item.created).toString());
        TextView statusView = (TextView) convertView.findViewById(R.id.item_status);
        statusView.setText(orderItemDetails.order.status.toString());
        TextView raveView = (TextView) convertView.findViewById(R.id.item_rave_it);
        raveView.setTag(orderItemDetails);
        raveView.setTag(orderItemDetails.product.id);
        raveView.setOnClickListener(this);
        TextView contactSellerView = (TextView) convertView.findViewById(R.id.item_contact_seller);
        contactSellerView.setTag(orderItemDetails);
        contactSellerView.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount() && call) {
            call = false;
            if (lastVisibleItem != 0)
                progressDialog = Util.getProgressDialog(ctx);
            ApiService.getService().OrderAPI_UserOrderItemList(start, numOfItem, new Callback<OrderItemList>() {
                @Override
                public void success(OrderItemList list, Response response) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    if (list.items.size() > 0) {
                        OrderHistoryAdapter.this.list.addAll(list.items);
                        notifyDataSetChanged();
                    }
                    if(list.items.size()<numOfItem)
                        call = false;
                    else
                        call =true;

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
        OrderDetailsActivity.startActivity(ctx, getItem(position).order.id);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_rave_it) {
            Intent intent = new Intent(ctx, ReviewOrderActivity.class);
            intent.putExtra(Constant.PRODUCT_ID,(String)v.getTag());
            ctx.startActivity(intent);

        } else if (v.getId() == R.id.item_contact_seller) {
            ContactSellerActivity.startActivity(this.ctx, "Seller");
        }
    }
}
