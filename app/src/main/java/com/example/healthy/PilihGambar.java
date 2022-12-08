package com.example.healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthy.databinding.ActivityEncodeMessageBinding;
import com.example.healthy.databinding.ActivityPilihGambarBinding;

public class PilihGambar extends AppCompatActivity {
    private ActivityPilihGambarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_gambar);

        binding = ActivityPilihGambarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }
}