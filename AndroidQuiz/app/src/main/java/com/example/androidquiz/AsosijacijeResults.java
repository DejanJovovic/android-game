package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class AsosijacijeResults extends AppCompatActivity {

    boolean isHost;
    private String selectedGameName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asosijacije_results);

        isHost = getIntent().getExtras().getBoolean("isHost");
        final AppCompatButton startNewBtn = findViewById(R.id.startNewQuizBtnAsosijacije);
        final TextView totalScore = findViewById(R.id.totalScoreAsosijacije);

        final int getTotalScore = getIntent().getIntExtra("totalScore", 0);

        totalScore.setText(String.valueOf(getTotalScore));


        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedGameName = "Asosijacije";
                Intent intent = new Intent(AsosijacijeResults.this, AsosijacijeActivity2.class);
                intent.putExtra("isHost", isHost);
                intent.putExtra("selectedGame", selectedGameName);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AsosijacijeResults.this, Games.class));
        finish();
    }
}