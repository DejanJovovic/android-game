package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Asosijacije extends AppCompatActivity {


    private Timer quizTimer;
    private int totalTimeInMins = 1;
    private int seconds = 0;

    private final List<QuestionsList> questionsLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asosijacije);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Asosijacije");
    }



    private void StartTimer(TextView timerTextView) {
        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (seconds == 0){
                    totalTimeInMins--;
                    seconds = 59;
                }
                else if(seconds == 0 && totalTimeInMins == 0) {
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(Asosijacije.this, "Time over", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Asosijacije.this, QuizResults.class);
                }

            }
        }, 1000, 1000 );
    }

}