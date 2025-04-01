package com.ruler_hao.taipei_recycler.domain.repository;

import com.ruler_hao.taipei_recycler.data.entity.TruckData;

public interface TruckRepository {
    TruckData getTruckData(int offset, int limit);
}