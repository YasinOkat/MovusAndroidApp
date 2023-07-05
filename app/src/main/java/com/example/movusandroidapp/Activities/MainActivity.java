package com.example.movusandroidapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.movusandroidapp.Api.MainViewModel;
import com.example.movusandroidapp.R;
import com.example.movusandroidapp.Utils.ButtonAnimation;
import com.example.movusandroidapp.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String username;
    private static final String PREF_USERNAME = "pref_username";
    private static final String PREF_PASSWORD = "pref_password";
    private static final String PREF_REMEMBER_ME = "pref_remember_me";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String savedUsername = sharedPreferences.getString(PREF_USERNAME, "");
        String savedPassword = sharedPreferences.getString(PREF_PASSWORD, "");
        binding.etUsername.setText(savedUsername);
        binding.etPassword.setText(savedPassword);
        boolean rememberMeChecked = sharedPreferences.getBoolean(PREF_REMEMBER_ME, false);
        binding.cbRememberMe.setChecked(rememberMeChecked);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvSuccess.setText(s);

                if (s.equals("Giriş Başarılı")) {
                    openNewActivity();
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonAnimation.animateButton(binding.btnLogin);

                username = Objects.requireNonNull(binding.etUsername.getText()).toString().trim();
                String password = Objects.requireNonNull(binding.etPassword.getText()).toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Lütfen kullanıcı adı ve şifre giriniz", Toast.LENGTH_SHORT).show();
                } else {
                    mViewModel.login(username, password);

                    if (binding.cbRememberMe.isChecked()) {
                        saveCredentialsToPreferences(username, password);
                    } else {
                        clearCredentialsFromPreferences();
                    }
                }
            }
        });
    }

    private void openNewActivity() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void saveCredentialsToPreferences(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USERNAME, username);
        editor.putString(PREF_PASSWORD, password);
        editor.putBoolean(PREF_REMEMBER_ME, true);
        editor.apply();
    }

    private void clearCredentialsFromPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREF_USERNAME);
        editor.remove(PREF_PASSWORD);
        editor.remove(PREF_REMEMBER_ME);
        editor.apply();
    }

}