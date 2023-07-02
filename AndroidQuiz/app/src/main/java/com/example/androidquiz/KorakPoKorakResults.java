package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class KorakPoKorakResults extends AppCompatActivity {
    boolean isHost;
    private String selectedGameName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korak_po_korak_results);

        isHost = getIntent().getExtras().getBoolean("isHost");
        final AppCompatButton startNewBtn = findViewById(R.id.startNewQuizBtnKorakPoKorak);
        final TextView totalScore = findViewById(R.id.totalScoreKorakPoKorak);

        final int getPoints = getIntent().getIntExtra("totalScore", 0);

        totalScore.setText(String.valueOf(getPoints));

        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedGameName = "Korak po Korak";
                Intent intent = new Intent(KorakPoKorakResults.this, KorakPoKorakActivity2.class);
                intent.putExtra("selectedGame", selectedGameName);
                intent.putExtra("isHost", isHost);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(KorakPoKorakResults.this, Games.class));
        finish();
    }
}