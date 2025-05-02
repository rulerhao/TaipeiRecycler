package com.ruler_hao.taipei_recycler.utils;

import android.location.Location;
import android.location.LocationManager;

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

            return location;
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }
}
