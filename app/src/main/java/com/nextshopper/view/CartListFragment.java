package com.nextshopper.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.CartItemDetailsList;
import com.nextshopper.rest.beans.CartItemRequest;
import com.nextshopper.rest.beans.CartItemRequestList;
import com.nextshopper.rest.beans.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends Fragment {
    private ListView listView;
    private Item subtotalView;
    private Item shippingView;
    private Item totalView;
    private Item creditView;
    private Item couponView;
    private Item netPayView;
    List<Product> productList;
    private float shipping;
    private static final String ARG_PARAM1 = "param";

    public CartListFragment() {
        // Required empty public constructor
    }

    public static CartListFragment newInstance(boolean param) {
        CartListFragment fragment = new CartListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);
        listView = (ListView) view.findViewById(R.id.list_of_order);
        Map<String, Product> productMap = ((NextShopperApplication) getActivity().getApplicationContext()).getProductMap();
        productList = new ArrayList<Product>(productMap.values());
        OrderAdapter orderAdapter = new OrderAdapter(getActivity(), productList, this.getArguments().getBoolean(ARG_PARAM1));
        listView.setAdapter(orderAdapter);
        subtotalView = (Item) view.findViewById(R.id.order_subtotal);
        shippingView = (Item) view.findViewById(R.id.order_shipping);
        totalView = (Item) view.findViewById(R.id.order_total);
        creditView = (Item) view.findViewById(R.id.order_credit);
        couponView = (Item) view.findViewById(R.id.order_coupon);
        netPayView = (Item) view.findViewById(R.id.order_netpay);

        CartItemRequestList cartItemRequestList = convert(productList);
        ApiService.getService().ShoppingAPI_AddCartItems(cartItemRequestList, new Callback<CartItemDetailsList>() {
            @Override
            public void success(CartItemDetailsList cartItemDetailsList, Response response) {
                shipping = cartItemDetailsList.shipping;
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        float subtotal=0;
        for(Product p: productList){
            subtotal+=p.quantity*p.salePrice;
        }
        float credit=0;
        float total = subtotal+ shipping;
        subtotalView.setRight(String.format(getResources().getString(R.string.price),subtotal));
        shippingView.setRight(String.format(getResources().getString(R.string.price), shipping));
        totalView.setRight(String.format(getResources().getString(R.string.price),total));
        creditView.setRight(String.format(getResources().getString(R.string.price),credit));
        netPayView.setRight(String.format(getResources().getString(R.string.price), total));
        return view;
    }

    CartItemRequestList convert(List<Product> productList){
        CartItemRequestList list = new CartItemRequestList();
        List<CartItemRequest> requestList = new ArrayList<>();
        list.items = requestList;
        for(Product product : productList){
            CartItemRequest request =  new CartItemRequest();
            request.productId = product.id;
            request.quantity = product.quantity;
            requestList.add(request);
        }
        return list;
    }
}
