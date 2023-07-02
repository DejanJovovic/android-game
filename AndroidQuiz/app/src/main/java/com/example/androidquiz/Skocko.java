package com.example.androidquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Skocko extends AppCompatActivity {

    boolean isHost;
    TextView[] results = new TextView[6];
    ImageView[][] slots = new ImageView[6][4];
    int[][] guesses = new int[6][4];
    int row = 0;
    int column = 0;
    int[] solution;
    TextView timerText;
    CountDownTimer timer;

    void initTimer() {
        timerText = findViewById(R.id.timer);
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                showResult();
            }
        };
        timer.start();
    }

    void initResult() {
        results[0] = findViewById(R.id.result0);
        results[1] = findViewById(R.id.result1);
        results[2] = findViewById(R.id.result2);
        results[3] = findViewById(R.id.result3);
        results[4] = findViewById(R.id.result4);
        results[5] = findViewById(R.id.result5);
    }

    void initSlots() {
        slots[0][0] = findViewById(R.id.slot00);
        slots[0][1] = findViewById(R.id.slot01);
        slots[0][2] = findViewById(R.id.slot02);
        slots[0][3] = findViewById(R.id.slot03);
        slots[1][0] = findViewById(R.id.slot10);
        slots[1][1] = findViewById(R.id.slot11);
        slots[1][2] = findViewById(R.id.slot12);
        slots[1][3] = findViewById(R.id.slot13);
        slots[2][0] = findViewById(R.id.slot20);
        slots[2][1] = findViewById(R.id.slot21);
        slots[2][2] = findViewById(R.id.slot22);
        slots[2][3] = findViewById(R.id.slot23);
        slots[3][0] = findViewById(R.id.slot30);
        slots[3][1] = findViewById(R.id.slot31);
        slots[3][2] = findViewById(R.id.slot32);
        slots[3][3] = findViewById(R.id.slot33);
        slots[4][0] = findViewById(R.id.slot40);
        slots[4][1] = findViewById(R.id.slot41);
        slots[4][2] = findViewById(R.id.slot42);
        slots[4][3] = findViewById(R.id.slot43);
        slots[5][0] = findViewById(R.id.slot50);
        slots[5][1] = findViewById(R.id.slot51);
        slots[5][2] = findViewById(R.id.slot52);
        slots[5][3] = findViewById(R.id.slot53);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                guesses[i][j] = -1;
            }
        }

    }

    void handleButtonClick(Drawable d, int id) {
        if (row >= slots.length) {
            return;
        }
        slots[row][column].setImageDrawable(d);
        guesses[row][column] = id;
        column++;
        if (column >= 4) {
            column = 0;
            checkMatch();
            row++;
        }
        if (row == slots.length) {
            showResult();
        }
    }

    void handleDelete() {
        if (column == 0) {
            return;
        }
        slots[row][column-1].setImageDrawable(getDrawable(R.drawable.frame));
        guesses[row][column-1] = -1;
        if (column > 0) {
            column--;
        }
    }


    void checkMatch() {
        int correct = 0;
        int misplaced = 0;
        boolean[] confirmed = {false, false, false, false};
        for (int i = 0; i < 4; i++) {
            if (guesses[row][i] == solution[i]) {
                correct++;
                confirmed[i] = true;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (confirmed[i] == true) {
                continue;
            }
            for (int j = 0; j < 4; j++) {
                if (confirmed[j] == true) {
                    continue;
                }
                if (guesses[row][i] == solution[j]) {
                    misplaced++;
                    confirmed[j] = true;
                    break;
                }
            }
        }
        results[row].setText("correct:" + correct + "\nmisplaced:" + misplaced);
        if (correct == 4) {
            showResult();
            showScore();
        }
    }

    void initButtons() {
        ImageButton tref = findViewById(R.id.tref);
        ImageButton herc = findViewById(R.id.herc);
        ImageButton karo = findViewById(R.id.karo);
        ImageButton pik = findViewById(R.id.pik);
        ImageButton cd = findViewById(R.id.cd);
        ImageButton zvezda = findViewById(R.id.zvezda);
        ImageButton delete = findViewById(R.id.delete);

        tref.setOnClickListener((view) -> {
            handleButtonClick(((ImageButton)view).getDrawable(), 0);
        });
        herc.setOnClickListener((view) -> {
            handleButtonClick(((ImageButton)view).getDrawable(), 1);
        });
        karo.setOnClickListener((view) -> {
            handleButtonClick(((ImageButton)view).getDrawable(), 2);
        });
        pik.setOnClickListener((view) -> {
            handleButtonClick(((ImageButton)view).getDrawable(), 3);
        });
        cd.setOnClickListener((view) -> {
            handleButtonClick(((ImageButton)view).getDrawable(), 4);
        });
        zvezda.setOnClickListener((view) -> {
            handleButtonClick(((ImageButton)view).getDrawable(), 5);
        });
        delete.setOnClickListener((view) -> {
            handleDelete();
        });

    }

    void generateSolution() {
        solution = new int[4];
        Random generator = new Random();
        for (int i = 0; i < 4; i++) {
            int number = generator.nextInt(6);
            solution[i] = number;
        }
    }

    void showScore() {
        int score = 0;
        if (row / 2 == 0) {
            score = 20;
        } else if (row / 2 == 1) {
            score = 15;
        } else if (row / 2 == 2) {
            score = 10;
        }
        Toast.makeText(getApplicationContext(), "Osvojili ste " + score + " poena!", Toast.LENGTH_LONG).show();
    }

    void showResult() {
        ImageView[] slots = new ImageView[4];
        slots[0] = findViewById(R.id.slot70);
        slots[1] = findViewById(R.id.slot71);
        slots[2] = findViewById(R.id.slot72);
        slots[3] = findViewById(R.id.slot73);
        for (int i = 0; i < 4; i++) {
            switch (solution[i]) {
                case 0:
                    slots[i].setImageDrawable(getDrawable(R.drawable.tref));
                    break;
                case 1:
                    slots[i].setImageDrawable(getDrawable(R.drawable.herc));
                    break;
                case 2:
                    slots[i].setImageDrawable(getDrawable(R.drawable.karo));
                    break;
                case 3:
                    slots[i].setImageDrawable(getDrawable(R.drawable.pik));
                    break;
                case 4:
                    slots[i].setImageDrawable(getDrawable(R.drawable.cd));
                    break;
                case 5:
                    slots[i].setImageDrawable(getDrawable(R.drawable.zvezda));
                    break;
            }
        }
        Button nextGame = findViewById(R.id.nextGame);
        nextGame.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skocko);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        isHost = getIntent().getExtras().getBoolean("isHost");
        setTitle("Skocko");
        initTimer();
        initResult();
        initSlots();
        initButtons();
        generateSolution();
        Button nextGame = findViewById(R.id.nextGame);
        nextGame.setOnClickListener(view -> {

            Intent intent = new Intent(Skocko.this, KorakPoKorakActivity.class);
            Bundle extras = new Bundle();
            extras.putBoolean("isHost", isHost);
            intent.putExtras(extras);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Skocko.this, Games.class));
        finish();
    }
}