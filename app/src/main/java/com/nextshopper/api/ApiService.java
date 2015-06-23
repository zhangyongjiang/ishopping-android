package com.nextshopper.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by siyiliu on 6/20/15.
 */
public class ApiService {
    private static String endpoint = "https://api.nextshopper.com";
    private static RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(endpoint).setRequestInterceptor(new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept","application/json");
        }
    }).build();

    private static UserService service;
    public static UserService getUserService(){
        if(service == null) {
            service = restAdapter.create(UserService.class);
        }
        return service;
    }
}
