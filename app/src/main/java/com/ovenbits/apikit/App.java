package com.ovenbits.apikit;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ovenbits.apikit.api.ApiManager;

public class App extends Application {
    private static RequestQueue mRequestQueue;
    private static ApiManager mApiManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);
        mApiManager = new ApiManager(this, mRequestQueue);
    }

    public static ApiManager getApi() {
        return mApiManager;
    }
}
