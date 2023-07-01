package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class KorakPoKorakResults2 extends AppCompatActivity {
    private String selectedGameName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korak_po_korak_results2);

        final AppCompatButton startNewBtn = findViewById(R.id.startNewQuizBtnKorakPoKorak2);
        final TextView totalScore = findViewById(R.id.totalScoreKorakPoKorak2);

        final int getPoints = getIntent().getIntExtra("totalScore", 0);

        totalScore.setText(String.valueOf(getPoints));

        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedGameName = "MojBroj";
                Intent intent = new Intent(KorakPoKorakResults2.this, MojBroj.class);
                intent.putExtra("selectedGame", selectedGameName);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(KorakPoKorakResults2.this, Games.class));
        finish();
    }
}