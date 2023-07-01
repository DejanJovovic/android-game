package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import kotlin.random.Random;

public class MojBroj extends AppCompatActivity {
    boolean generated = false;
    TextView timerText;
    CountDownTimer timeLeft;
    CountDownTimer generateTimer;
    List<Button> input = new LinkedList<>();
    int currentNumber = 0;

    void initTimer() {
        timerText = findViewById(R.id.timeLeft);
        timeLeft = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
        timeLeft.start();
    }

    void init() {
        Button singleDigit0 = findViewById(R.id.singleDigit0);
        Button singleDigit1 = findViewById(R.id.singleDigit1);
        Button singleDigit2 = findViewById(R.id.singleDigit2);
        Button singleDigit3 = findViewById(R.id.singleDigit3);
        Button mediumDigit = findViewById(R.id.mediumDigit);
        Button bigDigit = findViewById(R.id.bigDigit);
        Button minus = findViewById(R.id.minusBtn);
        Button plus = findViewById(R.id.plusBtn);
        Button multiply = findViewById(R.id.multiplyBtn);
        Button divide = findViewById(R.id.divideBtn);
        Button openBracket = findViewById(R.id.openBracketBtn);
        Button closedBracket = findViewById(R.id.closedBracketBtn);
        Button delete = findViewById(R.id.delBtn);

        singleDigit0.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        singleDigit1.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        singleDigit2.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        singleDigit3.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        mediumDigit.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        bigDigit.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        plus.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        minus.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        multiply.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        divide.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        openBracket.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        closedBracket.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        delete.setOnClickListener(v -> {
            if (input.size() == 0) {

            } else {
            input.get(input.size()-1).setEnabled(true);
            input.remove(input.size()-1);
            displayInput();
            }
        });
    }

    void displayInput() {
        StringBuffer buffer = new StringBuffer();
        for (Button value : input) {
            buffer.append(value.getText().toString());
        }
        TextView inputText = findViewById(R.id.userInput);
        inputText.setText(buffer.toString());
    }

    void genNumbers() {
        if (generated) return;
        if (currentNumber == 0) {
            Button result = findViewById(R.id.finalResult);
            result.setText("" + Random.Default.nextInt(2, 1000));
        }
        String[] numbers = {"0", "0", "0", "0", "0", "0"};
        String[] singleDigits = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] mediumDigits = {"10", "15", "20"};
        String[] bigDigits = {"25", "50", "75", "100"};
        Button singleDigit0 = findViewById(R.id.singleDigit0);
        Button singleDigit1 = findViewById(R.id.singleDigit1);
        Button singleDigit2 = findViewById(R.id.singleDigit2);
        Button singleDigit3 = findViewById(R.id.singleDigit3);
        Button mediumDigit = findViewById(R.id.mediumDigit);
        Button bigDigit = findViewById(R.id.bigDigit);

        int idx = 0;
        if (currentNumber> 0 && currentNumber < 5) {
            idx = Random.Default.nextInt(singleDigits.length);
            numbers[currentNumber-1] = singleDigits[idx];
            if (currentNumber == 1) {
                singleDigit0.setText(numbers[currentNumber-1]);
            } else if (currentNumber == 2) {
                singleDigit1.setText(numbers[currentNumber-1]);
            } else if (currentNumber == 3) {
                singleDigit2.setText(numbers[currentNumber-1]);
            } else if (currentNumber == 4) {
                singleDigit3.setText(numbers[currentNumber-1]);
            }
        } else if (currentNumber == 5) {
            idx = Random.Default.nextInt(mediumDigits.length);
            numbers[currentNumber-1] = mediumDigits[idx];
            mediumDigit.setText(numbers[currentNumber-1]);
        } else if (currentNumber == 6) {
            idx = Random.Default.nextInt(bigDigits.length);
            numbers[currentNumber-1] = bigDigits[idx];
            bigDigit.setText(numbers[currentNumber-1]);
        }
        if (currentNumber == 6) {
            enableButtons();
            generated = true;
        } else {
            currentNumber ++;
        }

    }

    void enableButtons() {
        Button singleDigit0 = findViewById(R.id.singleDigit0);
        Button singleDigit1 = findViewById(R.id.singleDigit1);
        Button singleDigit2 = findViewById(R.id.singleDigit2);
        Button singleDigit3 = findViewById(R.id.singleDigit3);
        Button mediumDigit = findViewById(R.id.mediumDigit);
        Button bigDigit = findViewById(R.id.bigDigit);
        Button minus = findViewById(R.id.minusBtn);
        Button plus = findViewById(R.id.plusBtn);
        Button multiply = findViewById(R.id.multiplyBtn);
        Button divide = findViewById(R.id.divideBtn);
        Button openBracket = findViewById(R.id.openBracketBtn);
        Button closedBracket = findViewById(R.id.closedBracketBtn);
        Button delete = findViewById(R.id.delBtn);
        singleDigit0.setEnabled(true);
        singleDigit1.setEnabled(true);
        singleDigit2.setEnabled(true);
        singleDigit3.setEnabled(true);
        mediumDigit.setEnabled(true);
        bigDigit.setEnabled(true);
        minus.setEnabled(true);
        plus.setEnabled(true);
        multiply.setEnabled(true);
        divide.setEnabled(true);
        openBracket.setEnabled(true);
        closedBracket.setEnabled(true);
        delete.setEnabled(true);
    }

    Double evalSolution() {
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        TextView solution = findViewById(R.id.userInput);
        try {
            Scriptable scope = rhino.initStandardObjects();
            Object result = rhino.evaluateString(scope, solution.getText().toString(), "JavaScript", 1, null);
            return Double.parseDouble(Context.toString(result));
        } finally {
            Context.exit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeLeft != null) {
            timeLeft.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moj_broj);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Moj broj");
        init();
        initTimer();
        generateTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (!generated) {
                    genNumbers();
                    generateTimer.start();
                }
            }
        };
        generateTimer.start();
        Button result = findViewById(R.id.finalResult);
        Button finish = findViewById(R.id.finishBtn);
        finish.setOnClickListener(v -> {
            timeLeft.cancel();
            Double actual = Double.parseDouble(result.getText().toString());
            Double guessed = evalSolution();
            TextView solution = findViewById(R.id.userInput);
            solution.setText(guessed.toString());
            if (actual == guessed) {
                Toast.makeText(getApplicationContext(), "Osvojili ste 20 poena!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Osvojili ste 5 poena!", Toast.LENGTH_LONG).show();
            }
        });

        Button stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(v -> {
            genNumbers();
            generateTimer.cancel();
            generateTimer.start();
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MojBroj.this, Games.class));
        finish();
    }
}