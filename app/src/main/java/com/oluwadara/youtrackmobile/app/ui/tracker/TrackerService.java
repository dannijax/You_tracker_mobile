package com.oluwadara.youtrackmobile.app.ui.tracker;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.oluwadara.youtrackmobile.app.YouTrackMobileApp;
import com.oluwadara.youtrackmobile.app.data.model.CellInfo;
import com.oluwadara.youtrackmobile.app.data.model.Location;
import com.oluwadara.youtrackmobile.app.data.model.NetworkClient;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrackerService extends Service {

    public static final String MNC = "mnc";
    public static final String MCC = "mcc";
    public static final String CID = "cid";
    public static final String LAC = "lac";
    public static final String NOTIFICATION = TrackerService.class.getName();

    FirebaseAuth firebaseAuth;

    FirebaseDatabase database;

    private int mnc;
    private int mcc;
    private int cid;
    private int lac;

    private final IBinder mBinder = new LocalBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(NOTIFICATION, "onStartCommand: " );
        return super.onStartCommand(intent, flags, startId);
    }

    private void setUp() {
        if (firebaseAuth == null && database == null){
            firebaseAuth = ((YouTrackMobileApp) getApplication()).getmAuth();
            database = ((YouTrackMobileApp) getApplication()).getmDataBase();
        }
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        PhoneTracker tracker = new PhoneTracker(getBaseContext());
        mnc = tracker.getMnc();
        mcc = tracker.getMcc();
        cid = tracker.getCellId();
        lac = tracker.getLac();
        List<NetworkClient.Cell> cells = new ArrayList<>();
        cells.add(new NetworkClient.Cell(lac, cid));
        NetworkClient.LocationRequest locationRequest =
                new NetworkClient.LocationRequest("94d73db8ab7bb8",
                        "gsm", mnc, mcc, cells, 1 );
        try {
            NetworkClient.getLocation(locationRequest).enqueue(new Callback<NetworkClient.LocationResponse>() {
                @Override
                public void onResponse(Call<NetworkClient.LocationResponse> call, Response<NetworkClient.LocationResponse> response) {
                    NetworkClient.LocationResponse response1 = response.body();
                    Location location = new Location();
                    location.setLongitude(response1.getLon());
                    location.setLatitude(response1.getLat());
                    location.setDateTime(getDateAsString());
                    if (user != null){
                        database.getReference("users")
                                .child(user.getUid())
                                .child("location")
                                .child(location.getId())
                                .setValue(location);
                    }

                }

                @Override
                public void onFailure(Call<NetworkClient.LocationResponse> call, Throwable t) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        CellInfo cellInfo = new CellInfo(mnc, mcc, lac, cid);
        String date = getDateAsString();

        cellInfo.setDate(date);

        if (user != null){
            String id = user.getUid();
            database.getReference("users")
                    .child(id)
                    .child("cellinfo")
                    .child(cellInfo.getId())
                    .setValue(cellInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    private String getDateAsString() {
        String dateTime = DateTime.now().toString("MM/dd/yyyy HH:mm:ss");
// Format for input
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
// Parsing the date
        DateTime jodatime = dtf.parseDateTime(dateTime);
// Format for output
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd/yyyy");
        return dtfOut.print(jodatime);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        setUp();
        intent.putExtra(MNC, mnc);
        intent.putExtra(MCC, mcc);
        intent.putExtra(CID, cid);
        intent.putExtra(LAC, lac);
        Log.e(NOTIFICATION, "onBind: " );
        sendBroadcast(intent);
        return mBinder;
    }

    class LocalBinder extends Binder {
        TrackerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return TrackerService.this;
        }
    }
}
