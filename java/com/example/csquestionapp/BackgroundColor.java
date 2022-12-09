package com.example.csquestionapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class BackgroundColor extends View {
    private static final String[] mColors = {
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7" // light gray
    };
    public int backgroundColor;

    public BackgroundColor(Context context) {
        super(context);
        init();
    }

    public BackgroundColor(Context context , AttributeSet attrs) {
        super(context , attrs);
        init();
    }

    public BackgroundColor(Context context , AttributeSet attrs , int defStyleAttr) {
        super(context , attrs , defStyleAttr);
        init();
    }

    public BackgroundColor(Context context , AttributeSet attrs , int defStyleAttr , int defStyleRes) {
        super(context , attrs , defStyleAttr , defStyleRes);
        init();
    }

    private void init() {
        this.backgroundColor = Color.parseColor("#ffffff"); // White default
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(this.backgroundColor);
    }

    public int getColor1() {
        return this.backgroundColor;
    }

    public void setColor(BackgroundColor newBackgroundColor) {
        this.backgroundColor = newBackgroundColor.getColor1();
    }

    public void applyRandomColor(){
        //Randomly generate a color
        String color;
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mColors.length);
        color = mColors[randomNumber];
        int colorAsInt;
        colorAsInt = Color.parseColor(color);
        this.backgroundColor = colorAsInt;
    }
}
