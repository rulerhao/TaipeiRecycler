package com.ruler_hao.taipei_recycler.data.entity.parser;

import com.ruler_hao.taipei_recycler.data.entity.TruckData;

import org.json.JSONException;

public class ApiParser {
    public static <T> T parse(String jsonResponse, Class<T> clazz) throws IllegalArgumentException, JSONException {
        if (clazz == TruckData.class) {
            return clazz.cast(TruckDataParser.parseJsonToTruckData(jsonResponse));
        } else {
            throw new IllegalArgumentException("Unsupported class type: " + clazz.getName());
        }
    }
}
