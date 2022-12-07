package com.example.healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class Questioner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questioner);
        getSupportActionBar().hide();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.radio_pirates:
//                if (checked)
//                    // Pirates are the best
//                    break;
//            case R.id.radio_ninjas:
//                if (checked)
//                    // Ninjas rule
//                    break;
//        }
    }
}