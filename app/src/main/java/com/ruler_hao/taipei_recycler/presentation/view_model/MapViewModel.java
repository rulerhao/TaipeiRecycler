package com.ruler_hao.taipei_recycler.presentation.view_model;

import com.ruler_hao.taipei_recycler.app.MyApp;
import com.ruler_hao.taipei_recycler.data.entity.StationData;
import com.ruler_hao.taipei_recycler.domain.use_case.TruckUseCase;
import com.ruler_hao.taipei_recycler.presentation.view.map.MapViewModelCallback;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapViewModel {
    private final TruckUseCase truckUseCase = MyApp.truckUseCase;
    private static MapViewModel instance;

    private MapViewModelCallback callback;

    public List<StationData> truckData;

    private MapViewModel() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            fetchTruckData();
            callback.onDataFetched();
        });
    }

    public static MapViewModel getInstance() {
        if(instance == null) {
            instance = new MapViewModel();
        }

        return instance;
    }

    public void setCallback(MapViewModelCallback callback) {
        this.callback = callback;
    }

    public void fetchTruckData() {
        truckData = truckUseCase.execute();
    }
}
