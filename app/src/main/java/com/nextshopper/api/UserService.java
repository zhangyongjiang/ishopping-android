package com.nextshopper.api;

import com.nextshopper.bean.RegisterRequest;
import com.nextshopper.bean.User;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by siyiliu on 6/20/15.
 */
public interface UserService {
    @POST("/ws/user/register")
    void register(@Body RegisterRequest request, Callback<User> callback);

}
