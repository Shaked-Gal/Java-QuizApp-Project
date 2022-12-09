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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ResultActivity extends AppCompatActivity {
    TextView tv, tv2, tv3;
    Button btnRestart;
    SharedPreferences sp;
    BackgroundColor backgroundColor;
    int correctAns = QuestionsActivity.correct;
    int wrongAns = QuestionsActivity.wrong;
    String name;
    ArrayList<Player> playerList = new ArrayList<Player>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get id's:
        tv = findViewById(R.id.tvRes);
        tv2 = findViewById(R.id.tvRes2);
        tv3 = findViewById(R.id.tvRes3);
        btnRestart = findViewById(R.id.btnRestart);
        backgroundColor = findViewById(R.id.bgBackground);

        // Get player name:
        Intent intent = getIntent();
        name = intent.getStringExtra("myName");

        // Set Shared Preference:
        sp = getApplicationContext().getSharedPreferences("Settings" , Context.MODE_PRIVATE);

        if(sp.contains("PlayerList")) { // If PlayerList is in Shared Prefs:

            // Get PlayerList from Shared Prefs:
            Gson gson = new Gson();
            String json = sp.getString("PlayerList" , null);
            Type type = new TypeToken<ArrayList<Player>>() {}.getType();
            playerList = gson.fromJson(json , type);

        } else { // PlayerList not exist - create a new one:
            playerList = new ArrayList<Player>(); // new PlayerList
        }

        playerList.add(new Player(name , correctAns));

        // Sort - Descending:
        Collections.sort(playerList , new Comparator<Player>() {
            public int compare(Player p1 , Player p2) {
                return Integer.valueOf(p2.getScore()).compareTo(Integer.valueOf(p1.getScore()));
            }
        });

        // Save PlayerList
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(playerList);
        editor.putString("PlayerList" , json);
        editor.apply();

        // Show results on text views:
        StringBuffer sb = new StringBuffer();
        sb.append(getResources().getString(R.string.correctAns)).append(correctAns).append("\n");
        StringBuffer sb2 = new StringBuffer();
        sb2.append(getResources().getString(R.string.wrongAns)).append(wrongAns).append("\n");
        StringBuffer sb3 = new StringBuffer();
        sb3.append(getResources().getString(R.string.finalScore)).append(correctAns).append("\n");

        tv.setText(sb);
        tv2.setText(sb2);
        tv3.setText(sb3);

        // Reset data for next time:
        QuestionsActivity.correct=0;
        QuestionsActivity.wrong=0;

        // Get the randomized background color from shared prefs:
        ActionBar actionBar = getSupportActionBar(); // set ActionBar language too
        actionBar.setTitle(getResources().getString(R.string.app_name)); // setTitle won't be null! (app_name was given a name)

        // Set Background Color:
        backgroundColor.setColor(MainActivity.currentBackgroundColor);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ResultActivity.this , R.anim.blink_anim);
                btnRestart.startAnimation(animation);
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
            }
        });
    }

}
