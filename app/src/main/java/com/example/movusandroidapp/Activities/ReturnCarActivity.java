package com.example.movusandroidapp.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.movusandroidapp.Api.GetUsedCarsResponse;
import com.example.movusandroidapp.Api.MainViewModel;
import com.example.movusandroidapp.Constants;
import com.example.movusandroidapp.Location.LocationService;
import com.example.movusandroidapp.R;
import com.example.movusandroidapp.Repository.MainRepository;
import com.example.movusandroidapp.Utils.ButtonAnimation;
import com.example.movusandroidapp.databinding.ActivityReturnCarBinding;

import java.util.ArrayList;
import java.util.List;

public class ReturnCarActivity extends AppCompatActivity implements MainRepository.IResponseCallback{

    private ActivityReturnCarBinding binding;
    private Spinner spinnerCars;
    private ArrayAdapter<String> spinnerAdapter;
    private String username;
    private String kilometer;
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReturnCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spinnerCars = binding.spnUsedCars;
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCars.setAdapter(spinnerAdapter);

        binding.tbReturnCar.setTitle("Araba Teslim Et");
        setSupportActionBar(binding.tbReturnCar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.tbReturnCar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (binding != null && binding.tbReturnCar.getNavigationIcon() != null) {
            binding.tbReturnCar.getNavigationIcon().setTint(ContextCompat.getColor(this, R.color.white));
        }


        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getUsedCarsList().observe(this, new Observer<List<GetUsedCarsResponse>>() {
            @Override
            public void onChanged(List<GetUsedCarsResponse> getUsedCarsResponse) {
                List<String> carList = new ArrayList<>();
                carList.add(0, "Araba seçiniz");
                for (GetUsedCarsResponse car : getUsedCarsResponse) {
                    carList.add(car.getPlate());
                }

                spinnerAdapter.clear();
                spinnerAdapter.addAll(carList);
                spinnerAdapter.notifyDataSetChanged();
            }
        });

        mViewModel.getUsedCars();

        binding.btnReturnTheCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonAnimation.animateButton(binding.btnReturnTheCar);
                binding.btnReturnTheCar.setEnabled(false);

                String selectedCar = (String) spinnerCars.getSelectedItem();
                kilometer = String.valueOf(binding.etKilometer.getText());

                username = getIntent().getExtras().getString("username");
                Log.e("username", username);

                mViewModel.getCars();

                boolean carExists = false;
                for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                    if (spinnerAdapter.getItem(i).equals(selectedCar)) {
                        carExists = true;
                        break;
                    }
                }


                if (!carExists) {
                    Toast.makeText(ReturnCarActivity.this, "Arabayı senden önce başka biri almış", Toast.LENGTH_SHORT).show();
                    binding.btnReturnTheCar.setEnabled(true);
                }
                else if (kilometer.isEmpty() || selectedCar.equals("Araba seçiniz")) {
                    Toast.makeText(ReturnCarActivity.this, "Lütfen araba seçiniz ve kilometre giriniz", Toast.LENGTH_SHORT).show();
                    binding.btnReturnTheCar.setEnabled(true);
                } else {
                    mViewModel.returnCar(selectedCar, kilometer, ReturnCarActivity.this);
                    showDialogAndFinish("Araba teslim edildi.");
                    stopLocationService();
                    binding.btnReturnTheCar.setEnabled(true);
                }
            }
        });
    }

    private void showDialogAndFinish(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Tamam", (dialogInterface, i) -> finish())
                .show();
    }

    @Override
    public void onResponse(String message) {

    }

    @Override
    public void onFailure(String error) {

    }

    private boolean isLocationServiceRunning(){
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null){
            for(ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(LocationService.class.getName().equals(service.service.getClassName())){
                    if(service.foreground){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void stopLocationService(){
        if(isLocationServiceRunning()){
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this,"Location service stopped", Toast.LENGTH_SHORT).show();
        }
    }
}