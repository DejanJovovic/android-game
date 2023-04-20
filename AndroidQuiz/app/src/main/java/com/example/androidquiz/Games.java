package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Games extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Games");
    }


    public void koZnaZna(View v) {
        Intent intent = new Intent(this, KoZnaZna.class);
        startActivity(intent);
    }

    public void spojnice(View v) {
        Intent intent = new Intent(this, Spojnice.class);
        startActivity(intent);
    }

    public void asosijacije(View v) {
        Intent intent = new Intent(this, Asosijacije.class);
        startActivity(intent);
    }

    public void skocko(View v) {
        Intent intent = new Intent(this, Skocko.class);
        startActivity(intent);
    }

    public void korakPoKorak(View v) {
        Intent intent = new Intent(this, KorakPoKorak.class);
        startActivity(intent);
    }

    public void mojBroj(View v) {
        Intent intent = new Intent(this, MojBroj.class);
        startActivity(intent);
    }

}