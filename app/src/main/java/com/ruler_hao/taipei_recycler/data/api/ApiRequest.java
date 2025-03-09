package com.ruler_hao.taipei_recycler.data.api;

import static com.ruler_hao.taipei_recycler.data.entity.StationData.parseJsonToTruckData;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ruler_hao.taipei_recycler.data.entity.TruckData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiRequest {
    private static final String API_URL = "https://data.taipei/api/v1/dataset/a6e90031-7ec4-4089-afb5-361a4efe7202?scope=resourceAquire&resource_id=a6e90031-7ec4-4089-afb5-361a4efe7202";
    private static ApiRequest instance;
    private final Context context;

    private ApiRequest(Context context) {
        this.context = context.getApplicationContext(); // 使用 ApplicationContext
    }

    public static ApiRequest getInstance(Context context) {
        if (instance == null) {
            instance = new ApiRequest(context);
        }
        return instance;
    }

    public void fetchApiData() {
        // 使用單執行緒池，這樣可以按需創建新的線程並處理
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TruckData truckData = performNetworkRequest();
                // 使用 Handler 來更新 UI 线程
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // 顯示 API 回應結果
                        if (truckData != null) {
                            Toast.makeText(context, "API Response: " + truckData.getCount(), Toast.LENGTH_LONG).show();
                            Log.d("API Response", truckData.toString());
                        } else {
                            Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private TruckData performNetworkRequest() {
        StringBuilder response = new StringBuilder();
        HttpURLConnection urlConnection = null;

        try {
            // 創建 URL 物件
            URL url = new URL(API_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(15000);  // 設置超時時間
            urlConnection.setReadTimeout(15000);

            // 檢查返回的 HTTP 狀態碼
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {  // 200 OK
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 解析 JSON 並返回 TruckData
                return parseJsonToTruckData(response.toString());
            } else {
                return null; // 返回 null，代表請求失敗
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
