package com.nextshopper.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextshopper.activity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyOrderFragment extends Fragment {


    public EmptyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.empty_order_list, container, false);
    }


}
