package com.example.healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthy.databinding.ActivityDaftarPesanBinding;
import com.example.healthy.databinding.ActivityMainBinding;

public class DaftarPesan extends AppCompatActivity {

    private ActivityDaftarPesanBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDaftarPesanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }
}