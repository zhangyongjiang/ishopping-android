package com.nextshopper.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.nextshopper.activity.ChangePasswordActivity;
import com.nextshopper.activity.FavoriteActivity;
import com.nextshopper.activity.FollowedStoreActivity;
import com.nextshopper.activity.MainActivity;
import com.nextshopper.activity.ProfileActivity;
import com.nextshopper.activity.R;
import com.nextshopper.activity.ShippingActivity;
import com.nextshopper.common.Constant;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.UserSettings;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SettingsFragment extends Fragment implements View.OnClickListener{
    private ToggleButton pushView;
    private SettingItem profileView;
    private SettingItem changePwdView;
    private SettingItem shippingView;
    private SettingItem favoriteView;
    private SettingItem storeView;
    private SettingItem logoutView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        profileView = (SettingItem)view.findViewById(R.id.profile);
        changePwdView = (SettingItem) view.findViewById(R.id.password);
        shippingView =(SettingItem) view.findViewById(R.id.shipping);
        pushView =(ToggleButton)view.findViewById(R.id.setting_push_noti);
        favoriteView = (SettingItem) view.findViewById(R.id.setting_favorite);
        storeView =(SettingItem) view.findViewById(R.id.setting_stores);
        logoutView = (SettingItem) view.findViewById(R.id.setting_logout);
        pushView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserSettings settings = new UserSettings();
                if(isChecked){
                    settings.notificationEnabled= 1;
                }else{
                   settings.notificationEnabled=0;
                }
                ApiService.getService().PushNotificationAPI_UpdatePushNotificationSetting(settings, new Callback<UserSettings>() {
                    @Override
                    public void success(UserSettings userSettings, Response response) {
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Util.alertBox(SettingsFragment.this.getActivity(), error);
                    }
                });
            }
        });
        ApiService.getService().PushNotificationAPI_GetPushNotificationSetting(new Callback<UserSettings>() {
            @Override
            public void success(UserSettings userSettings, Response response) {
                int notificationEnabled = userSettings.notificationEnabled;
                if(notificationEnabled==0){
                    pushView.setChecked(false);
                }else{
                    pushView.setChecked(true);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        profileView.setOnClickListener(this);
        changePwdView.setOnClickListener(this);
        shippingView.setOnClickListener(this);
        favoriteView.setOnClickListener(this);
        storeView.setOnClickListener(this);
        logoutView.setOnClickListener(this);
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
        } else if(v.getId()==R.id.setting_favorite){
            Intent intent = new Intent(getActivity(), FavoriteActivity.class);
            startActivity(intent);
        } else if(v.getId()==R.id.setting_stores){
            Intent intent = new Intent(getActivity(), FollowedStoreActivity.class);
            startActivity(intent);
        } else if(v.getId() == R.id.setting_logout){
            SharedPreferences preferences = getActivity().getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
            File dir = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File thumnail = new File(dir, Constant.THUMNAIL);
            thumnail.delete();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

}
