package com.example.healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthy.databinding.ActivityDaftarPesanBinding;
import com.example.healthy.databinding.ActivityEncodeMessageBinding;

public class EncodeMessage extends AppCompatActivity {
    private ActivityEncodeMessageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEncodeMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }
}