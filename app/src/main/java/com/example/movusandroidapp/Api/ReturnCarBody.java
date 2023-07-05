package com.example.movusandroidapp.Api;

import com.google.gson.annotations.SerializedName;

public class ReturnCarBody {
    @SerializedName("plate")
    private String plate;
    @SerializedName("kilometer")
    private String kilometer;

    public ReturnCarBody(String plate, String kilometer) {
        this.plate = plate;
        this.kilometer = kilometer;
    }
}
