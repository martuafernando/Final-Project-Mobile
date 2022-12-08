package com.example.healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        binding.decodePesanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarPesan.this, PilihGambar.class));
            }
        });

        binding.pesan1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarPesan.this, GetPesan.class));
            }
        });
    }
}