package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Games extends AppCompatActivity {

    private String selectedGameName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Games");

        final LinearLayout Ko_Zna_Zna = findViewById(R.id.koZnaZnaLayout);
        final LinearLayout Korak_po_Korak = findViewById(R.id.korakPoKorakLayout);
        final LinearLayout asosijacije = findViewById(R.id.asosijacijeLayout);

        final Button startBtn = findViewById(R.id.startQuizBtn);

        Ko_Zna_Zna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedGameName = "Ko zna zna";

                Ko_Zna_Zna.setBackgroundResource(R.drawable.round_back_white_stroke);

                Korak_po_Korak.setBackgroundResource(R.drawable.round_white);
                asosijacije.setBackgroundResource(R.drawable.round_white);
            }
        });

        Korak_po_Korak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedGameName = "Korak po Korak";

                Korak_po_Korak.setBackgroundResource(R.drawable.round_back_white_stroke);

                Ko_Zna_Zna.setBackgroundResource(R.drawable.round_white);
                asosijacije.setBackgroundResource(R.drawable.round_white);

                Intent intent = new Intent(Games.this, KorakPoKorakActivity.class);
                intent.putExtra("selectedGame", selectedGameName);
                startActivity(intent);


            }
        });

        asosijacije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedGameName = "Asosijacije";

                asosijacije.setBackgroundResource(R.drawable.round_back_white_stroke);

                Ko_Zna_Zna.setBackgroundResource(R.drawable.round_white);
                Korak_po_Korak.setBackgroundResource(R.drawable.round_white);

                Intent intent = new Intent(Games.this, AsosijacijeActivity.class);
                intent.putExtra("selectedGame", selectedGameName);
                startActivity(intent);

            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedGameName.isEmpty()) {
                    Toast.makeText(Games.this, "Please select a game", Toast.LENGTH_SHORT).show();
                }
                else {
                    final Intent intent = new Intent(Games.this, KoZnaZnaActivity.class);
                    intent.putExtra("selectedGame", selectedGameName);
                    startActivity(intent);
                }
            }
        });


    }

}