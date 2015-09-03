package com.nextshopper.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nextshopper.activity.HomeActivity;
import com.nextshopper.activity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyCartFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.empty_cart, container, false);
        TextView startShopping = (TextView) view.findViewById(R.id.start_shopping);
        startShopping.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager =  getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, HomeActivity.PlaceholderFragment.newInstance("Shop")).commit();
        fragmentManager.executePendingTransactions();
    }

}
