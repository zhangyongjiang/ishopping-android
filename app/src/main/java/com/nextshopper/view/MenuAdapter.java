package com.nextshopper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nextshopper.activity.R;

/**
 * Created by siyiliu on 7/20/15.
 */
public class MenuAdapter extends BaseAdapter {
    private String menuItems[];
    private Context context;

    public MenuAdapter(String[] menuItems, Context context){
        this.menuItems = menuItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return menuItems.length;
    }

    @Override
    public String getItem(int position) {
        return menuItems[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(menuItems[position]);
        int drawableId=0;
        switch (menuItems[position]){
            case "Shop": drawableId = R.drawable.menu_shop; break;
            case "Search": drawableId = R.drawable.menu_search;break;
            case "Shopping Cart": drawableId = R.drawable.menu_cart;break;
            case "Settings": drawableId = R.drawable.menu_setting;break;
            case "Message":drawableId=R.drawable.menu_message;break;
            case "Order History": drawableId=R.drawable.menu_order;break;
            default :drawableId=R.drawable.store_about;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(drawableId, null),null,null,null);
        return textView;
        }
}
