package com.oluwadara.youtrackmobile.app.data.model;


import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class NetworkClient {

    public static final String TAG = NetworkClient.class.getName();
    private static final String API_URL = "https://us1.unwiredlabs.com";

    public static String Token;

    private static TrackerRequest trackerRequest;


    static {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.e(TAG, "log: " + message );
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        // Create an instance of our RiteTag API interface.
        trackerRequest = retrofit.create(TrackerRequest.class);
    }

    public static Call<LocationResponse>  getLocation(LocationRequest request) throws IOException {
         return trackerRequest.getLocation(request);
    }
    public interface TrackerRequest {
        @POST("/v2/process.php")
        Call<LocationResponse> getLocation(@Body LocationRequest body, Callback<LocationResponse> callback);

        @POST("/v2/process.php")
        Call<LocationResponse> getLocation(@Body LocationRequest body);

    }

    public static class LocationRequest {

        private String token;
        private String radio;
        private int mnc;
        private int mcc;
        private List<Cell> cells;
        private int address;

        public LocationRequest(String token, String radio, int mnc, int mcc, List<Cell> cells) {
            this.token = token;
            this.radio = radio;
            this.mnc = mnc;
            this.mcc = mcc;
            this.cells = cells;
        }

        public LocationRequest(String token, String radio, int mnc, int mcc, List<Cell> cells, int address) {
            this.token = token;
            this.radio = radio;
            this.mnc = mnc;
            this.mcc = mcc;
            this.cells = cells;
            this.address = address;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public int getMnc() {
            return mnc;
        }

        public void setMnc(int mnc) {
            this.mnc = mnc;
        }

        public int getMcc() {
            return mcc;
        }

        public void setMcc(int mcc) {
            this.mcc = mcc;
        }

        public List<Cell> getCells() {
            return cells;
        }

        public void setCells(List<Cell> cells) {
            this.cells = cells;
        }
    }

    public static class Cell {
        private int lac;
        private int cid;

        public Cell(int lac, int cid) {
            this.lac = lac;
            this.cid = cid;
        }

        public int getLac() {
            return lac;
        }

        public void setLac(int lac) {
            this.lac = lac;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }
    }

    public static class LocationResponse {
        private String status;
        private long balance;
        private double lat;
        private double lon;
        private long accuracy;
        private String address;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getBalance() {
            return balance;
        }

        public void setBalance(long balance) {
            this.balance = balance;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public long getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(long accuracy) {
            this.accuracy = accuracy;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
