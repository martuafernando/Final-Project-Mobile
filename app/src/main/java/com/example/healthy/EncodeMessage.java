package com.example.healthy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.healthy.databinding.ActivityDaftarPesanBinding;
import com.example.healthy.databinding.ActivityEncodeMessageBinding;
import com.example.healthy.databinding.ActivityGetPesanBinding;

import java.io.IOException;
import java.util.List;

public class EncodeMessage extends AppCompatActivity {
    private ActivityEncodeMessageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEncodeMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.encodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EncodeMessage.this, EncodeResult.class));
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(intent);
            }
        });

        binding.autoGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Geocoder g = new Geocoder(getBaseContext());
                    List<Address> daftar = g.getFromLocation(getCoordinateLocation().getLatitude(), getCoordinateLocation().getLongitude(), 1);
                    binding.locationEditText.setText(String.valueOf(daftar.get(0).getAddressLine(0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data != null && data.getExtras().get("data") != null) {
                            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                            binding.addImageButton.setImageBitmap(capturedImage);

                        }
                    }
                }
            }
    );

    @SuppressLint("MissingPermission")
    public Location getCoordinateLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS == null) lastKnownLocationGPS =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            return lastKnownLocationGPS;
        } else {
            return null;
        }
    }
}