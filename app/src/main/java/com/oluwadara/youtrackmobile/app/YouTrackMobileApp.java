package com.oluwadara.youtrackmobile.app;

import android.app.Application;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;



public class YouTrackMobileApp extends Application {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDataBase;

    @Override
    public void onCreate() {
        super.onCreate();

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public FirebaseDatabase getmDataBase() {
        return mDataBase;
    }
}
