package com.example.movusandroidapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movusandroidapp.Utils.ButtonAnimation;
import com.example.movusandroidapp.databinding.ActivityMenuBinding;


public class MenuActivity extends AppCompatActivity {

    private String username;
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        retrieveIntentExtras();

        binding.btnTakeCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonAnimation.animateButton(binding.btnTakeCar);
                Intent intent = new Intent(MenuActivity.this, TakeCarActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        binding.btnReturnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonAnimation.animateButton(binding.btnReturnCar);
                Intent intent = new Intent(MenuActivity.this, ReturnCarActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        binding.btnCarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonAnimation.animateButton(binding.btnCarList);
                Intent intent = new Intent(MenuActivity.this, CarListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void retrieveIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
        }
    }
}
