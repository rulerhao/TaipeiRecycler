package com.ruler_hao.taipei_recycler.app;

import android.app.Application;

import com.ruler_hao.taipei_recycler.data.api.ApiRequest;

public class MyApp extends Application {
    public static ApiRequest apiRequest;

    @Override
    public void onCreate() {
        super.onCreate();

        apiRequest = ApiRequest.getInstance(this);

    }
}
