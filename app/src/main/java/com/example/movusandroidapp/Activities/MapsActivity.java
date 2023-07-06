package com.example.movusandroidapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.movusandroidapp.Api.GetLocationResponse;
import com.example.movusandroidapp.Api.MainViewModel;
import com.example.movusandroidapp.R;
import com.example.movusandroidapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityMapsBinding binding;
    private String plate;
    private GoogleMap googleMap;
    private String latitude;
    private String longitude;
    MainViewModel mViewModel;

    private Timer locationTimer;
    private TimerTask locationTimerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationTimer = new Timer();


        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        plate = getIntent().getExtras().getString("plate");

        setSupportActionBar(binding.toolbarMap);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbarMap.getNavigationIcon().setTint(ContextCompat.getColor(this, R.color.white));

        binding.toolbarMap.setNavigationOnClickListener(view -> onBackPressed());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Harita");
        }

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        mViewModel.getGetLocationLiveData().observe(this, new Observer<Pair<String, String>>() {
            @Override
            public void onChanged(Pair<String, String> location) {
                latitude = location.first;
                longitude = location.second;
                onMapReady(googleMap);
            }
        });


        locationTimerTask = new TimerTask() {
            @Override
            public void run() {
                // Call the getLocation method
                mViewModel.getLocation(plate);
            }
        };
        locationTimer.schedule(locationTimerTask, 0, 10000); // Delay: 0ms, Repeat: 10,000ms (10 seconds)
    }


    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (latitude != null && longitude != null) {
            LatLng position = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

            MarkerOptions markerOptions = new MarkerOptions().position(position).title(plate);
            googleMap.addMarker(markerOptions);

            float zoomLevel = 15f;
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel));

            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Cancel the timer task
        if (locationTimerTask != null) {
            locationTimerTask.cancel();
        }

        // Stop the timer
        if (locationTimer != null) {
            locationTimer.cancel();
            locationTimer.purge();
        }
    }




}
