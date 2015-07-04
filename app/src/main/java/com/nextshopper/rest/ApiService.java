package com.nextshopper.rest;

import com.nextshopper.rest.*;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by siyiliu on 6/20/15.
 */
public class ApiService {
    // private static String endpoint = "https://api.nextshopper.com";
    private static String endpoint = "http://api.onsalelocal.com";
    private static RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(endpoint);

    private static com.nextshopper.rest.NextShopperService service;

    public static com.nextshopper.rest.NextShopperService getService() {
        if (service == null) {
            buildService(null);
        }
        return service;
    }

    public static void buildService(final String cookieValue) {
        service = builder.setRequestInterceptor(
                new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        // request.addHeader("Content-Type", "application/json");
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Cookie", cookieValue);
                    }
                }).build().create(com.nextshopper.rest.NextShopperService.class);
    }
}


