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

public class KorakPoKorakActivity extends AppCompatActivity {

    boolean isHost;
    private AppCompatButton hint1, hint2, hint3, hint4, hint5, hint6, hint7;

    private EditText konacno1;

    private List<KorakPoKorakHints> korakPoKorakHint = new ArrayList<>();

    private Timer quizTimer;

    private int totalTimeInMins = 1;

    private int seconds = 0;

    int column = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korak_po_korak);

        isHost = getIntent().getExtras().getBoolean("isHost");
        final ImageView backBtnKorakPoKorak = findViewById(R.id.backBtnKorakPoKorak);
        final TextView timerKorakPoKorak = findViewById(R.id.timerKorakPoKorak);
        final TextView selectedGame = findViewById(R.id.gameNameKorakPoKorak);
        final String getSelectedGameName = getIntent().getStringExtra("selectedGame");

        selectedGame.setText(getSelectedGameName);



        hint1 = findViewById(R.id.hint1);
        hint2 = findViewById(R.id.hint2);
        hint3 = findViewById(R.id.hint3);
        hint4 = findViewById(R.id.hint4);
        hint5 = findViewById(R.id.hint5);
        hint6 = findViewById(R.id.hint6);
        hint7 = findViewById(R.id.hint7);

        konacno1 = findViewById(R.id.konacno1);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidquiz-ffbad-default-rtdb.firebaseio.com/");

        ProgressDialog progressDialog = new ProgressDialog(KorakPoKorakActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                totalTimeInMins = Integer.parseInt(snapshot.child("time").getValue(String.class));

                for (DataSnapshot dataSnapshot : snapshot.child("Korak_po_korak").getChildren()) {
                    final String getHint1 = dataSnapshot.child("1").getValue(String.class);
                    final String getHint2 = dataSnapshot.child("2").getValue(String.class);
                    final String getHint3 = dataSnapshot.child("3").getValue(String.class);
                    final String getHint4 = dataSnapshot.child("4").getValue(String.class);
                    final String getHint5 = dataSnapshot.child("5").getValue(String.class);
                    final String getHint6 = dataSnapshot.child("6").getValue(String.class);
                    final String getHint7 = dataSnapshot.child("7").getValue(String.class);
                    final String getKonacno1 = dataSnapshot.child("Konacno").getValue(String.class);

                    KorakPoKorakHints korakPoKorakHints = new KorakPoKorakHints(getHint1, getHint2, getHint3,
                            getHint4, getHint5, getHint6, getHint7, getKonacno1);
                    korakPoKorakHint.add(korakPoKorakHints);

                    startTimer(timerKorakPoKorak);

                    progressDialog.hide();




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        hint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint1.setText(korakPoKorakHint.get(0).getHint1());
            }
        });

        hint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint2.setText(korakPoKorakHint.get(0).getHint2());
            }
        });

        hint3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint3.setText(korakPoKorakHint.get(0).getHint3());
            }
        });

        hint4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint4.setText(korakPoKorakHint.get(0).getHint4());
            }
        });

        hint5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint5.setText(korakPoKorakHint.get(0).getHint5());
            }
        });

        hint6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint6.setText(korakPoKorakHint.get(0).getHint6());
            }
        });

        hint7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint7.setText(korakPoKorakHint.get(0).getHint7());

            }
        });

        backBtnKorakPoKorak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizTimer.purge();
                quizTimer.cancel();

                startActivity(new Intent(KorakPoKorakActivity.this, Games.class));
                finish();
            }
        });

    }

   public void updateKonacno(View view) {
        final String getKonacno1 = korakPoKorakHint.get(0).getKonacno1();
        if(konacno1.getText().toString().equals(getKonacno1)) {
            konacno1.setText(korakPoKorakHint.get(0).getKonacno1());
            konacno1.setBackgroundResource(R.drawable.round_green_reveal);
            getPoints();

            Intent intent = new Intent(KorakPoKorakActivity.this, KorakPoKorakResults.class);
            intent.putExtra("score", getPoints());
            intent.putExtra("isHost", isHost);
            startActivity(intent);

        }
        else {
            Toast.makeText(getApplicationContext(), "Netacan odgovor", Toast.LENGTH_SHORT).show();
        }

   }

   private int getPoints() {
        int score = 0;


       if (column / 2 == 0) {
           score = 20;
       } else if (column / 2 == 1) {
           score = 15;
       } else if (column / 2 == 2) {
           score = 10;
       }

       Toast.makeText(getApplicationContext(), "Osvojili ste " + score + "poena", Toast.LENGTH_LONG).show();
       return score;
       }



    private void startTimer(TextView timerTextView){
        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (seconds == 0) {
                    totalTimeInMins--;
                    seconds = 70;
                }
                else if(seconds == 0 && totalTimeInMins == 0) {
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(KorakPoKorakActivity.this, "Time over!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(KorakPoKorakActivity.this, KorakPoKorakResults.class);
                    intent.putExtra("score", getPoints());
                    intent.putExtra("isHost", isHost);
                    startActivity(intent);
                }
                else{
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(totalTimeInMins);
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

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(KorakPoKorakActivity.this, Games.class));
        finish();
    }


}