package com.example.csquestionapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class DeveloperActivity extends AppCompatActivity {
    private Button btnRestart;
    BackgroundColor backgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        // Get id's:
        btnRestart = findViewById(R.id.btnBack);
        backgroundColor = findViewById(R.id.bgBackground);

        // Set action bar:
        ActionBar actionBar = getSupportActionBar(); // set ActionBar language too
        actionBar.setTitle(getResources().getString(R.string.app_name)); // setTitle won't be null! (app_name was given a name)

        // Set Background Color:
        backgroundColor.setColor(MainActivity.currentBackgroundColor);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(DeveloperActivity.this , R.anim.blink_anim);
                btnRestart.startAnimation(animation);
                Intent in2 = new Intent(getApplicationContext(), com.example.csquestionapp.MainActivity.class);
                startActivity(in2);
            }
        });
    }


}
