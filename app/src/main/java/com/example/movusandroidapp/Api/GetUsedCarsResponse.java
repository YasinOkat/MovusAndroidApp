package com.example.movusandroidapp.Api;

import com.google.gson.annotations.SerializedName;

public class GetUsedCarsResponse {

    @SerializedName("UsedPlate")
    private String plate;
    @SerializedName("Username")
    private String username;
    @SerializedName("CarTakenTime")
    private String carTakenTime;
    @SerializedName("Destination")
    private String destination;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCarTakenTime() {
        return carTakenTime;
    }

    public void setCarTakenTime(String carTakenTime) {
        this.carTakenTime = carTakenTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
