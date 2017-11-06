package com.oluwadara.youtrackmobile.app.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.oluwadara.youtrackmobile.app.YouTrackMobileApp;
import com.oluwadara.youtrackmobile.app.ui.adapter.PagerAdapter;
import com.oluwadara.youtrackmobile.app.ui.tracker.HomeActivity;
import com.oluwadara.youtrackmobile.app.youtrackmobileapp.R;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager= findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        mAuth = ((YouTrackMobileApp)getApplication()).getmAuth();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.e("TAG", "onStart: " + currentUser );
        if (currentUser != null){
            startTrackerActivity();
            finish();
        }
    }

    private void startTrackerActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
