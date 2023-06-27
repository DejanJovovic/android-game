package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class KoZnaZnaResults extends AppCompatActivity {

    private String selectedGameName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ko_zna_zna_results);

        final AppCompatButton startNewBtn = findViewById(R.id.startNewQuizBtn);
        final TextView correctAnswer = findViewById(R.id.correctAnswers);
        final TextView incorrectAnswer = findViewById(R.id.incorrectAnswers);
        final TextView totalScore = findViewById(R.id.totalScore);


        final int getCorrectAnswers = getIntent().getIntExtra("correct",0);
        final int getIncorrectAnswers = getIntent().getIntExtra("incorrect", 0);
        final int getTotalScore = getIntent().getIntExtra("totalScore", 0);


        correctAnswer.setText(String.valueOf(getCorrectAnswers));
        incorrectAnswer.setText(String.valueOf(getIncorrectAnswers));
        totalScore.setText(String.valueOf(getTotalScore));


        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedGameName = "Spojnice";

                Intent intent = new Intent(KoZnaZnaResults.this, Spojnice.class);
                intent.putExtra("selectedGame", selectedGameName);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(KoZnaZnaResults.this, Games.class));
        finish();
    }
}