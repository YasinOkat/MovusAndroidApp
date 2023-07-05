package com.example.movusandroidapp.Api;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movusandroidapp.Repository.MainRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<String> mDrinksMutableData = new MutableLiveData<>();
    MutableLiveData<String> mLoginResultMutableData = new MutableLiveData<>();
    MutableLiveData<List<GetCarsResponse>> mCarsListMutableData;

    MutableLiveData<List<GetUsedCarsResponse>> mUsedCarsListMutableData;

    MainRepository mMainRepository;

    public MainViewModel() {
        mProgressMutableData.postValue(View.INVISIBLE);
        mDrinksMutableData.postValue("");
        mLoginResultMutableData.postValue("Giriş yapılmadı");
        mMainRepository = new MainRepository();
        mCarsListMutableData = new MutableLiveData<>();
        mUsedCarsListMutableData = new MutableLiveData<>();
    }
    public LiveData<String> getLoginResult(){
        return mLoginResultMutableData;
    }

    public LiveData<Integer> getProgress(){
        return mProgressMutableData;
    }
    public LiveData<List<GetCarsResponse>> getCarsList() {
        return mCarsListMutableData;
    }

    public LiveData<List<GetUsedCarsResponse>> getUsedCarsList(){
        return mUsedCarsListMutableData;
    }

    public void login(String username, String password) {
        mProgressMutableData.postValue(View.VISIBLE);
        mLoginResultMutableData.postValue("Yükleniyor");
        mMainRepository.loginRemote(new LoginBody(username, password), new MainRepository.ILoginResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                boolean success = loginResponse.isSuccess();
                if (success) {
                    mLoginResultMutableData.postValue("Giriş Başarılı");
                } else {
                    mLoginResultMutableData.postValue("Hatalı kullanıcı adı veya parola");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mLoginResultMutableData.postValue("Giriş başarısız: " + t.getLocalizedMessage());
            }
        });
    }

    public void getCars() {
        mProgressMutableData.postValue(View.VISIBLE);
        mMainRepository.getCars(new MainRepository.IGetCarsResponse() {
            @Override
            public void onResponse(List<GetCarsResponse> getCarsResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mCarsListMutableData.postValue(getCarsResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
            }
        });
    }

    public void getUsedCars() {
        mProgressMutableData.postValue(View.VISIBLE);
        mMainRepository.getUsedCars(new MainRepository.IGetUsedCarsResponse() {
            @Override
            public void onResponse(List<GetUsedCarsResponse> getUsedCarsResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mUsedCarsListMutableData.postValue(getUsedCarsResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
            }
        });
    }

    public void takeCar(String plate, String username, String destination, String purpose, double longitude, double latitude, MainRepository.IResponseCallback callback) {
        mProgressMutableData.postValue(View.VISIBLE);

        TakeCarBody takeCarBody = new TakeCarBody(plate, username, destination, purpose, longitude, latitude);
        mMainRepository.takeCar(takeCarBody, new MainRepository.ITakeCarResponse() {
            @Override
            public void onResponse(TakeCarResponse takeCarResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                if (takeCarResponse.isSuccess()) {
                    callback.onResponse("Araba başarıyla alındı");
                } else {
                    callback.onFailure("Bir hata oluştu");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
                callback.onFailure("Bir hata oluştu: " + t.getLocalizedMessage());
            }
        });
    }

    public void returnCar(String plate, String kilometer, MainRepository.IResponseCallback callback) {
        mProgressMutableData.postValue(View.VISIBLE);

        ReturnCarBody returnCarBody = new ReturnCarBody(plate, kilometer);
        mMainRepository.returnCar(returnCarBody, new MainRepository.IReturnCarResponse() {
            @Override
            public void onResponse(ReturnCarResponse returnCarResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                if (returnCarResponse.isSuccess()) {
                    callback.onResponse("Araba başarıyla teslim edildi");
                } else {
                    callback.onFailure("Bir hata oluştu");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
                callback.onFailure("Bir hata oluştu: " + t.getLocalizedMessage());
            }
        });
    }



}
