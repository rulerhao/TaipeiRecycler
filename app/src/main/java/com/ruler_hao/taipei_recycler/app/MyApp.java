package com.ruler_hao.taipei_recycler.app;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import com.ruler_hao.taipei_recycler.data.api.ApiRequest;
import com.ruler_hao.taipei_recycler.data.impl.TruckRepositoryImpl;
import com.ruler_hao.taipei_recycler.domain.use_case.TruckUseCase;

public class MyApp extends Application {
    public static ApiRequest apiRequest;
    public static TruckUseCase truckUseCase;
    public static LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        apiRequest = ApiRequest.getInstance();
        truckUseCase = TruckUseCase.getInstance(new TruckRepositoryImpl(apiRequest));
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
}
