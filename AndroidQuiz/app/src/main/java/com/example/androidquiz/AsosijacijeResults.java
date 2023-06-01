package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AsosijacijeResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asosijacije_results);

        final AppCompatButton startNewBtn = findViewById(R.id.startNewQuizBtnAsosijacije);
        final TextView totalScore = findViewById(R.id.totalScoreAsosijacije);

        final int getTotalScore = getIntent().getIntExtra("totalScore", 0);

        totalScore.setText(String.valueOf(getTotalScore));


        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AsosijacijeResults.this, KorakPoKorakActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AsosijacijeResults.this, Games.class));
        finish();
    }
}