package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MojBrojResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moj_broj_results);
        Bundle extras = getIntent().getExtras();
        int hostScore = extras.getInt("hostScore");
        int guestScore = extras.getInt("guestScore");
        String solution = extras.getString("solution");

        TextView hostPoints = findViewById(R.id.hostPoints);
        TextView guestPoints = findViewById(R.id.guestPoints);
        TextView closerSolution = findViewById(R.id.closerSolution);
        Button finish = findViewById(R.id.finishGamesBtn);

        hostPoints.setText(String.valueOf(hostScore));
        guestPoints.setText(String.valueOf(guestScore));
        closerSolution.setText(solution);

    }
}