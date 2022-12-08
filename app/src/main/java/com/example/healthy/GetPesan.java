package com.example.healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthy.databinding.ActivityGetPesanBinding;

public class GetPesan extends AppCompatActivity {

    private ActivityGetPesanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGetPesanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
    }
}