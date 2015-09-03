package com.nextshopper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.CartItemDetailsList;
import com.nextshopper.rest.beans.Product;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 8/23/15.
 */
public class OrderAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private List<Product> productList;

    public OrderAdapter(Context ctx, List<Product> productList ){
        context = ctx;
        this.productList = productList;
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        }
        Product product = productList.get(position);
       ImageView imageView=  (ImageView)convertView.findViewById(R.id.order_img);
        TextView productNameView = (TextView) convertView.findViewById(R.id.order_product_name);
        TextView productStoreView = (TextView) convertView.findViewById(R.id.order_store_name);
        TextView prodcutPriceView =(TextView) convertView.findViewById(R.id.order_product_price);
        ((NextShopperApplication)context.getApplicationContext()).loadBitmaps(product.imgs.get(0), imageView, false, 0);
        Button quantityButton = (Button) convertView.findViewById(R.id.item_quantity);
        productNameView.setText(product.name);
        productStoreView.setText(String.format(context.getResources().getString(R.string.order_store_name), product.storeName));
        prodcutPriceView.setText(String.format(context.getResources().getString(R.string.order_prodcut_price), product.salePrice));
        quantityButton.setText(product.quantity + "");
        Button minusButton = (Button)convertView.findViewById(R.id.item_minus);
        minusButton.setTag(quantityButton);
        quantityButton.setTag(product);
        Button addButton = (Button) convertView.findViewById(R.id.item_add);
        minusButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        addButton.setTag(quantityButton);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Button quantityButton = (Button)v.getTag();
        Product product = (Product) quantityButton.getTag();
        if(v.getId()==R.id.item_minus){
            if(Integer.parseInt(quantityButton.getText().toString())==1){
                productList.remove(product);
                //quantityButton.setText(String.valueOf(Integer.parseInt(quantityButton.getText().toString())-1));
                ((NextShopperApplication)context.getApplicationContext()).getProductMap().remove(product.id);
               // notifyDataSetChanged();
                ApiService.getService().ShoppingAPI_RemoveCartItem(product.id, new Callback<CartItemDetailsList>() {
                    @Override
                    public void success(CartItemDetailsList cartItemDetailsList, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
            else{
                quantityButton.setText(String.valueOf(Integer.parseInt(quantityButton.getText().toString())-1));
                Map<String, Product> map =  ((NextShopperApplication)context.getApplicationContext()).getProductMap();
                map.get(product.id).quantity =  map.get(product.id).quantity-1;
                map.put(product.id, product);
                //notifyDataSetChanged();
                ApiService.getService().ShoppingAPI_UpdateItemQuantity(product.id, map.get(product.id).quantity, new Callback<CartItemDetailsList>() {
                    @Override
                    public void success(CartItemDetailsList cartItemDetailsList, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        } else if(v.getId()==R.id.item_add){

            quantityButton.setText(String.valueOf(Integer.parseInt(quantityButton.getText().toString())+1));
            Map<String, Product> map =  ((NextShopperApplication)context.getApplicationContext()).getProductMap();
            map.get(product.id).quantity =  map.get(product.id).quantity+1;
            map.put(product.id, product);
           // notifyDataSetChanged();
            ApiService.getService().ShoppingAPI_UpdateItemQuantity(product.id, map.get(product.id).quantity, new Callback<CartItemDetailsList>() {
                @Override
                public void success(CartItemDetailsList cartItemDetailsList, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        notifyDataSetChanged();
    }
}
