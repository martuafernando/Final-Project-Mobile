package com.example.healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.healthy.databinding.ActivityDaftarPesanBinding;
import com.example.healthy.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DaftarPesan extends AppCompatActivity {

    private ActivityDaftarPesanBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDaftarPesanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(binding.gambar1);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API.daftarPesan)
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                System.out.println("testing::" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                System.out.println("testing::" + myResponse);
                try {
                    JSONObject reader = new JSONObject(myResponse);
                    System.out.println("data::" + reader.getString("imageFile"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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

        binding.pesan2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarPesan.this, GetPesan.class));
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}