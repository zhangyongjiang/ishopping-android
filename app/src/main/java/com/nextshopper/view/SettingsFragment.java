package com.nextshopper.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextshopper.activity.ChangePasswordActivity;
import com.nextshopper.activity.ProfileActivity;
import com.nextshopper.activity.R;
import com.nextshopper.activity.ShippingActivity;

public class SettingsFragment extends Fragment implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        SettingItem profile = (SettingItem)view.findViewById(R.id.profile);
        SettingItem changePwd = (SettingItem) view.findViewById(R.id.password);
        profile.setOnClickListener(this);
        changePwd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.profile){
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        } else if(v.getId() == R.id.password){
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        } else if(v.getId() == R.id.shipping){
            Intent intent = new Intent(getActivity(), ShippingActivity.class);
            startActivity(intent);
        }
    }
}
