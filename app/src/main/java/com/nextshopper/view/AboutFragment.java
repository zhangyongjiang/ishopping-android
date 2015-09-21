package com.nextshopper.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextshopper.activity.AboutActivity;
import com.nextshopper.activity.ContactSellerActivity;
import com.nextshopper.activity.R;
import com.nextshopper.common.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment implements View.OnClickListener{

   private SettingItem usView;
    private SettingItem faqView;
    private SettingItem supportView;
    private SettingItem termsView;
    private SettingItem privacyView;
    private SettingItem rateView;
    private static String TITLE="title";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        usView = (SettingItem) view.findViewById(R.id.about_us);
        faqView =(SettingItem) view.findViewById(R.id.about_fqa);
        supportView=(SettingItem)view.findViewById(R.id.about_support);
        termsView =(SettingItem)view.findViewById(R.id.about_tos);
        privacyView = (SettingItem) view.findViewById(R.id.about_pp);
        rateView =(SettingItem)view.findViewById(R.id.about_rate_this_app);
        usView.setOnClickListener(this);
        faqView.setOnClickListener(this);
        supportView.setOnClickListener(this);
        termsView.setOnClickListener(this);
        privacyView.setOnClickListener(this);
        rateView.setOnClickListener(this);
       return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.about_us){
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            intent.putExtra(TITLE, "About Us");
            startActivity(intent);
        } else if(v.getId()==R.id.about_fqa){
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            intent.putExtra(TITLE,"FAQ");
            startActivity(intent);
        } else if(v.getId()==R.id.about_tos){
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            intent.putExtra(TITLE,"Terms of Service");
            startActivity(intent);
        }else if(v.getId()==R.id.about_pp){
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            intent.putExtra(TITLE,"Privacy Policy");
            startActivity(intent);
        }else if(v.getId()==R.id.about_support){
            ContactSellerActivity.startActivity(getActivity(), Constant.NEXTSHOPPER);
        }
    }
}
