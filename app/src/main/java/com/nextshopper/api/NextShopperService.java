package com.nextshopper.api;

import com.nextshopper.bean.RegisterRequest;
import com.nextshopper.bean.User;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by siyiliu on 6/20/15.
 */
public interface NextShopperService {
    @POST("/ws/user/register")
    void register(@Body RegisterRequest request, Callback<User> callback);

    @Multipart
    @POST("/ws/user/upload-image")
    void uploadImage(@Part("file")TypedFile file, Callback<User> callback);

}
