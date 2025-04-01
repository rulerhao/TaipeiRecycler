package com.ruler_hao.taipei_recycler.data.impl;

import com.ruler_hao.taipei_recycler.data.api.ApiRequest;
import com.ruler_hao.taipei_recycler.data.entity.TruckData;
import com.ruler_hao.taipei_recycler.domain.repository.TruckRepository;

import java.util.HashMap;
import java.util.Map;

public class TruckRepositoryImpl implements TruckRepository {

    private static final String API_URL = "https://data.taipei/api/v1/dataset/a6e90031-7ec4-4089-afb5-361a4efe7202" +
            "?scope=resourceAquire&resource_id=a6e90031-7ec4-4089-afb5-361a4efe7202";

    private final ApiRequest apiRequest;

    public TruckRepositoryImpl(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }

    @Override
    public TruckData getTruckData(int offset, int limit) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("offset", String.valueOf(offset));
        parameters.put("limit", String.valueOf(limit));

        return apiRequest.performNetworkRequest(API_URL, parameters, TruckData.class);
    }
}
