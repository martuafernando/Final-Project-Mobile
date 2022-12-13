package com.example.healthy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
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
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;

import com.example.healthy.databinding.ActivityEncodeMessageBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EncodeMessage extends AppCompatActivity {
    private ActivityEncodeMessageBinding binding;
    private File captured_image_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEncodeMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.encodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("message", String.valueOf(binding.pesanEditText.getText()))
                        .addFormDataPart("secretKey", String.valueOf(binding.keyEditText.getText()))
                        .addFormDataPart("latitude", binding.locationEditText.getText().toString().equals("") ? String.valueOf(0) : String.valueOf(getLatitudeFromLocationName(String.valueOf(binding.locationEditText.getText()))))
                        .addFormDataPart("langitude", binding.locationEditText.getText().toString().equals("") ? String.valueOf(0) : String.valueOf(getLongitudeFromLocationName(String.valueOf(binding.locationEditText.getText()))))

                        .addFormDataPart("gambar", captured_image_file.getName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"),
                                captured_image_file))
                        .build();

                Request request = new Request.Builder()
                        .url(API.encodeMessage)
                        .method("POST", formBody)
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
                    }
                });
                //                startActivity(new Intent(EncodeMessage.this, EncodeResult.class));
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
                            try {
                            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();

                            capturedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array

                            // save it in your external storage.
                            File dir=  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                            Date d = new Date();
                            CharSequence s  = DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
                            File output=new File(dir, s + ".png");
                            FileOutputStream fo = new FileOutputStream(output);
                            fo.write(byteArray);
                            fo.flush();
                            fo.close();
                            captured_image_file = output;
                            binding.addImageButton.setImageURI(Uri.fromFile(output));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


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

    public double getLatitudeFromLocationName(String location_name){
        try {
            Geocoder g = new Geocoder(getBaseContext());
            List<Address> daftar = g.getFromLocationName(location_name, 1);
            Address alamat = daftar.get(0);

            return alamat.getLatitude();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getLongitudeFromLocationName(String location_name){
        try {
            Geocoder g = new Geocoder(getBaseContext());
            List<Address> daftar = g.getFromLocationName(location_name, 1);
            Address alamat = daftar.get(0);

            return alamat.getLongitude();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}