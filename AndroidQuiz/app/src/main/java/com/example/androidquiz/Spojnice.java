package com.example.androidquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidquiz.models.SpojniceAnswerModel;
import com.example.androidquiz.models.SpojniceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Spojnice extends AppCompatActivity {
    SpojniceModel model = new SpojniceModel();
    List<Button> leftBtns = new ArrayList<>();
    List<Button> rightBtns = new ArrayList<>();
    List<String> left;
    List<String> right;
    int selectedLeft = 0;
    int correctCounter = 0;
    TextView timerText;
    CountDownTimer timer1;

    private void initTimer() {
        timerText = findViewById(R.id.timer1);
        timer1 = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {finishGame();}
        };
        timer1.start();
    }

    private void init() {
        Button theme = findViewById(R.id.tema);
        Button left0 = findViewById(R.id.levo0);
        Button left1 = findViewById(R.id.levo1);
        Button left2 = findViewById(R.id.levo2);
        Button left3 = findViewById(R.id.levo3);
        Button left4 = findViewById(R.id.levo4);
        Button right0 = findViewById(R.id.desno0);
        Button right1 = findViewById(R.id.desno1);
        Button right2 = findViewById(R.id.desno2);
        Button right3 = findViewById(R.id.desno3);
        Button right4 = findViewById(R.id.desno4);
        theme.setText(model.getTitle());
        leftBtns.add(left0);
        leftBtns.add(left1);
        leftBtns.add(left2);
        leftBtns.add(left3);
        leftBtns.add(left4);
        rightBtns.add(right0);
        rightBtns.add(right1);
        rightBtns.add(right2);
        rightBtns.add(right3);
        rightBtns.add(right4);

        for (int i = 0; i < 5; i++) {
            leftBtns.get(i).setText(left.get(i));
            rightBtns.get(i).setText(right.get(i));
            int index = i;
            rightBtns.get(i).setOnClickListener(v -> {
                boolean correct = checkIsCorrect(selectedLeft, index);
                if (correct) {
                    v.setEnabled(false);
                    ((Button)v).setTextColor(getResources().getColor(android.R.color.black, null));
                    v.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light, null));
                }
                selectedLeft ++;
                if (selectedLeft >= leftBtns.size()) {
                    finishGame();
                }
                highlightLeftBtns();
            });
        }
        highlightLeftBtns();
    }

    private void finishGame() {
        for (Button btn : rightBtns) {
            btn.setEnabled(false);
        }
        Toast.makeText(this, "Osvojili ste: " + correctCounter * 2 + " bodova!", Toast.LENGTH_LONG).show();
        Button nextGameBtn = findViewById(R.id.nextGame1);
        nextGameBtn.setVisibility(View.VISIBLE);
        timer1.cancel();
        nextGameBtn.setOnClickListener(v1 -> {
            Intent intent = new Intent(Spojnice.this, AsosijacijeActivity.class);
            startActivity(intent);
        });
    }

    private boolean checkIsCorrect(int left, int right) {
        String leftText = leftBtns.get(left).getText().toString();
        String rightText = rightBtns.get(right).getText().toString();
        for (SpojniceAnswerModel item : model.getAnswers()) {
            if (item.getLeft().equals(leftText)) {
                if (item.getRight().equals(rightText)) {
                    correctCounter++;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private void highlightLeftBtns() {
        for (int i = 0; i < leftBtns.size(); i++) {
            if (i == selectedLeft) {
                leftBtns.get(i).setBackgroundColor(getResources().getColor(R.color.red, null));
            } else {
                leftBtns.get(i).setBackgroundColor(getResources().getColor(R.color.purple_200, null));
            }
        }
    }

    private void loadAnswers() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidquiz-ffbad-default-rtdb.firebaseio.com/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SpojniceModel> spojnice = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.child("spojnice").getChildren()) {
                    SpojniceModel spojnica = dataSnapshot.getValue(SpojniceModel.class);
                    spojnice.add(spojnica);
                }
                int randomIndex = new Random().nextInt(spojnice.size());
                model = spojnice.get(randomIndex);

                left = new ArrayList<>();
                right = new ArrayList<>();
                for (SpojniceAnswerModel answer : model.getAnswers()) {
                    left.add(answer.getLeft());
                    right.add(answer.getRight());
                }

                Collections.shuffle(left);
                Collections.shuffle(right);
                init();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer1 != null) {
            timer1.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spojnice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Spojnice");
        initTimer();
        loadAnswers();
    }
}