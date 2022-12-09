package com.example.csquestionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PlayerListAdapter extends ArrayAdapter<Player> {

    private Context mContext;
    int mResource;

    public PlayerListAdapter(Context context, int resource, ArrayList<Player> objects) {
        super(context, resource , objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        // Get the Player's info:
        String name = getItem(position).getName();
        int score = getItem(position).getScore();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource , parent , false);

        TextView tvName = (TextView) convertView.findViewById(R.id.playerName);
        TextView tvScore = (TextView) convertView.findViewById(R.id.playerScore);

        tvName.setText(name);
        tvScore.setText(String.valueOf(score));

        return convertView;
    }
}
