package com.ruler_hao.taipei_recycler.utils;

import android.location.Location;
import android.location.LocationManager;

import com.ruler_hao.taipei_recycler.app.MyApp;

public class LocationUtils {
    public static Location getLocation(LocationManager locationManager) {
        if (locationManager == null) {
            return null;
        }
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (location == null) {
                return null;
            }
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            return location;
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }
}
