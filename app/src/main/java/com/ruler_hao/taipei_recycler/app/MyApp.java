package com.ruler_hao.taipei_recycler.app;

import android.app.Application;

import com.ruler_hao.taipei_recycler.data.api.ApiRequest;
import com.ruler_hao.taipei_recycler.data.impl.TruckRepositoryImpl;
import com.ruler_hao.taipei_recycler.domain.use_case.TruckUseCase;
import com.ruler_hao.taipei_recycler.presentation.view_model.MapViewModel;

public class MyApp extends Application {
    public static ApiRequest apiRequest;
    public static TruckUseCase truckUseCase;

    @Override
    public void onCreate() {
        super.onCreate();

        apiRequest = ApiRequest.getInstance();
        truckUseCase = TruckUseCase.getInstance(new TruckRepositoryImpl(apiRequest));
    }
}
