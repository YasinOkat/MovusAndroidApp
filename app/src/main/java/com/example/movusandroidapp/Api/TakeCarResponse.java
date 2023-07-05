package com.example.movusandroidapp.Api;

import com.google.gson.annotations.SerializedName;

public class TakeCarResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return message.equalsIgnoreCase("success");
    }
}

