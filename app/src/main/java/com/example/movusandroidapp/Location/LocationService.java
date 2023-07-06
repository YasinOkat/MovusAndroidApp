package com.example.movusandroidapp.Location;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.movusandroidapp.Activities.TakeCarActivity;
import com.example.movusandroidapp.Api.MainViewModel;
import com.example.movusandroidapp.Constants;
import com.example.movusandroidapp.R;
import com.example.movusandroidapp.Repository.MainRepository;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class LocationService extends Service implements MainRepository.IResponseCallback {

    public double latitude;
    public double longitude;
    private String usedCar;

    MainViewModel mViewModel;

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(locationResult != null && locationResult.getLastLocation() != null){
                latitude = locationResult.getLastLocation().getLatitude();
                longitude = locationResult.getLastLocation().getLongitude();
                Log.d("LOCATION_UPDATE", latitude + ", " + longitude);
                processLocationUpdates();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @SuppressLint("MissingPermission")
    private void startLocationService() {

        String channelId = "location_notification_channel";
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                channelId)
                ;
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Location Service");
        builder.setContentText("Running");
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setOngoing(true);

        Notification notification = builder.build();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null
                    && notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId,
                        "Location Service",
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.setDescription("This channel is used by location service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(20000)
                .setMaxUpdateDelayMillis(1000)
                .setIntervalMillis(10000)
                .build();

        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        startForeground(Constants.LOCATION_SERVICE_ID, notification);
    }


    private void stopLocationService() {
        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopSelf();
    }

    @Override
    public void onResponse(String message) {

    }

    @Override
    public void onFailure(String error) {

    }

    public class MyBinder extends Binder {
        LocationService getService() {
            return LocationService.this;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Constants.ACTION_START_LOCATION_SERVICE)) {
                    usedCar = intent.getStringExtra("used_car");

                    String viewModelIdentifier = intent.getStringExtra("viewModelIdentifier");

                    if (viewModelIdentifier.equals(MainViewModel.class.getName())) {
                        mViewModel = new MainViewModel();
                    }

                    Log.d("LocationService", "Used Car: " + usedCar);
                    Log.d("LocationService", "Latitude: " + getLatitude());
                    startLocationService();
                    mViewModel.updateLocation(longitude, latitude, usedCar, LocationService.this);
                } else if (action.equals(Constants.ACTION_STOP_LOCATION_SERVICE)) {
                    stopLocationService();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void processLocationUpdates() {
        if (usedCar != null && mViewModel != null) {
            Log.d("LocationService", "Used Car: " + usedCar);
            Log.d("LocationService", "Latitude: " + latitude);

            mViewModel.updateLocation(longitude, latitude, usedCar, LocationService.this);
        }
    }

}
