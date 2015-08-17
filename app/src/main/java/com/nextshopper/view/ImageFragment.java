package com.nextshopper.view;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nextshopper.activity.R;
import com.nextshopper.rest.BitmapWorkerTask;

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
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        return view;
    }

}
