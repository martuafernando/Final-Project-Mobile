package com.example.healthy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.healthy.databinding.ActivityEncodeMessageBinding;
import com.example.healthy.databinding.ActivityPilihGambarBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PilihGambar extends AppCompatActivity {
    private ActivityPilihGambarBinding binding;
    private File selected_image_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_gambar);

        binding = ActivityPilihGambarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.pilihGambarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator);
                intent.setDataAndType(uri,"*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResultLauncher.launch(intent);
            }
        });

        binding.decodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(API.pilihGambar
                                + "/" + selected_image_file.getName()
                                + "/" + binding.keyEditText.getText().toString()
                                + "/" + binding.penerimaEditText.getText().toString()
                        )
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

                        try {
                            final String myResponse = response.body().string();

                            JSONObject obj = null;
                            obj = new JSONObject(myResponse);
                            System.out.println("testingSource::" + obj);

                            Intent intent = new Intent(PilihGambar.this, GetPesan.class);
                            intent.putExtra("message", obj.getString("message"));
                            intent.putExtra("latitude", obj.getString("x"));
                            intent.putExtra("longitude", obj.getString("y"));
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
                        Uri selectedImageUri = data.getData();
                        selected_image_file = new File(selectedImageUri.getPath());
                        try {
                            System.out.println("testing::" + selected_image_file.getCanonicalFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        binding.pilihGambarButton.setImageURI(selectedImageUri);
                    }
                }
            }
    );
}