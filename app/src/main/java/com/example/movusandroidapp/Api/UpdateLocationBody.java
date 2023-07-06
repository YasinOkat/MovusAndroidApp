package com.example.movusandroidapp.Api;

import com.google.gson.annotations.SerializedName;

public class UpdateLocationBody {
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("used_car")
    private String used_car;

    public UpdateLocationBody(double longitude, double latitude, String used_car) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.used_car = used_car;
    }

}
