package com.example.csquestionapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HighScoresActivity extends AppCompatActivity {

    ListView listView;
    Button btnRestart , btnClean;
    SharedPreferences sp;
    BackgroundColor backgroundColor;
    ArrayList<Player> playerArrayList = new ArrayList<Player>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // Get id's:
        btnRestart =findViewById(R.id.btnBack_highScores);
        btnClean =findViewById(R.id.btnClean_highScores);
        backgroundColor = findViewById(R.id.bgBackground);
        listView = findViewById(R.id.lvHighScores);

        // Get the shared prefs:
        sp = getApplicationContext().getSharedPreferences("Settings" , Context.MODE_PRIVATE);

        ActionBar actionBar = getSupportActionBar(); // set ActionBar language too
        actionBar.setTitle(getResources().getString(R.string.app_name)); // setTitle won't be null! (app_name was given a name)

        // Set Background Color:
        backgroundColor.setColor(MainActivity.currentBackgroundColor);

        // Get HighScores from Shared Prefs:
        if(sp.contains("PlayerList")) { // If PlayerList is in Shared Prefs:

            // Get PlayerList from Shared Prefs:
            Gson gson = new Gson();
            String json = sp.getString("PlayerList" , null);
            Type type = new TypeToken<ArrayList<Player>>() {}.getType();
            playerArrayList = gson.fromJson(json , type);

        } else { // PlayerList not exist - create a new one:
            playerArrayList = new ArrayList<Player>(); // new PlayerList
        }

        PlayerListAdapter arrayAdapter = new PlayerListAdapter(HighScoresActivity.this , R.layout.adapter_view_layout , playerArrayList);
        listView.setAdapter(arrayAdapter);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(HighScoresActivity.this , R.anim.blink_anim);
                btnRestart.startAnimation(animation);
                Intent in2 = new Intent(getApplicationContext(), com.example.csquestionapp.MainActivity.class);
                startActivity(in2);
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(HighScoresActivity.this , R.anim.fadein);
                btnClean.startAnimation(animation);

                // Reset All High Scores:
                sp = getApplicationContext().getSharedPreferences("Settings" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit(); // Set editor for edit

                // Save PlayerList
                playerArrayList = new ArrayList<Player>(); // new PlayerList
                Gson gson = new Gson();
                String json = gson.toJson(playerArrayList);
                editor.putString("PlayerList" , json);
                editor.apply();

                PlayerListAdapter arrayAdapter = new PlayerListAdapter(HighScoresActivity.this , R.layout.adapter_view_layout , playerArrayList);
                listView.setAdapter(arrayAdapter);
            }
        });
    }
}