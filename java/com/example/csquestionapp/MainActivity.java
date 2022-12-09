package com.example.csquestionapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    Button startButton, aboutButton, changeLanguageButton, highScoresButton;
    SharedPreferences sp;
    static public BackgroundColor currentBackgroundColor;
    static public MediaPlayer mediaPlayer;
    String language = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set pre - Settings before loading the layout(load settings before showing the screen to user - for background and language):

        // Set shared preferences:
        sp = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        // Check Shared Preferences before uploading UI:
        if (sp.contains("Language")) { // If this isn't the first time entering the app(a Language chosen already):
            language = sp.getString("Language", "en"); // Get it
        }

        // Set Language
        setLocale(language);
        ActionBar actionBar = getSupportActionBar(); // set ActionBar language too
        actionBar.setTitle(getResources().getString(R.string.app_name)); // setTitle won't be null! (app_name was given a name)

        // End of pre - Settings , Show screen:
        setContentView(R.layout.activity_main);

        // Set background:
        currentBackgroundColor = (BackgroundColor)findViewById(R.id.bgBackground);
        if(currentBackgroundColor.getColor1() == Color.parseColor("#ffffff")) { // Background is white (background isn't changed -> First time entering the app):
            currentBackgroundColor.applyRandomColor();
        }

        //Get id's:
        startButton = findViewById(R.id.btnStart);
        aboutButton = findViewById(R.id.btnAbout);
        changeLanguageButton = findViewById(R.id.btnLanguage);
        highScoresButton = findViewById(R.id.btnHighScores);
        final EditText nameText = findViewById(R.id.editName);

        // Set Music
        if(mediaPlayer == null) { // First time entering the app:
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.appmusic);
            mediaPlayer.setLooping(true);
        }
        mediaPlayer.start();

        // Click Listeners:
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this , R.anim.lefttoright);
                startButton.startAnimation(animation);
                String name = nameText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                intent.putExtra("myName", name);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this , R.anim.lefttoright);
                aboutButton.startAnimation(animation);
                Intent intent = new Intent(getApplicationContext(), DeveloperActivity.class);
                startActivity(intent);
            }
        });

        changeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this , R.anim.bounce);
                changeLanguageButton.startAnimation(animation);
                // Array of languages to display in alert dialog:
                final String[] langList = {"English", "עברית"};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("Choose Language...");
                mBuilder.setSingleChoiceItems(langList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            // English
                            setLocale("en");
                            recreate();
                        }
                        if (i == 1) {
                            // Hebrew
                            setLocale("he");
                            recreate();
                        }
                        // Dismiss alert dialog when language is selected:
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                // Show alert dialog
                mDialog.show();
            }

        });

        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this , R.anim.lefttoright);
                highScoresButton.startAnimation(animation);
                Intent intent = new Intent(getApplicationContext(), HighScoresActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        // Save data to shared preferences:
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("Language", lang);
        editor.apply();
    }
}