package com.example.healthy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.example.healthy.databinding.ActivityEncodeMessageBinding;
import com.example.healthy.databinding.ActivityEncodeResultBinding;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class EncodeResult extends AppCompatActivity {
    private ActivityEncodeResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEncodeResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        String imageFile = getIntent().getStringExtra("imageFile");
        System.out.println("testingResult::" + imageFile);

        Picasso.get().load(API.domain + "/" + imageFile).into(binding.pilihGambarButton);
        System.out.println("testing" + API.domain + "/" + imageFile);

        binding.simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BitmapDrawable draw = (BitmapDrawable) binding.pilihGambarButton.getDrawable();
                    Bitmap capturedImage = draw.getBitmap();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    capturedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array

                    // save it in your external storage.
                    File dir= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "Encoded");
                    String[] imageFileOlah = imageFile.split("/");
                    File output=new File(dir, imageFileOlah[imageFileOlah.length-1]);
                    FileOutputStream fo = new FileOutputStream(output);
                    fo.write(byteArray);
                    fo.flush();
                    fo.close();
                    Toast.makeText(EncodeResult.this, "Gambar Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
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
                        if (data != null && data.getData() != null) {
                            Uri selectedImageUri = data.getData();
                            binding.pilihGambarButton.setImageURI(selectedImageUri);
                        }
                    }
                }
            }
    );
}