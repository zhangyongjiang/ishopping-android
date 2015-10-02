package com.nextshopper.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextshopper.activity.R;
import com.nextshopper.activity.ShippingActivity;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.CartItemDetailsList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CartFragment extends SwipeFragment{

    private OnFragmentInteractionListener mListener;
    private TitleView titleView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ApiService.getService().ShoppingAPI_List(null, new Callback<CartItemDetailsList>() {
            @Override
            public void success(CartItemDetailsList cartItemDetailsList, Response response) {
                if (cartItemDetailsList.items.size() > 0) {
                    getFragmentManager().beginTransaction().add(R.id.cart_container, CartListFragment.newInstance(true)).commit();
                } else{
                    getFragmentManager().beginTransaction().add(R.id.cart_container, new EmptyCartFragment()).commit();
                    titleView.getTextRight().setText("");
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       super.onCreateView(inflater, container, savedInstanceState);
       // Map<String, Product> productMap = ((NextShopperApplication)getActivity().getApplication()).getProductMap();
        titleView = (TitleView)view.findViewById(R.id.cart_title);
        refresh();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void rightOnClick(View view){
        Intent intent = new Intent(getActivity(), ShippingActivity.class);
        intent.putExtra("source", "checkout");
        startActivity(intent);
    }

}
