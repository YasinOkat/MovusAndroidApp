package com.example.movusandroidapp.Repository;

import android.location.Location;

import com.example.movusandroidapp.Api.GetCarsResponse;
import com.example.movusandroidapp.Api.GetUsedCarsResponse;
import com.example.movusandroidapp.Api.IApiService;
import com.example.movusandroidapp.Api.LoginBody;
import com.example.movusandroidapp.Api.LoginResponse;
import com.example.movusandroidapp.Api.RetrofitClientInstance;
import com.example.movusandroidapp.Api.ReturnCarBody;
import com.example.movusandroidapp.Api.ReturnCarResponse;
import com.example.movusandroidapp.Api.TakeCarBody;
import com.example.movusandroidapp.Api.TakeCarResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    public MainRepository(){

    }

    private IApiService mApiService;

    public void loginRemote(LoginBody loginBody, ILoginResponse loginResponse){
        IApiService apiService = RetrofitClientInstance.getInstance().create(IApiService.class);
        Call<LoginResponse> performLogin = apiService.login(loginBody);

        performLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    loginResponse.onResponse(response.body());
                }
                else{
                    loginResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.onFailure(t);
            }
        });
    }

    public void getCars(IGetCarsResponse getCarsResponse){
        IApiService apiService = RetrofitClientInstance.getInstance().create(IApiService.class);
        Call<List<GetCarsResponse>> performGetCars = apiService.getCars();

        performGetCars.enqueue(new Callback<List<GetCarsResponse>>() {
            @Override
            public void onResponse(Call<List<GetCarsResponse>> call, Response<List<GetCarsResponse>> response) {
                if (response.isSuccessful()){
                    getCarsResponse.onResponse(response.body());
                } else {
                    getCarsResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<GetCarsResponse>> call, Throwable t) {
                getCarsResponse.onFailure(t);
            }
        });
    }

    public void getUsedCars(IGetUsedCarsResponse getUsedCarsResponse){
        IApiService apiService = RetrofitClientInstance.getInstance().create(IApiService.class);
        Call<List<GetUsedCarsResponse>> performGetUsedCars = apiService.getUsedCars();

        performGetUsedCars.enqueue(new Callback<List<GetUsedCarsResponse>>() {
            @Override
            public void onResponse(Call<List<GetUsedCarsResponse>> call, Response<List<GetUsedCarsResponse>> response) {
                if (response.isSuccessful()){
                    getUsedCarsResponse.onResponse(response.body());
                } else {
                    getUsedCarsResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<GetUsedCarsResponse>> call, Throwable t) {
                getUsedCarsResponse.onFailure(t);
            }
        });
    }

    public void takeCar(TakeCarBody takeCarBody, ITakeCarResponse takeCarResponse) {
        IApiService apiService = RetrofitClientInstance.getInstance().create(IApiService.class);
        Call<TakeCarResponse> call = apiService.takeCar(takeCarBody);

        call.enqueue(new Callback<TakeCarResponse>() {
            @Override
            public void onResponse(Call<TakeCarResponse> call, Response<TakeCarResponse> response) {
                if (response.isSuccessful()) {
                    takeCarResponse.onResponse(response.body());
                } else {
                    takeCarResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<TakeCarResponse> call, Throwable t) {
                takeCarResponse.onFailure(t);
            }
        });
    }

    public void returnCar(ReturnCarBody returnCarBody, IReturnCarResponse returnCarResponse) {
        IApiService apiService = RetrofitClientInstance.getInstance().create(IApiService.class);
        Call<ReturnCarResponse> call = apiService.returnCar(returnCarBody);

        call.enqueue(new Callback<ReturnCarResponse>() {
            @Override
            public void onResponse(Call<ReturnCarResponse> call, Response<ReturnCarResponse> response) {
                if (response.isSuccessful()) {
                    returnCarResponse.onResponse(response.body());
                } else {
                    returnCarResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ReturnCarResponse> call, Throwable t) {
                returnCarResponse.onFailure(t);
            }
        });
    }



    public interface ITakeCarResponse {
        void onResponse(TakeCarResponse takeCarResponse);

        void onFailure(Throwable throwable);
    }

    public interface IReturnCarResponse {
        void onResponse(ReturnCarResponse returnCarResponse);

        void onFailure(Throwable throwable);
    }


    public interface ILoginResponse{
        void onResponse(LoginResponse loginResponse);
        void onFailure(Throwable throwable);
    }

    public interface IGetCarsResponse {
        void onResponse(List<GetCarsResponse> getCarsResponse);
        void onFailure(Throwable throwable);
    }

    public interface IGetUsedCarsResponse {
        void onResponse(List<GetUsedCarsResponse> getUsedCarsResponse);
        void onFailure(Throwable throwable);
    }
    public interface IResponseCallback {
        void onResponse(String message);
        void onFailure(String error);
    }


}
