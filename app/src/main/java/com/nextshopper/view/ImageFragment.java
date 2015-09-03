package com.nextshopper.view;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nextshopper.activity.R;
import com.nextshopper.common.NextShopperApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    private ImageView imageView;
    private String url;
    private static String ARG_PARAM;

    public ImageFragment(){}

    public static ImageFragment newInstance(String url) {
        ImageFragment fragment = new ImageFragment();
        fragment.url = url;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = (ImageView)view.findViewById(R.id.details_img);
        imageView.setTag(url);
        ((NextShopperApplication)getActivity().getApplicationContext()).loadBitmaps(url, imageView, false,0);
        return view;
    }

    public Bitmap getBitMap(){
        return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }
}
