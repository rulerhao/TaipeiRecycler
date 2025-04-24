package com.ruler_hao.taipei_recycler.presentation.view.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ruler_hao.taipei_recycler.R;
import com.ruler_hao.taipei_recycler.app.MyApp;
import com.ruler_hao.taipei_recycler.utils.LocationUtils;

public class MapPageFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private MapFragment mapFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fragmentManager = getChildFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mapFragment = new MapFragment();
        transaction.replace(R.id.map_container, mapFragment);
        transaction.commit();

        // 綁定按鈕
        ImageButton btnGetLocation = view.findViewById(R.id.btn_get_location);
        btnGetLocation.setOnClickListener(v -> checkLocationPermission());
    }

    // 檢查權限
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            setCurrentPosition();
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }

    // 取得當前位置
    private void setCurrentPosition() {
        Location location = LocationUtils.getLocation(MyApp.locationManager);
        if (location == null) {
            Toast.makeText(requireContext(), "無法取得當前位置", Toast.LENGTH_SHORT).show();
            return;
        }
        // 更新地圖位置
        mapFragment.setPosition(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setCurrentPosition();
            } else {
                Toast.makeText(requireContext(), "未取得定位權限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}