package com.example.movusandroidapp.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movusandroidapp.Adapters.UsedCarsAdapter;
import com.example.movusandroidapp.Api.GetUsedCarsResponse;
import com.example.movusandroidapp.R;
import com.example.movusandroidapp.Repository.MainRepository;
import com.example.movusandroidapp.databinding.ActivityCarListBinding;

import java.util.ArrayList;
import java.util.List;

public class CarListActivity extends AppCompatActivity implements MainRepository.IGetUsedCarsResponse {
    private ActivityCarListBinding binding;
    private UsedCarsAdapter adapter;
    private List<GetUsedCarsResponse> carsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.rvUsedCars;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.tbCarList.setTitle("Kullanılan Arabalar");
        setSupportActionBar(binding.tbCarList);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        carsList = new ArrayList<>();
        adapter = new UsedCarsAdapter(carsList);
        recyclerView.setAdapter(adapter);

        MainRepository repository = new MainRepository();
        repository.getUsedCars(this);

    }

    @Override
    public void onResponse(List<GetUsedCarsResponse> getUsedCarsResponse) {
        carsList.clear();
        carsList.addAll(getUsedCarsResponse);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Throwable t) {
    }
}