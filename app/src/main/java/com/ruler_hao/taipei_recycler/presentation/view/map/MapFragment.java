package com.ruler_hao.taipei_recycler.presentation.view.map;

import static com.ruler_hao.taipei_recycler.utils.DrawableUtils.getBitmapFromVectorDrawable;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.ruler_hao.taipei_recycler.R;
import com.ruler_hao.taipei_recycler.app.MyApp;
import com.ruler_hao.taipei_recycler.data.entity.StationData;
import com.ruler_hao.taipei_recycler.presentation.view_model.MapViewModel;
import com.ruler_hao.taipei_recycler.utils.LocationUtils;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final MapViewModel viewModel = MapViewModel.getInstance();

    private StationInfoView stationInfoView;
    private final MapViewModelCallback viewModelCallback = new MapViewModelCallback() {
        @Override
        public void onDataFetched() {
            init();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stationInfoView = view.findViewById(R.id.station_info_view);
        viewModel.setCallback(viewModelCallback);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map, mapFragment)
                    .commit();
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        init();
    }

    private void init() {
        initMap();
        setMarker(mMap);
    }

    private void initMap() {
        if (mMap == null) return;

        requireActivity().runOnUiThread(() -> {
            Location location = LocationUtils.getLocation(MyApp.locationManager);
            if (location == null) {
                return;
            }
            setPosition(location.getLatitude(), location.getLongitude());
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    stationInfoView.hide();
                }
            });
        });
    }

    private void setMarker(GoogleMap map) {
        if (map == null) return;
        if (viewModel.truckData == null) return;

        requireActivity().runOnUiThread(() -> {
            Bitmap truckBitmap = getBitmapFromVectorDrawable(getContext(), R.drawable.trash_truck);
            for (int i = 0; i < viewModel.truckData.size(); i++) {
                LatLng latLng = new LatLng(
                        viewModel.truckData.get(i).getLatitude(),
                        viewModel.truckData.get(i).getLongitude()
                );
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("垃圾車位置")
                        .icon(BitmapDescriptorFactory.fromBitmap(truckBitmap))
                );
                viewModel.markerData.put(marker.hashCode(), viewModel.truckData.get(i));
            }
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    StationData stationData = viewModel.markerData.get(marker.hashCode());
                    if (stationData != null) {
                        stationInfoView.showStationInfo(stationData);
                    }

                    return false;
                }
            });
        });
    }

    public void setPosition(double latitude, double longitude) {
        if (mMap == null) return;

        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}