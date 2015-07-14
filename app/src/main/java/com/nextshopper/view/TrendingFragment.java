package com.nextshopper.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.nextshopper.activity.R;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.TrendProductList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TrendingFragment extends Fragment {
	private ProductGridAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_tab_trending, container,false);
		GridView gridView = (GridView)view.findViewById(R.id.product_grid_view);
		adapter = new ProductGridAdapter(this.getActivity());
		gridView.setAdapter(adapter);

		NextShopperService service = ApiService.getService();
		service.ProductAPI_PopularProducts(null, 0, 20, new Callback<TrendProductList>() {
			@Override
			public void success(TrendProductList trendProductList, Response response) {
				adapter.setSearchableProductList(trendProductList);
			}

			@Override
			public void failure(RetrofitError error) {
				Log.i("HomeActivity", error.getMessage());
			}
		});
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}
}
