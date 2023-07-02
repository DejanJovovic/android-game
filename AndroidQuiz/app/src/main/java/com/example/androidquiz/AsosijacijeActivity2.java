package com.example.androidquiz;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class AsosijacijeActivity2 extends AppCompatActivity {


    private AppCompatButton btnA1, btnA2, btnA3, btnA4;
    private AppCompatButton btnB1, btnB2, btnB3, btnB4;
    private AppCompatButton btnC1, btnC2, btnC3, btnC4;
    private AppCompatButton btnD1, btnD2, btnD3, btnD4;

    private EditText konacnoA, konacnoB, konacnoC, konacnoD;
    private EditText konacno;

    private List<AsosijacijeFields> asosijacijeField = new ArrayList<>();

    private Timer quizTimer;

    private int totalTimeInMins = 1;

    private int seconds = 0;

    int userSelectedField = 0;

    int score = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asosijacije2);


        final ImageView backBtnAsosijacije = findViewById(R.id.backBtnAsosijacije2);
        final TextView timerAsosijacije = findViewById(R.id.timerAsosijacije2);
        final TextView selectedGame = findViewById(R.id.gameNameAsosijacije2);
        final String getSelectedGameName = getIntent().getStringExtra("selectedGame");

        selectedGame.setText(getSelectedGameName);

        btnA1 = findViewById(R.id.btnA1);
        btnA2 = findViewById(R.id.btnA2);
        btnA3 = findViewById(R.id.btnA3);
        btnA4 = findViewById(R.id.btnA4);

        btnB1 = findViewById(R.id.btnB1);
        btnB2 = findViewById(R.id.btnB2);
        btnB3 = findViewById(R.id.btnB3);
        btnB4 = findViewById(R.id.btnB4);

        btnC1 = findViewById(R.id.btnC1);
        btnC2 = findViewById(R.id.btnC2);
        btnC3 = findViewById(R.id.btnC3);
        btnC4 = findViewById(R.id.btnC4);

        btnD1 = findViewById(R.id.btnD1);
        btnD2 = findViewById(R.id.btnD2);
        btnD3 = findViewById(R.id.btnD3);
        btnD4 = findViewById(R.id.btnD4);

        konacnoA = findViewById(R.id.konacnoA);
        konacnoB = findViewById(R.id.konacnoB);
        konacnoC = findViewById(R.id.konacnoC);
        konacnoD = findViewById(R.id.konacnoD);
        konacno = findViewById(R.id.konacno);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidquiz-ffbad-default-rtdb.firebaseio.com/");

        ProgressDialog progressDialog = new ProgressDialog(AsosijacijeActivity2.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                totalTimeInMins = Integer.parseInt(snapshot.child("time").getValue(String.class));

                for (DataSnapshot dataSnapshot : snapshot.child("asosijacije2").getChildren()) {
                    final String getBtnA1 = dataSnapshot.child("A1").getValue(String.class);
                    final String getBtnA2 = dataSnapshot.child("A2").getValue(String.class);
                    final String getBtnA3 = dataSnapshot.child("A3").getValue(String.class);
                    final String getBtnA4 = dataSnapshot.child("A4").getValue(String.class);
                    final String getBtnB1 = dataSnapshot.child("B1").getValue(String.class);
                    final String getBtnB2 = dataSnapshot.child("B2").getValue(String.class);
                    final String getBtnB3 = dataSnapshot.child("B3").getValue(String.class);
                    final String getBtnB4 = dataSnapshot.child("B4").getValue(String.class);
                    final String getBtnC1 = dataSnapshot.child("C1").getValue(String.class);
                    final String getBtnC2 = dataSnapshot.child("C2").getValue(String.class);
                    final String getBtnC3 = dataSnapshot.child("C3").getValue(String.class);
                    final String getBtnC4 = dataSnapshot.child("C4").getValue(String.class);
                    final String getBtnD1 = dataSnapshot.child("D1").getValue(String.class);
                    final String getBtnD2 = dataSnapshot.child("D2").getValue(String.class);
                    final String getBtnD3 = dataSnapshot.child("D3").getValue(String.class);
                    final String getBtnD4 = dataSnapshot.child("D4").getValue(String.class);
                    final String getKonacnoA = dataSnapshot.child("konacnoA").getValue(String.class);
                    final String getKonacnoB = dataSnapshot.child("konacnoB").getValue(String.class);
                    final String getKonacnoC = dataSnapshot.child("konacnoC").getValue(String.class);
                    final String getKonacnoD = dataSnapshot.child("konacnoD").getValue(String.class);
                    final String getKonacno = dataSnapshot.child("konacno").getValue(String.class);

                    AsosijacijeFields asosijacijeFields = new AsosijacijeFields(getBtnA1, getBtnA2, getBtnA3, getBtnA4, getBtnB1,
                            getBtnB2, getBtnB3, getBtnB4, getBtnC1, getBtnC2, getBtnC3, getBtnC4, getBtnD1, getBtnD2, getBtnD3,
                            getBtnD4, getKonacnoA, getKonacnoB, getKonacnoC, getKonacnoD, getKonacno);
                    asosijacijeField.add(asosijacijeFields);

                    startTimer(timerAsosijacije);

                    progressDialog.hide();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnA1.setText(asosijacijeField.get(0).getBtnA1());
            }
        });

        btnA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnA2.setText(asosijacijeField.get(0).getBtnA2());
            }
        });

        btnA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnA3.setText(asosijacijeField.get(0).getBtnA3());
            }
        });

        btnA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnA4.setText(asosijacijeField.get(0).getBtnA4());
            }
        });

        btnB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnB1.setText(asosijacijeField.get(0).getBtnB1());
            }
        });

        btnB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnB2.setText(asosijacijeField.get(0).getBtnB2());
            }
        });

        btnB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnB3.setText(asosijacijeField.get(0).getBtnB3());
            }
        });

        btnB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnB4.setText(asosijacijeField.get(0).getBtnB4());
            }
        });

        btnC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnC1.setText(asosijacijeField.get(0).getBtnC1());
            }
        });

        btnC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnC2.setText(asosijacijeField.get(0).getBtnC2());
            }
        });

        btnC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnC3.setText(asosijacijeField.get(0).getBtnC3());
            }
        });

        btnC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnC4.setText(asosijacijeField.get(0).getBtnC4());
            }
        });

        btnD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnD1.setText(asosijacijeField.get(0).getBtnD1());
            }
        });

        btnD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnD2.setText(asosijacijeField.get(0).getBtnD2());
            }
        });

        btnD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnD3.setText(asosijacijeField.get(0).getBtnD3());
            }
        });

        btnD4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnD4.setText(asosijacijeField.get(0).getBtnD4());
            }
        });


        backBtnAsosijacije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizTimer.purge();
                quizTimer.cancel();

                startActivity(new Intent(AsosijacijeActivity2.this, Games.class));
                finish();
            }
        });

    }

    public void updateAsosijacije(View view) {
        final String getKonacnoA = asosijacijeField.get(0).getKonacnoA();
        final String getKonacnoB = asosijacijeField.get(0).getKonacnoB();
        final String getKonacnoC = asosijacijeField.get(0).getKonacnoC();
        final String getKonacnoD = asosijacijeField.get(0).getKonacnoD();
        final String getKonacno = asosijacijeField.get(0).getKonacno();

        if(konacnoA.getText().toString().equals(getKonacnoA)) {
            konacnoA.setText(asosijacijeField.get(0).getKonacnoA());
            konacnoA.setBackgroundResource(R.drawable.round_green_reveal);

            getPoints();
            Toast.makeText(getApplicationContext(), "Osvojili ste " + score + "poena", Toast.LENGTH_LONG).show();
        } else if(!konacnoA.getText().toString().equals(getKonacnoA)){
            score = 0;
        }
        if (konacnoB.getText().toString().equals(getKonacnoB)) {
            konacnoB.setText(asosijacijeField.get(0).getKonacnoB());
            konacnoB.setBackgroundResource(R.drawable.round_green_reveal);
            getPoints();
            Toast.makeText(getApplicationContext(), "Osvojili ste " + score + "poena", Toast.LENGTH_LONG).show();
        } else if(!konacnoB.getText().toString().equals(getKonacnoB)) {
            score = 0;
        }
        if(konacnoC.getText().toString().equals(getKonacnoC)) {
            konacnoC.setText(asosijacijeField.get(0).getKonacnoC());
            konacnoC.setBackgroundResource(R.drawable.round_green_reveal);
            getPoints();
            Toast.makeText(getApplicationContext(), "Osvojili ste " + score + "poena", Toast.LENGTH_LONG).show();
        } else if(!konacnoC.getText().toString().equals(getKonacnoC)){
            score = 0;
        }
        if (konacnoD.getText().toString().equals(getKonacnoD)) {
            konacnoD.setText(asosijacijeField.get(0).getKonacnoD());
            konacnoD.setBackgroundResource(R.drawable.round_green_reveal);
            getPoints();
            Toast.makeText(getApplicationContext(), "Osvojili ste " + score + "poena", Toast.LENGTH_LONG).show();

        } else if(!konacnoD.getText().toString().equals(getKonacnoD)){
            score = 0;
        }
        if(konacno.getText().toString().equals(getKonacno)) {
            konacno.setText(asosijacijeField.get(0).getKonacno());
            konacno.setBackgroundResource(R.drawable.round_green_reveal);
            getPointsKonacno();
            Toast.makeText(getApplicationContext(), "Osvojili ste " + score + "poena", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AsosijacijeActivity2.this, AsosijacijeResults2.class);
            startActivity(intent);



        }
        else if (!konacno.getText().toString().equals(getKonacno)){
            Toast.makeText(getApplicationContext(), "Netacan odgovor", Toast.LENGTH_SHORT).show();
            score = 0;
        }
    }

    private int getPoints() {

        if(userSelectedField / 2 == 0) {
            score += 4;
        }
        return score;
    }

    private int getPointsKonacno() {

        if(userSelectedField / 2 == 0) {
            score += 10;
        }
        return score;
    }



    private void startTimer(TextView timerTextView) {
        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (seconds == 0) {
                    totalTimeInMins--;
                    seconds = 59;
                } else if (seconds == 0 && totalTimeInMins == 0) {
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(AsosijacijeActivity2.this, "Time over!", Toast.LENGTH_SHORT).show();

                } else {
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if (finalMinutes.length() == 1) {
                            finalMinutes = "0" + finalMinutes;
                        }

                        if (finalSeconds.length() == 1) {
                            finalSeconds = "0" + finalSeconds;
                        }

                        timerTextView.setText(finalMinutes + ":" + finalSeconds);

                    }
                });


            }
        }, 1000, 1000);
    }

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(AsosijacijeActivity2.this, Games.class));
        finish();
    }

}