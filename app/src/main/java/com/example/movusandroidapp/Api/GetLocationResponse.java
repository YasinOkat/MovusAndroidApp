package com.example.movusandroidapp.Api;

import com.google.gson.annotations.SerializedName;

public class GetLocationResponse {

    @SerializedName("Latitude")
    private String latitude;
    @SerializedName("Longitude")
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    public void setLongitude(String longitude){this.longitude = longitude;}

}
