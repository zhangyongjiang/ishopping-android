package com.nextshopper.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nextshopper.activity.R;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.CartItemDetails;
import com.nextshopper.rest.beans.CartItemDetailsList;
import com.nextshopper.rest.beans.CartItemRequest;
import com.nextshopper.rest.beans.CartItemRequestList;
import com.nextshopper.rest.beans.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends SwipeFragment {
    private ListView listView;
    private Item subtotalView;
    private Item shippingView;
    private Item totalView;
    private Item creditView;
    private Item couponView;
    private Item netPayView;
   // List<Product> productList;
    private static final String ARG_PARAM1 = "param";
    private CartItemDetailsList cartItemDetailsList;
    private View footerView;
    private OrderAdapter adapter;

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
        super.onCreateView(inflater, container, savedInstanceState);
        //View view = inflater.inflate(R.layout.fragment_cart_list, container, false);
        footerView = inflater.inflate(R.layout.fragment_cart_footer, null, false);
        listView = (ListView) view.findViewById(R.id.listview);
        listView.addFooterView(footerView);
        //Map<String, Product> productMap = ((NextShopperApplication) getActivity().getApplicationContext()).getProductMap();
        //productList = new ArrayList<Product>(productMap.values());
        adapter = new OrderAdapter(this, this.getArguments().getBoolean(ARG_PARAM1));
        listView.setAdapter(adapter);
        subtotalView = (Item) footerView.findViewById(R.id.order_subtotal);
        shippingView = (Item) footerView.findViewById(R.id.order_shipping);
        totalView = (Item) footerView.findViewById(R.id.order_total);
        creditView = (Item) footerView.findViewById(R.id.order_credit);
        couponView = (Item) footerView.findViewById(R.id.order_coupon);
        netPayView = (Item) footerView.findViewById(R.id.order_netpay);
        serviceCall(adapter);

        //CartItemRequestList cartItemRequestList = convert(productList);
       // final ProgressDialog progressDialog= Util.getProgressDialog(getActivity());
        /*
        ApiService.getService().ShoppingAPI_AddCartItems(cartItemRequestList, new Callback<CartItemDetailsList>() {
            @Override
            public void success(CartItemDetailsList cartItemDetailsList, Response response) {
                progressDialog.dismiss();
                shipping = cartItemDetailsList.shipping;
            }

            @Override
            public void failure(RetrofitError error) {
               progressDialog.dismiss();
                Util.alertBox(CartListFragment.this.getActivity(), error);
            }
        });*/

        return view;
    }

    void serviceCall(final OrderAdapter orderAdapter){
        ApiService.getService().ShoppingAPI_List(null, new Callback<CartItemDetailsList>() {
            @Override
            public void success(CartItemDetailsList cartItemDetailsList, Response response) {
                CartListFragment.this.cartItemDetailsList= cartItemDetailsList;
                orderAdapter.updateList(cartItemDetailsList.items);
                updateShipping(cartItemDetailsList);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

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

    public void updateShipping(CartItemDetailsList list){
        float subtotal=0;
        for(CartItemDetails cartItemDetails: list.items){
            subtotal+=cartItemDetails.item.quantity*cartItemDetails.item.price;
        }
        float credit=list.userCredit;
        float total = subtotal+ list.shipping;
        subtotalView.setRight(String.format(getResources().getString(R.string.price),subtotal));
        shippingView.setRight(String.format(getResources().getString(R.string.price), (double)list.shipping));
        totalView.setRight(String.format(getResources().getString(R.string.price),total));
        creditView.setRight(String.format(getResources().getString(R.string.price),credit));
        netPayView.setRight(String.format(getResources().getString(R.string.price), total-credit));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_view;
    }

    @Override
    protected void refresh() {
        serviceCall(adapter);
    }
}
