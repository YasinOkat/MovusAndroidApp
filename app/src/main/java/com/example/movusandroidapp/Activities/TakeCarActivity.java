package com.example.movusandroidapp.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.movusandroidapp.Api.GetCarsResponse;
import com.example.movusandroidapp.Api.MainViewModel;
import com.example.movusandroidapp.R;
import com.example.movusandroidapp.Repository.MainRepository;
import com.example.movusandroidapp.Utils.ButtonAnimation;
import com.example.movusandroidapp.databinding.ActivityTakeCarBinding;

import java.util.ArrayList;
import java.util.List;

public class TakeCarActivity extends AppCompatActivity implements MainRepository.IResponseCallback {

    private ActivityTakeCarBinding binding;
    private Spinner spinnerCars;
    private ArrayAdapter<String> spinnerAdapter;
    private String username;
    private String destination;
    private String purpose;
    private Double longitude;
    private Double latitude;
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTakeCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spinnerCars = binding.spnAvailableCars;
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCars.setAdapter(spinnerAdapter);

        binding.tbTakeCar.setTitle("Araba Al");
        setSupportActionBar(binding.tbTakeCar);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.tbTakeCar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (binding != null && binding.tbTakeCar.getNavigationIcon() != null) {
            binding.tbTakeCar.getNavigationIcon().setTint(ContextCompat.getColor(this, R.color.white));
        }



        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getCarsList().observe(this, new Observer<List<GetCarsResponse>>() {
            @Override
            public void onChanged(List<GetCarsResponse> getCarsResponse) {
                List<String> carList = new ArrayList<>();
                carList.add(0, "Araba seçiniz");
                for (GetCarsResponse car : getCarsResponse) {
                    carList.add(car.getPlate());
                }

                spinnerAdapter.clear();
                spinnerAdapter.addAll(carList);
                spinnerAdapter.notifyDataSetChanged();
            }
        });

        mViewModel.getCars();

        binding.btnTakeTheCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonAnimation.animateButton(binding.btnTakeTheCar);
                binding.btnTakeTheCar.setEnabled(false);

                String selectedCar = spinnerCars.getSelectedItem().toString();
                username = getIntent().getExtras().getString("username");

                destination = String.valueOf(binding.etDestination.getText());
                purpose = String.valueOf(binding.etPurpose.getText());
                longitude = 36.0;
                latitude = 35.0;

                mViewModel.getCars();
                boolean carExists = false;
                for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                    if (spinnerAdapter.getItem(i).equals(selectedCar)) {
                        carExists = true;
                        break;
                    }
                }

                if (!carExists) {
                    Toast.makeText(TakeCarActivity.this, "Arabayı senden önce başka biri almış", Toast.LENGTH_SHORT).show();
                    binding.btnTakeTheCar.setEnabled(true);
                }
                else if (purpose.isEmpty() || destination.isEmpty() || selectedCar.equals("Araba seçiniz")) {
                    Toast.makeText(TakeCarActivity.this, "Lütfen hedef ve amaç giriniz", Toast.LENGTH_SHORT).show();
                    binding.btnTakeTheCar.setEnabled(true);
                }
                else {
                    mViewModel.takeCar(selectedCar, username, destination, purpose, longitude, latitude, TakeCarActivity.this);
                    showDialogAndFinish("Çıkış yapıldı.");
                    binding.btnTakeTheCar.setEnabled(true);
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
}
