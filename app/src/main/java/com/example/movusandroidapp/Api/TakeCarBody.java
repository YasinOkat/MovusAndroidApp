package com.example.movusandroidapp.Api;

import com.google.gson.annotations.SerializedName;

public class TakeCarBody {
    @SerializedName("plate")
    private String plate;
    @SerializedName("username")
    private String username;
    @SerializedName("destination")
    private String destination;
    @SerializedName("purpose")
    private String purpose;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private double latitude;

    public TakeCarBody(String plate, String username, String destination, String purpose, double longitude, double latitude) {
        this.plate = plate;
        this.username = username;
        this.destination = destination;
        this.purpose = purpose;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}

