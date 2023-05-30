package com.example.androidquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class KoZnaZnaActivity extends AppCompatActivity {

    private TextView questions;
    private TextView question;

    private AppCompatButton option1, option2, option3, option4;
    private AppCompatButton nextBtn;

    private Timer quizTimer;

    private int TotalTimeInMins = 1;

    private int seconds = 0;

    private List<KoZnaZnaList> koZnaZnaLists = new ArrayList<>();
    private int currentQuestionPosition = 0;

    private String selectedOptionByUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ko_zna_zna);

        final String getSelectedGameName = getIntent().getStringExtra("selectedGame");

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView timer = findViewById(R.id.timer);
        final TextView selectedGame = findViewById(R.id.gameName);


        selectedGame.setText(getSelectedGameName);


        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        nextBtn = findViewById(R.id.nextBtn);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidquiz-ffbad-default-rtdb.firebaseio.com/");

        //show dialog while questions are being fetched
        ProgressDialog progressDialog = new ProgressDialog(KoZnaZnaActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            //getting questions from firebase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //getting quiz time
                TotalTimeInMins = Integer.parseInt(snapshot.child("time").getValue(String.class));

                //getting all questions from firebase for a specific game
                for (DataSnapshot dataSnapshot : snapshot.child("Ko_zna_zna").getChildren()) {
                    //getting data from firebase
                    final String getQuestion = dataSnapshot.child("question").getValue(String.class);
                    final String getOption1 = dataSnapshot.child("option1").getValue(String.class);
                    final String getOption2 = dataSnapshot.child("option2").getValue(String.class);
                    final String getOption3 = dataSnapshot.child("option3").getValue(String.class);
                    final String getOption4 = dataSnapshot.child("option4").getValue(String.class);
                    final String getAnswer = dataSnapshot.child("answer").getValue(String.class);

                    //adding data to the questionsList
                    KoZnaZnaList koZnaZnaList = new KoZnaZnaList(getQuestion, getOption1,
                            getOption2, getOption3, getOption4, getAnswer);
                    koZnaZnaLists.add(koZnaZnaList);

//                    //hide dialog
                    progressDialog.hide();

                    //set current question to TextView
                    questions.setText((currentQuestionPosition+1)+ "/" + koZnaZnaLists.size());
                    question.setText(koZnaZnaLists.get(0).getQuestion());
                    option1.setText(koZnaZnaLists.get(0).getOption1());
                    option2.setText(koZnaZnaLists.get(0).getOption2());
                    option3.setText(koZnaZnaLists.get(0).getOption3());
                    option4.setText(koZnaZnaLists.get(0).getOption4());

                    //start timer after data has fetched from firebase
                    startTimer(timer);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option1.getText().toString();

                    option1.setBackgroundResource(R.drawable.round_red);
                    option1.setTextColor(Color.WHITE);

                    revealAnswer();

                    koZnaZnaLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        option2 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option2.getText().toString();

                    option2.setBackgroundResource(R.drawable.round_red);
                    option2.setTextColor(Color.WHITE);

                    revealAnswer();

                    koZnaZnaLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }


            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option3.getText().toString();

                    option3.setBackgroundResource(R.drawable.round_red);
                    option3.setTextColor(Color.WHITE);

                    revealAnswer();

                    koZnaZnaLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option4.getText().toString();

                    option4.setBackgroundResource(R.drawable.round_red);
                    option4.setTextColor(Color.WHITE);

                    revealAnswer();

                    koZnaZnaLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(selectedOptionByUser.isEmpty()){
                    Toast.makeText(KoZnaZnaActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
                else {
                    changeNextQuesiton();
                }
            }
        });




        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizTimer.purge();
                quizTimer.cancel();

                startActivity(new Intent(KoZnaZnaActivity.this, Games.class));
                finish();
            }
        });

    }

    private void changeNextQuesiton(){
        currentQuestionPosition++;

        if((currentQuestionPosition+1) == koZnaZnaLists.size()){
            nextBtn.setText("Submit Quiz");
        }
        if(currentQuestionPosition < koZnaZnaLists.size()){
            selectedOptionByUser = "";

            option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option1.setTextColor(Color.parseColor("#1F6BBB"));

            option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option2.setTextColor(Color.parseColor("#1F6BBB"));

            option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option3.setTextColor(Color.parseColor("#1F6BBB"));

            option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option4.setTextColor(Color.parseColor("#1F6BBB"));


            questions.setText((currentQuestionPosition+1)+ "/" + koZnaZnaLists.size());
            question.setText(koZnaZnaLists.get(currentQuestionPosition).getQuestion());
            option1.setText(koZnaZnaLists.get(currentQuestionPosition).getOption1());
            option2.setText(koZnaZnaLists.get(currentQuestionPosition).getOption2());
            option3.setText(koZnaZnaLists.get(currentQuestionPosition).getOption3());
            option4.setText(koZnaZnaLists.get(currentQuestionPosition).getOption4());

        }
        else {
            Intent intent = new Intent(KoZnaZnaActivity.this, QuizResults.class);
            intent.putExtra("correct", getCorrectAnswers());
            intent.putExtra("incorrect", getInCorrectAnswers());
            startActivity(intent);

            finish();
        }
    }
    private void startTimer(TextView timerTextView){
        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (seconds == 0) {
                    TotalTimeInMins--;
                    seconds = 59;
                }
                else if(seconds == 0 && TotalTimeInMins == 0) {
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(KoZnaZnaActivity.this, "Time over!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(KoZnaZnaActivity.this, QuizResults.class);
                    intent.putExtra("correct", getCorrectAnswers());
                    intent.putExtra("incorrect", getInCorrectAnswers());
                    startActivity(intent);

                    finish();

                }
                else{
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(TotalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if(finalMinutes.length() == 1){
                            finalMinutes = "0" + finalMinutes;
                        }

                        if(finalSeconds.length() == 1) {
                            finalSeconds = "0" + finalSeconds;
                        }

                        timerTextView.setText(finalMinutes + ":" + finalSeconds);

                    }
                });


            }
        }, 1000, 1000);
    }

    private int getCorrectAnswers(){
        int correctAnswers = 0;

        for(int i = 0; i< koZnaZnaLists.size(); i++){
            final String getUserSelectedAnswer = koZnaZnaLists.get(i).getUserSelectedAnswer();
            final String getAnswer = koZnaZnaLists.get(i).getAnswer();

            if(getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    private int getInCorrectAnswers(){
        int correctAnswers = 0;

        for(int i = 0; i< koZnaZnaLists.size(); i++){
            final String getUserSelectedAnswer = koZnaZnaLists.get(i).getUserSelectedAnswer();
            final String getAnswer = koZnaZnaLists.get(i).getAnswer();

            if(!getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(KoZnaZnaActivity.this, Games.class));
        finish();
    }


    private void revealAnswer(){
        final String getAnswer = koZnaZnaLists.get(currentQuestionPosition).getAnswer();

        if(option1.getText().toString().equals(getAnswer)){
            option1.setBackgroundResource(R.drawable.round_green_reveal);
            option1.setTextColor(Color.WHITE);
        }
        else if(option2.getText().toString().equals(getAnswer)) {
            option2.setBackgroundResource(R.drawable.round_green_reveal);
            option2.setTextColor(Color.WHITE);
        }

        else if(option3.getText().toString().equals(getAnswer)) {
            option3.setBackgroundResource(R.drawable.round_green_reveal);
            option3.setTextColor(Color.WHITE);
        }

        else if(option4.getText().toString().equals(getAnswer)) {
            option4.setBackgroundResource(R.drawable.round_green_reveal);
            option4.setTextColor(Color.WHITE);
        }


    }
}