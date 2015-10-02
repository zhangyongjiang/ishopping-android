package com.nextshopper.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.CartItemDetails;
import com.nextshopper.rest.beans.CartItemDetailsList;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 8/23/15.
 */
public class OrderAdapter extends NextShopperAdapter<CartItemDetails> implements View.OnClickListener {
    private Context context;
    private CartListFragment fragment;
    private boolean changable;

    public OrderAdapter(CartListFragment fragment, boolean changable) {
        this.fragment = fragment;
        this.context = fragment.getActivity();
        this.changable = changable;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (changable)
                convertView = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
            else
                convertView = LayoutInflater.from(context).inflate(R.layout.order_item_preview, parent, false);
        }
        CartItemDetails cartItemDetails = list.get(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.order_img);
        TextView productNameView = (TextView) convertView.findViewById(R.id.order_product_name);
        TextView productStoreView = (TextView) convertView.findViewById(R.id.order_store_name);
        TextView prodcutPriceView = (TextView) convertView.findViewById(R.id.order_product_price);
        ((NextShopperApplication) context.getApplicationContext()).loadBitmaps(cartItemDetails.product.imgs.get(0), imageView, false, 0);
        productNameView.setText(cartItemDetails.product.name);
        productStoreView.setText(String.format(context.getResources().getString(R.string.order_store_name), cartItemDetails.storeInfo.name));
        prodcutPriceView.setText(String.format(context.getResources().getString(R.string.order_prodcut_price), cartItemDetails.product.salePrice));
        if (changable) {
            Button quantityButton = (Button) convertView.findViewById(R.id.item_quantity);
            quantityButton.setText(cartItemDetails.item.quantity + "");
            Button minusButton = (Button) convertView.findViewById(R.id.item_minus);
            minusButton.setTag(quantityButton);
            quantityButton.setTag(cartItemDetails);
            Button addButton = (Button) convertView.findViewById(R.id.item_add);
            minusButton.setOnClickListener(this);
            addButton.setOnClickListener(this);
            addButton.setTag(quantityButton);
        } else {
            TextView textView = (TextView) convertView.findViewById(R.id.order_preview_quantity);
            textView.setText("Quantity: " + cartItemDetails.item.quantity);

        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Button quantityButton = (Button) v.getTag();
        CartItemDetails cartItemDetails = (CartItemDetails) quantityButton.getTag();
        final ProgressDialog progressDialog = Util.getProgressDialog(context);
        if (v.getId() == R.id.item_minus) {
            if (Integer.parseInt(quantityButton.getText().toString()) == 1) {
                //quantityButton.setText(String.valueOf(Integer.parseInt(quantityButton.getText().toString())-1));
                // ((NextShopperApplication)context.getApplicationContext()).getProductMap().remove(product.id);
                // notifyDataSetChanged();
                ApiService.getService().ShoppingAPI_RemoveCartItem(cartItemDetails.item.id, new Callback<CartItemDetailsList>() {
                    @Override
                    public void success(CartItemDetailsList cartItemDetailsList, Response response) {
                        progressDialog.dismiss();
                        OrderAdapter.this.list = cartItemDetailsList.items;
                        fragment.updateShipping(cartItemDetailsList);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                    }
                });
            } else {
                quantityButton.setText(String.valueOf(Integer.parseInt(quantityButton.getText().toString()) - 1));
                // Map<String, Product> map =  ((NextShopperApplication)context.getApplicationContext()).getProductMap();
                //map.get(product.id).quantity =  map.get(product.id).quantity-1;
                //map.put(product.id, product);
                //notifyDataSetChanged();
                cartItemDetails.item.quantity--;
                ApiService.getService().ShoppingAPI_UpdateItemQuantity(cartItemDetails.item.id, cartItemDetails.item.quantity, new Callback<CartItemDetailsList>() {
                    @Override
                    public void success(CartItemDetailsList cartItemDetailsList, Response response) {
                        progressDialog.dismiss();
                        OrderAdapter.this.list = cartItemDetailsList.items;
                        fragment.updateShipping(cartItemDetailsList);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                    }
                });
            }
        } else if (v.getId() == R.id.item_add) {

            quantityButton.setText(String.valueOf(Integer.parseInt(quantityButton.getText().toString()) + 1));
            //Map<String, Product> map =  ((NextShopperApplication)context.getApplicationContext()).getProductMap();
            //map.get(product.id).quantity =  map.get(product.id).quantity+1;
            //map.put(product.id, product);
            // notifyDataSetChanged();
            cartItemDetails.item.quantity++;
            ApiService.getService().ShoppingAPI_UpdateItemQuantity(cartItemDetails.item.id, cartItemDetails.item.quantity, new Callback<CartItemDetailsList>() {
                @Override
                public void success(CartItemDetailsList cartItemDetailsList, Response response) {
                    progressDialog.dismiss();
                    OrderAdapter.this.list = cartItemDetailsList.items;
                    fragment.updateShipping(cartItemDetailsList);
                    notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                }
            });
        }
        notifyDataSetChanged();
    }

    public void updateList(List<CartItemDetails> cartItemDetailsList) {
        this.list.clear();
        this.list.addAll(cartItemDetailsList);
        notifyDataSetChanged();
    }
}
