package com.example.movusandroidapp.Api;

import com.google.gson.annotations.SerializedName;

public class GetCarsResponse {
    @SerializedName("Plate")
    private String plate;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
