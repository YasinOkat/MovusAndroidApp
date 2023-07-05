package com.example.movusandroidapp.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IApiService {

    @POST("/login")
    Call<LoginResponse> login(@Body LoginBody loginBody);

    @GET("/getCars")
    Call<List<GetCarsResponse>> getCars();

    @POST("/takeCar")
    Call<TakeCarResponse> takeCar(@Body TakeCarBody takeCarBody);

    @GET("/getUsedCars")
    Call<List<GetUsedCarsResponse>> getUsedCars();

    @POST("/returnCar")
    Call<ReturnCarResponse> returnCar(@Body ReturnCarBody returnCarBody);

}
