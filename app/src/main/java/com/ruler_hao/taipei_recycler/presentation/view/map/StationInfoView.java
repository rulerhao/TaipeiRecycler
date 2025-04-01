package com.ruler_hao.taipei_recycler.presentation.view.map;

import android.util.Log;
import android.widget.LinearLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.ruler_hao.taipei_recycler.R;
import com.ruler_hao.taipei_recycler.data.entity.StationData;

public class StationInfoView extends LinearLayout {

    private TextView textLocation, textVehicleNumber, textArrivalTime;

    public StationInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StationInfoView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_station_info, this, true);

        textLocation = findViewById(R.id.text_location);
        textVehicleNumber = findViewById(R.id.text_vehicle_number);
        textArrivalTime = findViewById(R.id.text_arrival_time);

        setVisibility(GONE);
    }

    public void showStationInfo(StationData stationData) {
        if (stationData != null) {
            Log.d("Test", "showStationInfo = " + stationData.toString());
            textLocation.setText("位置：" + stationData.getLocation());
            textVehicleNumber.setText("車輛編號：" + stationData.getVehicleNumber());
            textArrivalTime.setText("到達時間：" + stationData.getArrivalTime());

            setVisibility(VISIBLE);
        }
    }

    public void hide() {
        setVisibility(GONE);
    }
}