package com.example.movusandroidapp.Api;

import com.google.gson.annotations.SerializedName;

public class GetLocationBody {

    @SerializedName("plate")
    private String plate;

    public GetLocationBody(String plate) {
        this.plate = plate;
    }

}
