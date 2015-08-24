package com.nextshopper.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.beans.Product;

import java.util.List;

/**
 * Created by siyiliu on 8/23/15.
 */
public class OrderAdapter extends BaseAdapter implements NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener{
    private Context context;
    private List<Product> productList;
    private Product product;

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
        product = productList.get(position);
       ImageView imageView=  (ImageView)convertView.findViewById(R.id.order_img);
        TextView productNameView = (TextView) convertView.findViewById(R.id.order_product_name);
        TextView productStoreView = (TextView) convertView.findViewById(R.id.order_store_name);
        TextView prodcutPriceView =(TextView) convertView.findViewById(R.id.order_product_price);
        ((NextShopperApplication)context.getApplicationContext()).loadBitmaps(product.imgs.get(0), imageView, false, 0);
        TextView quantity = (TextView) convertView.findViewById(R.id.item_quantity);
        productNameView.setText(product.name);
        productStoreView.setText(String.format(context.getResources().getString(R.string.order_store_name), product.storeName));
        prodcutPriceView.setText(String.format(context.getResources().getString(R.string.order_prodcut_price), product.salePrice));
        quantity.setText(product.quantity+"");
        return convertView;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if(newVal==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warining");
            builder.setMessage(context.getString(R.string.empty_cart));
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    productList.remove(product);

                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
         product.quantity = newVal;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {

    }
}
