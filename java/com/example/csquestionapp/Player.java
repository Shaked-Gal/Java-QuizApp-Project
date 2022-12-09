package com.example.csquestionapp;


import com.google.gson.annotations.SerializedName;

public class Player {
    @SerializedName("name")
    private String mName;
    @SerializedName("score")
    private int mScore;

    public Player(String name, int score) {
        mName = name;
        mScore = score;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        this.mScore = score;
    }
}
