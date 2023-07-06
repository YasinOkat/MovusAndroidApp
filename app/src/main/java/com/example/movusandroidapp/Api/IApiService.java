package com.example.movusandroidapp.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @POST("/updateLocation")
    Call<UpdateLocationResponse> updateLocation(@Body UpdateLocationBody updateLocationBody);

    @POST("/getLocation")
    Call<List<GetLocationResponse>> getLocation(@Body GetLocationBody getLocationBody);



}
