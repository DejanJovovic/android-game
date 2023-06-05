package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import java.util.concurrent.atomic.AtomicBoolean;

import kotlin.random.Random;

public class MojBroj extends AppCompatActivity {

    void genNumbers() {
        String[] numbers = {"0", "0", "0", "0", "0", "0"};
        AtomicBoolean stop = new AtomicBoolean(false);
        String[] singleDigits = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] mediumDigits = {"10", "15", "20"};
        String[] bigDigits = {"25", "50", "75", "100"};
        Button singleDigit0 = findViewById(R.id.singleDigit0);
        Button singleDigit1 = findViewById(R.id.singleDigit1);
        Button singleDigit2 = findViewById(R.id.singleDigit2);
        Button singleDigit3 = findViewById(R.id.singleDigit3);
        Button mediumDigit = findViewById(R.id.mediumDigit);
        Button bigDigit = findViewById(R.id.bigDigit);

        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                stop.set(true);
            }
        };

        Button stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(v -> {
            stop.set(true);
            timer.cancel();
        });

        for (int i = 0; i < 6; i++) {
            timer.start();
            stop.set(false);
            while (!stop.get()) {
                int idx = 0;
                if (i < 4) {
                    idx = Random.Default.nextInt(singleDigits.length);
                    numbers[i] = singleDigits[idx];
                    if (i == 0) {
                        singleDigit0.setText(numbers[i]);
                    } else if (i == 1) {
                        singleDigit1.setText(numbers[i]);
                    } else if (i == 2) {
                        singleDigit2.setText(numbers[i]);
                    } else if (i == 3) {
                        singleDigit3.setText(numbers[i]);
                    }
                } else if (i == 4) {
                    idx = Random.Default.nextInt(mediumDigits.length);
                    numbers[i] = mediumDigits[idx];
                    mediumDigit.setText(numbers[i]);
                } else if (i == 5) {
                    idx = Random.Default.nextInt(bigDigits.length);
                    numbers[i] = bigDigits[idx];
                    bigDigit.setText(numbers[i]);
                }
            }
        }
        timer.cancel();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moj_broj);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Moj broj");
        genNumbers();
    }
}