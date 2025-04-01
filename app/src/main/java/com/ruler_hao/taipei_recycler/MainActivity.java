package com.ruler_hao.taipei_recycler;

import static com.ruler_hao.taipei_recycler.utils.DrawableUtils.getBitmapFromVectorDrawable;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ruler_hao.taipei_recycler.app.MyApp;
import com.ruler_hao.taipei_recycler.data.entity.StationData;
import com.ruler_hao.taipei_recycler.domain.use_case.TruckUseCase;
import com.ruler_hao.taipei_recycler.presentation.view.map.MapPageFragment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//
//    TruckUseCase truckUseCase = MyApp.truckUseCase;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(() -> {
//            List<StationData> result = truckUseCase.execute();
//            new Handler(Looper.getMainLooper()).post(() -> {
//                Log.d("Network Result", result != null ? result.toString() : "Request failed");
//            });
//        });
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(this);
//        }
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // 設定地圖初始位置 (例如台北 101)
//        LatLng taipei101 = new LatLng(25.0330, 121.5654);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taipei101, 15));
//
////        addMarker(new LatLng(25.0330, 121.5654), "Test Title", "Snippet");
//
////        BitmapDescriptor textBitmap = createTextMarker("台北 101");
////
////        mMap.addGroundOverlay(new GroundOverlayOptions()
////                .image(new BitmapDescriptor()) // 以文字作為圖片
////                .position(taipei101, 200f, 100f));  // 設定位置與大小
//
////        Bitmap bitmap = getBitmapFromVectorDrawable(this, R.drawable.ic_launcher_foreground);
////
////        GroundOverlayOptions overlayOptions = new GroundOverlayOptions()
////                .image(BitmapDescriptorFactory.fromBitmap(bitmap)) // 替換成你的圖片
////                .position(taipei101, 1000f, 1000f) // 設定圖片尺寸（500x500 公尺）
////                .transparency(0f); // 設定透明度（0 = 不透明, 1 = 完全透明）
////        GroundOverlay groundOverlay = mMap.addGroundOverlay(overlayOptions);
////        mMap.setOnMapClickListener(latLng -> {
////            Log.d(MainActivity.this.getClass().getName(), "latLng = " + latLng);
////            if (groundOverlay != null && isPointInsideOverlay(latLng, groundOverlay)) {
////                Toast.makeText(MainActivity.this, "GroundOverlay 被點擊！", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        mMap.setOnMarkerClickListener(marker -> {
//            // 在這裡處理 Marker 點擊事件
//            // 顯示標記的標題
//            Toast.makeText(MainActivity.this, "Marker clicked: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
//            // 返回 false，這樣地圖不會因為點擊而移動
//            return false;
//        });
//
//        Bitmap truckBitmap = getBitmapFromVectorDrawable(this, R.drawable.trash_truck);
//        mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(25.0330, 121.5654)) // 設定座標
//                .title("垃圾車位置")
//                .icon(BitmapDescriptorFactory.fromBitmap(truckBitmap))
//        );
//
//        // 繪製多邊形 (Polygon)
////        drawPolygon();
//
//        // 繪製折線 (Polyline)
////        drawPolyline();
//
//        // 繪製圓形 (Circle)
////        drawCircle();
//    }
//
//    private void drawPolygon() {
//        PolygonOptions polygonOptions = new PolygonOptions()
//                .add(new LatLng(25.0330, 121.5654),
//                        new LatLng(25.0335, 121.5660),
//                        new LatLng(25.0340, 121.5650),
//                        new LatLng(25.0335, 121.5645))
//                .strokeColor(Color.RED)
//                .fillColor(0x7FFF0000); // 半透明紅色
//        mMap.addPolygon(polygonOptions);
//    }
//
//    private void drawPolyline() {
//        PolylineOptions polylineOptions = new PolylineOptions()
//                .add(new LatLng(25.0330, 121.5654),
//                        new LatLng(25.0335, 121.5660),
//                        new LatLng(25.0340, 121.5650))
//                .color(Color.BLUE)
//                .width(10);
//        mMap.addPolyline(polylineOptions);
//    }
//
//    private void drawCircle() {
//        CircleOptions circleOptions = new CircleOptions()
//                .center(new LatLng(25.0330, 121.5654))
//                .radius(200) // 200 公尺
//                .strokeColor(Color.GREEN)
//                .fillColor(0x5500FF00); // 半透明綠色
//        mMap.addCircle(circleOptions);
//    }
//
////    private void test() {
////        MarkerOptions
////        mMap.addMarker()
////    }
//
//    private void addMarker(LatLng position, String title, String snippet) {
//        Marker marker = mMap.addMarker(new MarkerOptions()
//                .position(position)
//                .title(title)  // 簡單的字串顯示
////                .snippet(snippet) // 額外資訊，點擊時顯示
////                .visible(true)
//        );
//        assert marker != null;
//        marker.showInfoWindow();
//    }
//
//    private boolean isPointInsideOverlay(LatLng point, GroundOverlay overlay) {
//        LatLng center = overlay.getPosition();
//        float width = overlay.getWidth() / 2;
//        float height = overlay.getHeight() / 2;
//
//        // 計算 GroundOverlay 的邊界
//        double latMin = center.latitude - height / 111000; // 1度 ≈ 111公里
//        double latMax = center.latitude + height / 111000;
//        double lngMin = center.longitude - width / 111000;
//        double lngMax = center.longitude + width / 111000;
//
//        Log.d(MainActivity.this.getClass().getName(), "latMin = " + latMin);
//        Log.d(MainActivity.this.getClass().getName(), "latMax = " + latMax);
//        Log.d(MainActivity.this.getClass().getName(), "lngMin = " + lngMin);
//        Log.d(MainActivity.this.getClass().getName(), "lngMax = " + lngMax);
//
//        // 檢查點是否在範圍內
//        return (point.latitude >= latMin && point.latitude <= latMax &&
//                point.longitude >= lngMin && point.longitude <= lngMax);
//    }
//}

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadFragment(new MapPageFragment());
        }
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
